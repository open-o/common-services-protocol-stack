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

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.Netconf;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISessionFatory;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;

import mockit.Expectations;
import mockit.Mocked;

public class CacheSessionPoolTest {

    int INIT_PRIORITY_QUEUE_SIZE = 800;

    int MAX_SESSION_LIMIT = Netconf.getMaxHoldConnections();

    int ONE_MINUTE = 60;

    Set<Thread> callors = new ConcurrentSkipListSet<Thread>(new Comparator<Thread>() {

        @Override
        public int compare(Thread arg0, Thread arg1) {
            return (int)(arg0.getId() - arg1.getId());
        }
    });

    Object cleanerMonitor = new Object();

    Thread ImmediateCleanerThread;

    Thread longFreeCleanerThread;

    PriorityQueue<DeviceSessionPool> freeDevSessionPoolQueue =
            new PriorityQueue<DeviceSessionPool>(INIT_PRIORITY_QUEUE_SIZE);

    Lock lockObj = new ReentrantLock();

    int needRelease = 0;

    int totalSessionNum = 0;

    Object totalSessionNumMonitor = new Object();

    CacheSessionPool cacheSessionPool = new CacheSessionPool();

    String controllerId = "controllerId";

    @Mocked
    ISession session;

    @Mocked
    ISessionFatory<NetconfAccessInfo> sessionFactory;

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool#close(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession)}.
     * 
     * @throws NetconfException
     */
    @Test
    public void testCloseISession() throws NetconfException {
        CommunicateArg commArg = new CommunicateArg("10.1.1.1", "password", 20, "userName");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");
        commArg.setControllerId(controllerId);
        commArg.setCreateTime("createTime");
        commArg.setDescription("description");
        commArg.setIp("ip");
        commArg.setLoginTimeout(1);
        commArg.setMaxConnNum(1);
        commArg.setName("name");
        commArg.setPassword("password");
        commArg.setPort(1);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName("userName");
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        final NetconfAccessInfo na = new NetconfAccessInfo("1234", "1.1.1.1");
        na.setCommArg(commArg);
        new Expectations() {

            {
                session.getNetconfAccessInfo();
                result = na;
            }
        };
        new Expectations() {

            {
                sessionFactory.createSession(na, anyString);
                result = session;
            }
        };
        cacheSessionPool.setSessionFactory(sessionFactory);
        cacheSessionPool.getSession(na, "createThreadName");
        cacheSessionPool.testConnective(na);
        cacheSessionPool.close(this.session);
    }

    @Test
    public void testCloseISessionExcp() throws NetconfException {
        CommunicateArg ca = new CommunicateArg("10.1.1.1", "password", 20, "userName");

        final NetconfAccessInfo na = new NetconfAccessInfo("1234", "1.1.1.1");
        na.setCommArg(ca);
        DeviceSessionPool da = new DeviceSessionPool(na, 3);
        da.access();
        da.bookSession(true, MAX_SESSION_LIMIT);
        new Expectations() {

            {
                session.getNetconfAccessInfo();
                result = na;
            }
        };
        new Expectations() {

            {
                sessionFactory.createSession(na, anyString);
                result = session;
            }
        };
        cacheSessionPool.setSessionFactory(sessionFactory);
        cacheSessionPool.getSession(na, "createThreadName");
        cacheSessionPool.close(this.session);
    }

    @Test
    public void testCloseISessionExcpSessionWrapper() throws NetconfException {
        CommunicateArg ca = new CommunicateArg("10.1.1.1", "password", 20, "userName");

        final NetconfAccessInfo na = new NetconfAccessInfo("1234", "1.1.1.1");
        na.setCommArg(ca);
        final DeviceSessionPool da = new DeviceSessionPool(na, 3);
        SessionWrapper sessionWrapper = null;
        da.access();
        da.bookSession(true, MAX_SESSION_LIMIT);
        new Expectations() {

            {
                session.getNetconfAccessInfo();
                result = na;
            }
        };
        new Expectations() {

            {
                sessionFactory.createSession(na, anyString);
                result = session;
            }
        };
        cacheSessionPool.setSessionFactory(sessionFactory);
        cacheSessionPool.getSession(na, "createThreadName");
        cacheSessionPool.close(this.session);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool#close(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo)}.
     */
    @Test
    public void testCloseT() {
        CommunicateArg ca = new CommunicateArg("10.1.1.1", "password", 20, "userName");
        NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo("1234", "1.1.1.1");
        netconfAccessInfo.setCommArg(ca);
        DeviceSessionPool deviceSessionPool = new DeviceSessionPool(netconfAccessInfo, MAX_SESSION_LIMIT);
        cacheSessionPool.close(netconfAccessInfo);
    }

    @Test
    public void testCloseTDeviceSessionPoolNull() {
        CommunicateArg ca = new CommunicateArg("10.1.1.1", "password", 20, "userName");

        NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo("1234", "1.1.1.1");
        netconfAccessInfo.setCommArg(ca);
        cacheSessionPool.close(netconfAccessInfo);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool#disconnect(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession)}.
     * 
     * @throws NetconfException
     */
    @Test
    public void testDisconnect() throws NetconfException {
        CommunicateArg commArg = new CommunicateArg("10.1.1.1", "password", 20, "userName");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");
        commArg.setControllerId(controllerId);
        commArg.setCreateTime("createTime");
        commArg.setDescription("description");
        commArg.setIp("ip");
        commArg.setLoginTimeout(1);
        commArg.setMaxConnNum(1);
        commArg.setName("name");
        commArg.setPassword("password");
        commArg.setPort(1);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName("userName");
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        final NetconfAccessInfo na = new NetconfAccessInfo("1234", "1.1.1.1");
        na.setCommArg(commArg);
        new Expectations() {

            {
                session.getNetconfAccessInfo();
                result = na;
            }
        };
        new Expectations() {

            {
                sessionFactory.createSession(na, anyString);
                result = session;
            }
        };
        cacheSessionPool.setSessionFactory(sessionFactory);
        cacheSessionPool.getSession(na, "createThreadName");
        cacheSessionPool.close(session);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool#getSession(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo, java.lang.String)}.
     * 
     * @throws NetconfException
     */
    @Test
    public void testGetSession() throws NetconfException {
        CommunicateArg commArg = new CommunicateArg("10.1.1.1", "password", 20, "userName");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");
        commArg.setControllerId(controllerId);
        commArg.setCreateTime("createTime");
        commArg.setDescription("description");
        commArg.setIp("ip");
        commArg.setLoginTimeout(1);
        commArg.setMaxConnNum(1);
        commArg.setName("name");
        commArg.setPassword("password");
        commArg.setPort(1);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName("userName");
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        final NetconfAccessInfo na = new NetconfAccessInfo("1234", "1.1.1.1");
        na.setCommArg(commArg);
        new Expectations() {

            {
                session.getNetconfAccessInfo();
                result = na;
            }
        };
        new Expectations() {

            {
                sessionFactory.createSession(na, anyString);
                result = session;
            }
        };
        cacheSessionPool.setSessionFactory(sessionFactory);
        cacheSessionPool.getSession(na, "createThreadName");
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool#releaseSession(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession)}.
     * 
     * @throws NetconfException
     */
    @Test
    public void testReleaseSession() throws NetconfException {
        CommunicateArg commArg = new CommunicateArg("10.1.1.1", "password", 20, "userName");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");
        commArg.setControllerId(controllerId);
        commArg.setCreateTime("createTime");
        commArg.setDescription("description");
        commArg.setIp("ip");
        commArg.setLoginTimeout(1);
        commArg.setMaxConnNum(1);
        commArg.setName("name");
        commArg.setPassword("password");
        commArg.setPort(1);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName("userName");
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        final NetconfAccessInfo na = new NetconfAccessInfo("1234", "1.1.1.1");
        na.setCommArg(commArg);
        new Expectations() {

            {
                session.getNetconfAccessInfo();
                result = na;
            }
        };
        cacheSessionPool.setSessionFactory(sessionFactory);
        cacheSessionPool.releaseSession(session);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool#testConnective(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo)}.
     */
    @Test
    public void testTestConnective() {
    }

    // /**
    // * Test method for
    // * {@link
    // org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool#bookSession(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.DeviceSessionPool,
    // org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo,
    // boolean, long, java.lang.String)}.
    // */
    // @Test
    // public void testBookSession() {
    // NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo("1234", "1.1.1.1");
    // DeviceSessionPool deviceSessionPool = new DeviceSessionPool(netconfAccessInfo, 16);
    //
    // CacheSessionPool<NetconfAccessInfo> cacheSessionPool = new CacheSessionPool<>();
    // cacheSessionPool.setSessionFactory(new DefaultSessionFactory());
    // try {
    // cacheSessionPool.bookSession(deviceSessionPool, netconfAccessInfo, true, 1000L,
    // "threadname");
    // } catch(NetconfException e) {
    //
    // } finally {
    // cacheSessionPool.releaseUnUsedSession();
    // }
    //
    // }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool#releaseUnUsedSession()}.
     */
    @Test
    public void testReleaseUnUsedSession() {
        NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo("1234", "1.1.1.1");
        DeviceSessionPool deviceSessionPool = new DeviceSessionPool(netconfAccessInfo, 16);

        CacheSessionPool<NetconfAccessInfo> cacheSessionPool = new CacheSessionPool<>();
        cacheSessionPool.releaseUnUsedSession();
    }
}
