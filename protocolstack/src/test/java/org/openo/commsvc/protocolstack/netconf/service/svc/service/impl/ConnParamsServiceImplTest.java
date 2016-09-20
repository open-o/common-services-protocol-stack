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

package org.openo.commsvc.protocolstack.netconf.service.svc.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openo.commsvc.protocolstack.common.model.ConnInfo;
import org.openo.commsvc.protocolstack.common.model.ConnInfoQueryMsg;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.impl.ConnParamsMgr;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISessionFatory;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool;

public class ConnParamsServiceImplTest {

    ConnParamsServiceImpl connParamsServiceImpl = new ConnParamsServiceImpl();

    ConnParamsMgr connParamsMgr = new ConnParamsMgr();

    private ConnInfo[] connInfoList;

    ConnInfoQueryMsg connInfoMsg = new ConnInfoQueryMsg(connInfoList);

    String controllerId = "controllerId";

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.service.impl.ConnParamsServiceImpl#updateConnParams(org.openo.commsvc.protocolstack.common.model.ConnInfoQueryMsg)}.
     */
    @Test
    public void testUpdateConnParams() {
        CommunicateArg commArg = new CommunicateArg("ip", "password", 1, "userName");
        ConnInfo[] connInfoList1;

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
        NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo(commArg);
        netconfAccessInfo.setCommArg(commArg);
        netconfAccessInfo.setControllerId(controllerId);
        CacheSessionPool<NetconfAccessInfo> cacheSessionPool = new CacheSessionPool<NetconfAccessInfo>();
        ISessionFatory sessionFactory = null;
        cacheSessionPool.setSessionFactory(sessionFactory);
        connParamsMgr.setCacheSessionPool(cacheSessionPool);
        Map<String, String> commParamMap = new HashMap<>();
        String key = "key";
        commParamMap.put(key, "value");
        ConnInfo connInfo = new ConnInfo(commParamMap);
        String controllerId = "controllerId";
        connInfo.setControllerId(controllerId);
        connInfo.setHostName("hostName");
        connInfo.setMaxConnectionsPerClient(3);
        connInfo.setPort(16);
        connInfo.setProtocol("protocol");
        String controllerId1 = "controllerId1";
        ConnInfo connInfo1 = new ConnInfo(commParamMap);
        connInfo1.setControllerId(controllerId1);
        connInfo1.setHostName("hostName");
        connInfo1.setMaxConnectionsPerClient(6);
        connInfo1.setPort(15);
        connInfo1.setProtocol("protocol");
        connInfoList1 = new ConnInfo[2];
        connInfoList1[0] = connInfo;
        connInfoList1[1] = connInfo1;
        ConnInfoQueryMsg connInfoMsg = new ConnInfoQueryMsg(connInfoList1);
        connParamsMgr.updateConnParams(connInfoMsg);
        connParamsServiceImpl.setConnParamsMgr(connParamsMgr);
        connParamsServiceImpl.updateConnParams(connInfoMsg);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.service.impl.ConnParamsServiceImpl#getMaxConnNum(java.lang.String)}.
     */
    @Test
    public void testGetMaxConnNum() {
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.service.impl.ConnParamsServiceImpl#getConnParamsMgr()}.
     */
    @Test
    public void testGetConnParamsMgr() {
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.service.impl.ConnParamsServiceImpl#setConnParamsMgr(org.openo.commsvc.protocolstack.netconf.service.svc.business.impl.ConnParamsMgr)}.
     */
    @Test
    public void testSetConnParamsMgr() {
    }

}
