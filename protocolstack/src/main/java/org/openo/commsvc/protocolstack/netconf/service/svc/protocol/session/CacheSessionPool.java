/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.Netconf;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.NetconfContants;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.LockFailedException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfErrCode;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ICacheSessionPool;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISessionFatory;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISessionListener;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache session pool interface maintaining the session of device session pool.
 * <br>
 * 
 * @param <T>
 * @author
 * @version     GSO 0.5  Sep 7, 2016
 */
public final class CacheSessionPool<T extends NetconfAccessInfo> implements ICacheSessionPool<T>, ISessionListener {

    /**
     * priority queue size
     */
    private static final int INIT_PRIORITY_QUEUE_SIZE = 800;

    /**
     * max session limit
     */
    private static final int MAX_SESSION_LIMIT = Netconf.getMaxHoldConnections();

    /**
     * one min
     */
    private static final int ONE_MINUTE = 60;

    /**
     * set of concurrent callors
     */
    private final Set<Thread> callors = new ConcurrentSkipListSet<Thread>(new Comparator<Thread>() {

        /**
         * compare<br>
         * *
         * 
         * @author
         */
        @Override
        public int compare(Thread arg0, Thread arg1) {
            return (int)(arg0.getId() - arg1.getId());
        }
    });

    /**
     * cleaner monitor
     */
    private final Object cleanerMonitor = new Object();

    /**
     * immediate cleaner
     */
    private Thread immediateCleanerThread;

    /**
     * long free cleaner thread
     */
    private Thread longFreeCleanerThread;

    /**
     * map of device session pools
     */
    private final Map<T, DeviceSessionPool> deviceSessionPools = new HashMap<T, DeviceSessionPool>();

    /**
     * free device session pool queue
     */
    private final PriorityQueue<DeviceSessionPool> freeDevSessionPoolQueue =
            new PriorityQueue<DeviceSessionPool>(INIT_PRIORITY_QUEUE_SIZE);

    /**
     * deviceSessionPool reentrant lock
     */
    private final Lock lockObj = new ReentrantLock();

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheSessionPool.class);

    /**
     * need release
     */
    private int needRelease = 0;

    /**
     * Netconf session factory
     */
    @SuppressWarnings("rawtypes")
    private ISessionFatory sessionFactory;

    /**
     * total session number
     */
    private int totalSessionNum = 0;

    /**
     * total session monitors
     */
    private final Object totalSessionNumMonitor = new Object();

    /**
     * constructor<br>
     * <br>
     * 
     * @author
     */
    public CacheSessionPool() {
        this.immediateCleanerThread = new Thread(new ImmediateCleaner(), "ICleaner");
        this.immediateCleanerThread.setDaemon(true);
        this.immediateCleanerThread.start();
        this.longFreeCleanerThread = new Thread(new LongFreeCleaner(), "LFCleaner");
        this.longFreeCleanerThread.setDaemon(true);
        this.longFreeCleanerThread.start();
    }

    /**
     * close device session<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ICacheSessionPool#close(org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISession)
     * @author
     */
    @Override
    public void close(ISession session) {
        entryCallor();
        try {
            if(null == session) {
                this.LOGGER.info("close session null");
                return;
            }

            try {
                DeviceSessionPool devSessionPool = getDevSessionPool(session.getNetconfAccessInfo());
                if(null != devSessionPool) {
                    devSessionPool.removeSession(session);
                }
            } finally {
                closeNetconfSession(session);
            }
        } finally {
            exitCallor();
        }
    }

    /**
     * close device session<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISessionPool#close(org.openo.commsvc.connector.netconf.service.svc.protocol.service.NetconfAccessInfo)
     * @author
     */
    @Override
    public void close(T netconfAccessInfo) {
        entryCallor();
        try {
            final DeviceSessionPool deviceSessionPool;
            lockWithoutTimeout();
            try {
                deviceSessionPool = this.deviceSessionPools.remove(netconfAccessInfo);
                if(null == deviceSessionPool) {
                    this.LOGGER.error("close da null:{}", netconfAccessInfo.getDevIp());
                    return;
                }
                this.freeDevSessionPoolQueue.remove(deviceSessionPool);
            } finally {
                unlock();
            }

            List<SessionWrapper> removeFreeSessions = deviceSessionPool.removeFreeSessions();
            this.LOGGER.warn("close:{},{}", removeFreeSessions.size(),
                    deviceSessionPool.getNetconfAccessInfo().getDevIp());
            for(SessionWrapper removeFreeSession : removeFreeSessions) {
                if(null != removeFreeSession) {
                    closeNetconfSession(removeFreeSession.getSession());
                }
            }
        } finally {
            exitCallor();
        }
    }

    /**
     * disconnect session<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISessionListener#disconnect(org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISession)
     * @author
     */
    @Override
    public void disconnect(ISession session) {
        close(session);
    }

    /**
     * Get session<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISessionPool#getSession(org.openo.commsvc.connector.netconf.service.svc.protocol.service.NetconfAccessInfo,
     *      java.lang.String)
     * @author
     */
    @Override
    public ISession getSession(T netconfAccessInfo, String createThreadName) throws NetconfException {
        return getSession(netconfAccessInfo, false, createThreadName);
    }

    /**
     * release a session<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISessionPool#releaseSession(org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISession)
     * @author
     */
    @Override
    public void releaseSession(ISession session) {
        entryCallor();
        try {
            if(null == session) {
                this.LOGGER.info("releaseSession session null");
                return;
            }

            final DeviceSessionPool da = getDevSessionPool(session.getNetconfAccessInfo());
            if(null == da) {
                this.LOGGER.error("releaseSession da null: " + session);
                closeNetconfSession(session);
                return;
            }

            if(da.freeSession(session)) {
                this.LOGGER.warn("releaseSession success: " + session);
                add2FreeDevSessionPoolQueue(da);
            } else {
                closeNetconfSession(session);
            }
        } finally {
            exitCallor();
        }
    }

    /**
     * @param sessionFactory The sessionFactory to set.
     */
    @SuppressWarnings("rawtypes")
    public void setSessionFactory(ISessionFatory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Test connectivity <br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISessionPool#testConnective(org.openo.commsvc.connector.netconf.service.svc.protocol.service.NetconfAccessInfo)
     * @author
     */
    @Override
    public int testConnective(T netconfAccessInfo) {
        ISession session = null;
        try {
            session = getSession(netconfAccessInfo, false, Thread.currentThread().getName());
            return NetconfErrCode.SUCCESS;
        } catch(NetconfException e) {
            this.LOGGER.error("testConnective ex." + netconfAccessInfo.getDevIp(), e);
            return e.getErrorCode();
        } finally {
            if(null != session) {
                releaseSession(session);
            }
        }
    }

    /**
     * Add to the free device session pool queue<br>
     * 
     * @author
     * @param da DeviceSessionPool
     */
    private void add2FreeDevSessionPoolQueue(final DeviceSessionPool da) {
        lockWithoutTimeout();
        try {
            if(!this.freeDevSessionPoolQueue.contains(da)) {
                this.LOGGER.info("add free session:{},{}", da.getId(), da.getNetconfAccessInfo());
                this.freeDevSessionPoolQueue.add(da);
            }
        } finally {
            unlock();
        }
    }

    /**
     * clean unused device session pool <br>
     * 
     * @author
     */
    private void cleanUnuseSessionPool() {
        List<DeviceSessionPool> toCloseDeviceSessionPools = new ArrayList<DeviceSessionPool>();
        lockWithoutTimeout();
        try {
            Iterator<Entry<T, DeviceSessionPool>> it = this.deviceSessionPools.entrySet().iterator();
            while(it.hasNext()) {
                Entry<T, DeviceSessionPool> deviceSessionPoolEntry = it.next();
                DeviceSessionPool deviceSessionPool = deviceSessionPoolEntry.getValue();
                if(null == deviceSessionPool) {
                    it.remove();
                    continue;
                }

                int tmpUnuseTimes = deviceSessionPool.increaseUnuseTimes();
                if(tmpUnuseTimes >= Netconf.getDevSessionPoolKeepAliveTimes()) {
                    it.remove();
                    toCloseDeviceSessionPools.add(deviceSessionPool);
                }
            }
            this.freeDevSessionPoolQueue.removeAll(toCloseDeviceSessionPools);
        } finally {
            unlock();
        }

        for(DeviceSessionPool toCloseDeviceSessionPool : toCloseDeviceSessionPools) {
            List<SessionWrapper> removeFreeSessions = toCloseDeviceSessionPool.removeFreeSessions();
            this.LOGGER.info("cleanUnuseSessionPool:{},{}", removeFreeSessions.size(),
                    toCloseDeviceSessionPool.getNetconfAccessInfo());
            for(SessionWrapper removeFreeSession : removeFreeSessions) {
                if(null != removeFreeSession) {
                    closeNetconfSession(removeFreeSession.getSession());
                }
            }
        }
    }

    /**
     * Close free sessions
     * 
     * @author
     */
    private void closeFreeSession() {
        lockWithoutTimeout();
        try {
            boolean success = false;
            for(DeviceSessionPool da = this.freeDevSessionPoolQueue.poll(); null != da; da =
                    this.freeDevSessionPoolQueue.poll()) {
                List<SessionWrapper> removeFreeSessions = da.removeFreeSessions();
                this.LOGGER.warn("closeFreeSession:{},{}", removeFreeSessions.size(), da.getNetconfAccessInfo());
                for(SessionWrapper removeFreeSession : removeFreeSessions) {
                    if(null == removeFreeSession) {
                        continue;
                    }
                    if(closeNetconfSession(removeFreeSession.getSession())) {
                        success = true;
                    }
                }
                if(success) {
                    return;
                }
            }
        } finally {
            unlock();
        }
    }

    /**
     * Colse netconf session<br>
     * 
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     * @param session
     * @return true: close the session successfully
     */
    private boolean closeNetconfSession(ISession session) {
        if(null == session) {
            this.LOGGER.info("closeNetconfSession session null.");
            return false;
        }

        boolean success = false;
        success = session.close();
        if(success) {
            int decrementSessionCounter = decrementTotalSessionNum();
            this.LOGGER.warn("close session success : " + session + ", counter : " + decrementSessionCounter);
        }
        return success;
    }

    /**
     * book a new session<br>
     * 
     * @author
     * @param da DeviceSessionPool
     * @param netconfAccessInfo netconfAccessInfo
     * @param createFirst
     * @param deadLine
     * @param createThreadName
     * @return NETCONF session
     * @throws NetconfException
     */
    public ISession bookSession(DeviceSessionPool da, T netconfAccessInfo, boolean createFirst, final long deadLine,
            String createThreadName) throws NetconfException {
        SessionWrapper sessionWrapper = da.bookSession(createFirst, deadLine);
        boolean success = false;
        try {
            ISession session = sessionWrapper.getSession();
            if(null != session) {
                if(!createFirst) {
                    if(session.isConnected()) {
                        this.LOGGER.warn("reuse session: " + session);
                        success = true;
                        return session;
                    } else {
                        this.LOGGER.warn("session not connected: " + session);
                    }
                }
                closeNetconfSession(session);
            }
            ISession newSession = createNetconfSession(netconfAccessInfo, deadLine, createThreadName);
            sessionWrapper.setSession(newSession);
            success = true;
            return newSession;
        } finally {
            if(!success) {
                da.freeSession(sessionWrapper);
            }
        }
    }

    /**
     * Create a netconf session<br>
     * 
     * @author
     * @param netconfAccessInfo
     * @param deadLine
     * @param createThreadName
     * @return NETCONF session
     * @throws NetconfException
     */
    @SuppressWarnings("unchecked")
    private ISession createNetconfSession(T netconfAccessInfo, final long deadLine, String createThreadName)
            throws NetconfException {
        ISession newSession = null;
        int newTotalSessionNum = tryIncrementTotalSessionNum(deadLine);
        boolean success = false;
        try {
            newSession = this.sessionFactory.createSession(netconfAccessInfo, createThreadName);
            if(null == newSession) {
                throw new NetconfException(NetconfErrCode.SESSION_UNAVAILABLE,
                        new StringBuilder().append("session is null.").append(netconfAccessInfo).append(",")
                                .append(this.sessionFactory).toString());
            }
            newSession.addSessionListener(this);
            newSession.connect();
            this.LOGGER.warn("get session success.{},{}", newSession.getNetconfAccessInfo().getDevIp(),
                    newTotalSessionNum);
            success = true;
            return newSession;
        } finally {
            if(!success) {
                try {
                    if(null != newSession) {
                        newSession.close();
                    }
                } finally {
                    decrementTotalSessionNum();
                }
            }
        }
    }

    /**
     * Decrement total session count <br>
     * 
     * @author
     * @return int
     */
    private int decrementTotalSessionNum() {
        synchronized(this.totalSessionNumMonitor) {
            int curCounter = --this.totalSessionNum;
            this.totalSessionNumMonitor.notifyAll();
            return curCounter;
        }
    }

    /**
     * Default deadline<br>
     * 
     * @author
     * @return long
     */
    private long defaultDeadLine() {
        return System.currentTimeMillis() + Netconf.getDefaultLoginTimeout();
    }

    /**
     * getCleanerInfo<br>
     * 
     * @author
     * @param stringBuilder
     */
    private void getCleanerInfo(final StringBuilder stringBuilder) {
        stringBuilder.append("-----begin dump SessionPoolImpl").append(NetconfContants.NEXT_LINE);
        if(null != this.immediateCleanerThread) {
            stringBuilder.append("------begin dump stack of ").append(this.immediateCleanerThread.getName())
                    .append(NetconfContants.NEXT_LINE);
            for(StackTraceElement stack : this.immediateCleanerThread.getStackTrace()) {
                stringBuilder.append(stack).append(NetconfContants.NEXT_LINE);
            }
            stringBuilder.append("------end dump stack of ").append(this.immediateCleanerThread.getName())
                    .append(NetconfContants.NEXT_LINE);
        }
        if(null != this.longFreeCleanerThread) {
            stringBuilder.append("------begin dump stack of ").append(this.longFreeCleanerThread.getName())
                    .append(NetconfContants.NEXT_LINE);
            for(StackTraceElement stack : this.longFreeCleanerThread.getStackTrace()) {
                stringBuilder.append(stack).append(NetconfContants.NEXT_LINE);
            }
            stringBuilder.append("------end dump stack of ").append(this.longFreeCleanerThread.getName())
                    .append(NetconfContants.NEXT_LINE);
        }
        stringBuilder.append("-----end dump SessionPoolImpl").append(NetconfContants.NEXT_LINE);
    }

    /**
     * entryCallor<br>
     * 
     * @author
     */
    private void entryCallor() {
        this.callors.add(Thread.currentThread());
    }

    /**
     * exitCallor ‹<br>
     * 
     * @author
     */
    private void exitCallor() {
        this.callors.remove(Thread.currentThread());
    }

    /**
     * Get current thread information<br>
     * 
     * @author
     * @return String
     */
    private String getCurThreadsInfo() {
        final StringBuilder stringBuilder = new StringBuilder(CommonConstant.DEFAULT_STRING_LENGTH_128);
        stringBuilder.append("there is a deadlock problem, begin dump the callor's stack")
                .append(NetconfContants.NEXT_LINE);

        for(Thread callor : this.callors) {
            stringBuilder.append("------begin dump stack of ").append(callor.getName())
                    .append(NetconfContants.NEXT_LINE);
            for(StackTraceElement stack : callor.getStackTrace()) {
                stringBuilder.append(stack).append(NetconfContants.NEXT_LINE);
            }
            stringBuilder.append("------end dump stack of ").append(callor.getName()).append(NetconfContants.NEXT_LINE);
        }
        getCleanerInfo(stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * Get device session pool <br>
     * 
     * @author
     * @param netconfAccessInfo
     * @return DeviceSessionPool
     */
    private DeviceSessionPool getDevSessionPool(NetconfAccessInfo netconfAccessInfo) {
        lockWithoutTimeout();
        try {
            return this.deviceSessionPools.get(netconfAccessInfo);
        } finally {
            unlock();
        }
    }

    /**
     * Get device session pool <br>
     * 
     * @author
     * @param netconfAccessInfo
     * @param priority
     * @param deadLine
     * @return DeviceSessionPool
     * @throws LockFailedException
     */
    private DeviceSessionPool getDevSessionPool(T netconfAccessInfo, Netconf.SESSION_PRIORITY priority, long deadLine)
            throws LockFailedException {
        lock(deadLine);
        try {
            DeviceSessionPool da = this.deviceSessionPools.get(netconfAccessInfo);
            if(null == da) {
                da = new DeviceSessionPool(netconfAccessInfo, netconfAccessInfo.getCommArg().getMaxConnNum());
                da.access();
                this.deviceSessionPools.put(netconfAccessInfo, da);
            }

            if(Netconf.SESSION_PRIORITY.HIGH.equals(priority) && this.freeDevSessionPoolQueue.remove(da)) {
                da.access();
                this.freeDevSessionPoolQueue.add(da);
            }
            da.cleanUnuseTimes();
            return da;
        } finally {
            unlock();
        }
    }

    /**
     * Get netconf Session<br>
     * 
     * @author
     * @param netconfAccessInfo
     * @param createFirst
     * @param createThreadName
     * @return NETCONF Session
     * @throws NetconfException
     */
    private ISession getSession(T netconfAccessInfo, boolean createFirst, String createThreadName)
            throws NetconfException {
        entryCallor();
        try {
            long deadLine = defaultDeadLine();
            DeviceSessionPool da = getDevSessionPool(netconfAccessInfo, Netconf.SESSION_PRIORITY.HIGH, deadLine);
            return bookSession(da, netconfAccessInfo, createFirst, deadLine, createThreadName);
        } catch(LockFailedException e) {
            this.LOGGER.error(getCurThreadsInfo(), e);
            throw new NetconfException(NetconfErrCode.SESSION_UNAVAILABLE, "getSession ex." + netconfAccessInfo, e);
        } finally {
            exitCallor();
        }
    }

    /**
     * lock<br>
     * 
     * @author
     * @param deadLine
     * @throws LockFailedException
     */
    private boolean lock(final long deadLine) throws LockFailedException {
        long curTime = System.currentTimeMillis();
        final long timeout = deadLine - curTime;
        try {
            if(!this.lockObj.tryLock(timeout, TimeUnit.MILLISECONDS)) {
                throw new LockFailedException(new StringBuilder().append("timeout=").append(timeout).append(",curTime=")
                        .append(curTime).append(",deadLine=").append(deadLine).toString());
            }
        } catch(InterruptedException e) {
            throw new LockFailedException(e);
        }
        return true;
    }

    /**
     * lock without the timeout<br>
     * 
     * @author
     */
    private void lockWithoutTimeout() {
        this.lockObj.lock();
    }

    /**
     * Notify to release unused sessions<br>
     * 
     * @author
     */
    private void notifyReleaseUnUsedSession() {
        synchronized(this.cleanerMonitor) {
            ++this.needRelease;
            this.cleanerMonitor.notifyAll();
        }
    }

    /**
     * release unused sessions
     * 
     * @author
     */
    public void releaseUnUsedSession() {
        notifyReleaseUnUsedSession();
    }

    /**
     * Increment total session number<br>
     * 
     * @author
     * @param deadLine
     * @return int
     * @throws NetconfException
     */
    private int tryIncrementTotalSessionNum(final long deadLine) throws NetconfException {
        synchronized(this.totalSessionNumMonitor) {
            while(MAX_SESSION_LIMIT <= this.totalSessionNum) {
                if(System.currentTimeMillis() > deadLine) {
                    this.LOGGER.error("totalSessionNum reach max:{}", this.totalSessionNum);
                    throw new NetconfException(NetconfErrCode.SESSION_UNAVAILABLE);
                }

                notifyReleaseUnUsedSession();
                try {
                    this.totalSessionNumMonitor.wait(NetconfContants.ONE_SECOND);
                } catch(InterruptedException e) {
                    this.LOGGER.error("tryIncrementTotalSessionNum wait ex.", e);
                    throw new NetconfException(NetconfErrCode.SESSION_UNAVAILABLE);
                }
            }
            return ++this.totalSessionNum;
        }
    }

    /**
     * unlock the object<br>
     * 
     * @author
     */
    private void unlock() {
        this.lockObj.unlock();
    }

    /**
     * Long free cleaner class<br>
     * 
     * @author
     */
    private class LongFreeCleaner implements Runnable {

        /**
         * @see java.lang.Runnable#run()
         * @author
         */
        @Override
        public void run() {
            boolean success = false;
            while(true) {
                success = false;
                try {
                    TimeUnit.SECONDS.sleep(ONE_MINUTE);
                    cleanUnuseSessionPool();
                    success = true;
                } catch(InterruptedException e) {
                    CacheSessionPool.this.LOGGER.error("LongFreeCleaner ex.", e);
                } finally {
                    if(!success) {
                        CacheSessionPool.this.LOGGER.info("LongFreeCleaner error.");
                    }
                }
            }
        }
    }

    /**
     * Immediate cleaner
     * 
     * @author
     */
    private class ImmediateCleaner implements Runnable {

        /**
         * @see java.lang.Runnable#run()
         * @author
         */
        @Override
        public void run() {
            boolean success = false;
            while(true) {
                success = false;
                try {
                    boolean needReleaseMonitor = false;
                    synchronized(CacheSessionPool.this.cleanerMonitor) {
                        if(CacheSessionPool.this.needRelease > 0) {
                            needReleaseMonitor = true;
                            --CacheSessionPool.this.needRelease;
                        } else {
                            CacheSessionPool.this.cleanerMonitor.wait();
                        }
                    }
                    if(needReleaseMonitor) {
                        closeFreeSession();
                    }
                    success = true;
                } catch(InterruptedException e) {
                    CacheSessionPool.this.LOGGER.error("ImmediateCleaner ex.", e);
                } finally {
                    if(!success) {
                        CacheSessionPool.this.LOGGER.info("ImmediateCleaner error.");
                    }
                }
            }
        }
    }
}
