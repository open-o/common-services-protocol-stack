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

package org.openo.commsvc.protocolstack.netconf.service.svc.business.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.openo.commsvc.protocolstack.common.model.ConnInfo;
import org.openo.commsvc.protocolstack.common.model.ConnInfoQueryMsg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool;

import mockit.Mock;
import mockit.MockUp;

public class ConnParamsMgrTest {

    ConnParamsMgr connParamsMgr = new ConnParamsMgr();

    CacheSessionPool<NetconfAccessInfo> cacheSessionPool = new CacheSessionPool<>();

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.business.impl.ConnParamsMgr#updateConnParams(org.openo.commsvc.protocolstack.common.model.ConnInfoQueryMsg)}.
     */

    @Before
    public void setup() {
        connParamsMgr.setCacheSessionPool(cacheSessionPool);
    }

    @Test
    public void testUpdateConnParams() {

        connParamsMgr.setCacheSessionPool(cacheSessionPool);

        ConnInfo[] connInfoArray;
        String controllerId = "1.1.1.1";
        Map<String, String> commParamMap = new HashMap<String, String>();
        String key = "key";
        commParamMap.put(key, "value");
        ConnInfo connInfo = new ConnInfo(commParamMap);
        connInfo.setControllerId(controllerId);
        connInfo.setHostName("hostName");
        connInfo.setMaxConnectionsPerClient(16);
        connInfo.setPort(1);
        connInfo.setProtocol("netconf");

        connInfoArray = new ConnInfo[1];
        connInfoArray[0] = connInfo;
        ConnInfoQueryMsg connInfoMsg = new ConnInfoQueryMsg(connInfoArray);
        new MockUp<Set>() {

            @Mock
            public <E> boolean add(E e) {
                return true;

            }
        };

        try {
            CommunicationArgumentMgr.addCommunicateArg(new CommunicateArg("1.1.1.1", "password", 1, "username"));
        } catch(NetconfException e1) {
        }
        connParamsMgr.updateConnParams(connInfoMsg);
    }

    @Test
    public void test_updateConnParams_controllerID_empty() {

        ConnInfo connInfos[] = new ConnInfo[1];

        Map<String, String> commParamMap = new HashMap<String, String>();
        ConnInfo connInfoWithoutCtrlId = new ConnInfo(commParamMap);
        connInfoWithoutCtrlId.setHostName("hostname");
        connInfoWithoutCtrlId.setMaxConnectionsPerClient(16);
        connInfoWithoutCtrlId.setPort(1);
        connInfoWithoutCtrlId.setProtocol("netconf");

        connInfos[0] = connInfoWithoutCtrlId;
        ConnInfoQueryMsg connInfoMsg = new ConnInfoQueryMsg(connInfos);

        connParamsMgr.updateConnParams(connInfoMsg);
    }

    @Test
    public void test_updateConnParams_host_empty() {
        ConnInfo connInfos[] = new ConnInfo[1];

        Map<String, String> commParamMap = new HashMap<String, String>();
        ConnInfo connInfoEmptyHost = new ConnInfo(commParamMap);
        connInfoEmptyHost.setControllerId("ctrlID");
        connInfoEmptyHost.setMaxConnectionsPerClient(16);
        connInfoEmptyHost.setPort(1);
        connInfoEmptyHost.setProtocol("netconf");

        connInfos[0] = connInfoEmptyHost;
        ConnInfoQueryMsg connInfoMsg = new ConnInfoQueryMsg(connInfos);

        connParamsMgr.updateConnParams(connInfoMsg);
    }

    @Test
    public void test_UpdateConnParams_with_zero_maxconnNum() {
        ConnInfo connInfos[] = new ConnInfo[1];

        Map<String, String> commParamMap = new HashMap<String, String>();
        ConnInfo connInfoWithZeroMaxConn = new ConnInfo(commParamMap);
        connInfoWithZeroMaxConn.setHostName("hostname");
        connInfoWithZeroMaxConn.setControllerId("1.1.1.1");
        connInfoWithZeroMaxConn.setPort(1);
        connInfoWithZeroMaxConn.setMaxConnectionsPerClient(0);
        connInfoWithZeroMaxConn.setProtocol("netconf");

        connInfos[0] = connInfoWithZeroMaxConn;
        ConnInfoQueryMsg connInfoMsg = new ConnInfoQueryMsg(connInfos);

        connParamsMgr.updateConnParams(connInfoMsg);
    }

    @Test
    public void test_UpdateConnParams_with_invalid_protocol() {
        ConnInfo connInfos[] = new ConnInfo[1];

        Map<String, String> commParamMap = new HashMap<String, String>();
        ConnInfo connInfoWithZeroMaxConn = new ConnInfo(commParamMap);
        connInfoWithZeroMaxConn.setHostName("hostname");
        connInfoWithZeroMaxConn.setControllerId("1.1.1.1");
        connInfoWithZeroMaxConn.setPort(1);
        connInfoWithZeroMaxConn.setMaxConnectionsPerClient(16);
        connInfoWithZeroMaxConn.setProtocol("protocol");

        connInfos[0] = connInfoWithZeroMaxConn;
        ConnInfoQueryMsg connInfoMsg = new ConnInfoQueryMsg(connInfos);

        connParamsMgr.updateConnParams(connInfoMsg);
    }

    @Test
    public void test_updateConnParams_with_invalidPort() {
        ConnInfo connInfos[] = new ConnInfo[1];

        Map<String, String> commParamMap = new HashMap<String, String>();
        ConnInfo connInfoWithinvalidPortAddr = new ConnInfo(commParamMap);
        connInfoWithinvalidPortAddr.setHostName("hostname");
        connInfoWithinvalidPortAddr.setControllerId("1.1.1.1");
        connInfoWithinvalidPortAddr.setMaxConnectionsPerClient(16);
        connInfoWithinvalidPortAddr.setPort(-1);
        connInfoWithinvalidPortAddr.setProtocol("netconf");

        connInfos[0] = connInfoWithinvalidPortAddr;
        ConnInfoQueryMsg connInfoMsg = new ConnInfoQueryMsg(connInfos);

        connParamsMgr.updateConnParams(connInfoMsg);
    }

    @Test
    public void test_updateConnParams_Empty_ConnInfo() {

        ConnInfo connInfos[] = null;
        ConnInfoQueryMsg queryMsg = new ConnInfoQueryMsg(connInfos);

        connParamsMgr.setCacheSessionPool(new CacheSessionPool<>());

        connParamsMgr.updateConnParams(queryMsg);
    }

    @Test
    public void test_UpdateConnParams_ConnInfoMsgIsNull() {
        // TODO: need to add the assert for the the loogger message
        connParamsMgr.updateConnParams(null);
    }

    @Test
    public void test_UpdateConnParams_ConnInfoEmpty() {

    }
}
