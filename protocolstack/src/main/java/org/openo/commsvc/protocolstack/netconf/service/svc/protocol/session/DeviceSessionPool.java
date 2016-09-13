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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.Netconf;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.NetconfContants;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfErrCode;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DeviceSessionPool device session pool
 * 
 * @author
 */
class DeviceSessionPool implements Comparable<DeviceSessionPool> {

    /**
     * Id
     */
    private static AtomicInteger idGen = new AtomicInteger();

    /**
     * init access time
     */
    private static final int INIT_ACCESS_TIME = 0;

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSessionPool.class);

    /**
     * N
     */
    private static final int N = Netconf.getCachePolicyLastAccessCounter();

    /**
     * equal priority
     */
    private static final int PRI_EQ = 0;

    /**
     * High priority
     */
    private static final int PRI_HIGH = 1;

    /**
     * low priority
     */
    private static final int PRI_LOW = -1;

    /**
     * Increment Id
     */
    private int id = idGen.getAndIncrement();

    /**
     * lastAccessSessionIndex
     */
    private int lastAccessSessionIndex = 0;

    /**
     * lastAccessTimes
     */
    private final long[] lastAccessTimes = new long[N];

    /**
     * Netconf server session information
     */
    private final NetconfAccessInfo netconfAccessInfo;

    /**
     * Session wrappers
     */
    private final SessionWrapper[] sessions;

    /**
     * sessionsMonitor
     */
    private final Object sessionsMonitor = new boolean[0];

    /**
     * unuseTimes
     */
    private AtomicInteger unuseTimes = new AtomicInteger(0);

    /**
     * weight
     */
    private long weight = 0;

    /**
     * weightMonitor
     */
    private final Object weightMonitor = new Object();

    /**
     * Constructor <br>
     * <br>
     * 
     * @author
     * @param netconfAccessInfo
     * @param sessionLimit
     */
    DeviceSessionPool(final NetconfAccessInfo netconfAccessInfo, final int sessionLimit) {
        this.netconfAccessInfo = netconfAccessInfo;
        this.sessions = new SessionWrapper[sessionLimit];
        Arrays.fill(this.lastAccessTimes, INIT_ACCESS_TIME);
    }

    /**
     * compareTo<br>
     * <br>
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * @author
     */
    @Override
    public int compareTo(DeviceSessionPool that) {
        final long weigth0 = getWeight();
        final long weigth1 = that.getWeight();
        if(weigth0 > weigth1) {
            return PRI_HIGH;
        } else if(weigth0 == weigth1) {
            return PRI_EQ;
        } else {
            return PRI_LOW;
        }
    }

    /**
     * Checks equality based DeviceSessionPool key<br>
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * @author
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        DeviceSessionPool other = (DeviceSessionPool)obj;
        return this.id == other.id;
    }

    /**
     * hashCode<br>
     * <br>
     * 
     * @see java.lang.Object#hashCode()
     * @author
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.id;
        return result;
    }

    /**
     * toString<br>
     * <br>
     * 
     * @see java.lang.Object#toString()
     * @author
     */
    @Override
    public String toString() {

        String tmpString = "DeviceSessionPool[" + this.id + "," + getWeight() + "," + this.netconfAccessInfo
                + NetconfContants.NEXT_LINE;

        synchronized(this.sessionsMonitor) {
            for(SessionWrapper sessionWrapper : this.sessions) {
                tmpString += "session:" + sessionWrapper + NetconfContants.NEXT_LINE;
            }
        }
        return tmpString;
    }

    /**
     * access
     * 
     * @author
     */
    void access() {
        if(this.lastAccessSessionIndex >= N) {
            this.lastAccessSessionIndex = 0;
        }
        this.lastAccessTimes[this.lastAccessSessionIndex++] = System.currentTimeMillis();

        synchronized(this.weightMonitor) {
            this.weight = 0;
            for(int i = 0; i < N; ++i) {
                this.weight += this.lastAccessTimes[i];
            }
        }
    }

    /**
     * book a new session
     * 
     * @author
     * @param createFirst
     * @param deadLine
     * @return SessionWrapper
     * @throws NetconfException
     */
    public SessionWrapper bookSession(boolean createFirst, final long deadLine) throws NetconfException {
        synchronized(this.sessionsMonitor) {
            do {
                final SessionWrapper sessionWrapper = createFirst ? bookSessionCreateFirst() : bookSessionFreeFirst();
                if(null != sessionWrapper) {
                    return sessionWrapper;
                }

                try {
                    this.sessionsMonitor.wait(NetconfContants.ONE_SECOND);
                } catch(InterruptedException e) {
                    LOGGER.error("bookSession ex." + this.netconfAccessInfo.getDevIp(), e);
                    throw new NetconfException(NetconfErrCode.SESSION_UNAVAILABLE,
                            "bookSession ex." + this.netconfAccessInfo);
                }

                if(System.currentTimeMillis() > deadLine) {
                    LOGGER.error("booksession timeout:{}", this.netconfAccessInfo.getDevIp());
                    throw new NetconfException(NetconfErrCode.SESSION_UNAVAILABLE,
                            "booksession timeout." + this.netconfAccessInfo);
                }
            } while(true);
        }
    }

    /**
     * Clean unuse times<br>
     * 
     * @author
     */
    void cleanUnuseTimes() {
        this.unuseTimes.set(0);
    }

    /**
     * Free session<br>
     * 
     * @author
     * @param sessionWrapper
     */
    void freeSession(SessionWrapper sessionWrapper) {
        if(null == sessionWrapper) {
            return;
        }
        synchronized(this.sessionsMonitor) {
            sessionWrapper.free();
            this.sessionsMonitor.notifyAll();
        }
    }

    /**
     * Free session <br>
     * 
     * @author
     * @param session
     * @return true: if session is free
     */
    boolean freeSession(ISession session) {
        synchronized(this.sessionsMonitor) {
            for(SessionWrapper sessionWrapper : this.sessions) {
                if(null == sessionWrapper) {
                    continue;
                }

                if(session.equals(sessionWrapper.getSession())) {
                    sessionWrapper.free();
                    this.sessionsMonitor.notifyAll();
                    return true;
                }
            }
        }

        LOGGER.info("freeSession not find:{}", session);
        return false;
    }

    /**
     * @return Returns the id.
     */
    int getId() {
        return this.id;
    }

    /**
     * @return Returns the NetconfAccessInfo.
     */
    NetconfAccessInfo getNetconfAccessInfo() {
        return this.netconfAccessInfo;
    }

    /**
     * Increase unuse times<br>
     * 
     * @author
     */
    int increaseUnuseTimes() {
        return this.unuseTimes.incrementAndGet();
    }

    /**
     * Remove free session<br>
     * 
     * @author
     * @return List<SessionWrapper>
     */
    List<SessionWrapper> removeFreeSessions() {
        List<SessionWrapper> ret = new ArrayList<SessionWrapper>();
        synchronized(this.sessionsMonitor) {
            for(int i = 0; i < this.sessions.length; ++i) {
                if(null == this.sessions[i]) {
                    continue;
                }

                if(this.sessions[i].isFree()) {
                    ret.add(this.sessions[i]);
                    this.sessions[i] = null;
                }
            }
        }
        return ret;
    }

    /**
     * Remove session<br>
     * 
     * @author
     * @param session
     * @return true: if session is removed
     */
    boolean removeSession(ISession session) {
        synchronized(this.sessionsMonitor) {
            for(int i = 0; i < this.sessions.length; ++i) {
                if(null == this.sessions[i]) {
                    continue;
                }

                if(session.equals(this.sessions[i].getSession())) {
                    this.sessions[i] = null;
                    this.sessionsMonitor.notifyAll();
                    return true;
                }
            }
        }

        LOGGER.info("removeSession not find:{}", session);
        return false;
    }

    /**
     * Book session<br>
     * 
     * @author
     * @return SessionWrapper
     */
    private SessionWrapper bookSessionCreateFirst() {
        Integer minFreeIndex = null;
        for(int i = 0; i < this.sessions.length; ++i) {
            if(null == this.sessions[i]) {
                this.sessions[i] = new SessionWrapper();
                this.sessions[i].setUsing();
                return this.sessions[i];
            }
            if(this.sessions[i].isFree() && (null == minFreeIndex)) {
                minFreeIndex = i;
            }
        }

        if(null == minFreeIndex) {
            return null;
        }

        this.sessions[minFreeIndex].setUsing();
        return this.sessions[minFreeIndex];
    }

    /**
     * Book session
     * 
     * @author
     * @return SessionWrapper
     */
    private SessionWrapper bookSessionFreeFirst() {
        Integer minNullIndex = null;
        for(int i = 0; i < this.sessions.length; ++i) {
            if(null == this.sessions[i]) {
                if(null == minNullIndex) {
                    minNullIndex = i;
                }
                continue;
            }
            if(this.sessions[i].isFree()) {
                this.sessions[i].setUsing();
                return this.sessions[i];
            }
        }

        if(null == minNullIndex) {
            return null;
        }

        this.sessions[minNullIndex] = new SessionWrapper();
        this.sessions[minNullIndex].setUsing();
        return this.sessions[minNullIndex];
    }

    /**
     * Get available session number<br>
     * 
     * @author
     * @return int
     */
    public int getAvailableSessionNum() {
        int totalNum = 0;
        for(SessionWrapper session : sessions) {
            if(null != session) {
                totalNum++;
            }
        }

        return totalNum;
    }

    /**
     * get monitor weight
     * 
     * @author
     * @return long
     */
    private long getWeight() {
        synchronized(this.weightMonitor) {
            return this.weight;
        }
    }

}
