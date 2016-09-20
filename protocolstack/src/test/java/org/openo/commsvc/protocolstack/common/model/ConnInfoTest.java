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

package org.openo.commsvc.protocolstack.common.model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class ConnInfoTest {

    Map<String, String> commParamMap = new HashMap<>();

    ConnInfo connInfo = new ConnInfo(commParamMap);
    Map<String, String> commParamMap1 = new HashMap<>();

    ConnInfo connInfo1 = new ConnInfo(commParamMap1);
    ConnInfo[] connInfoArray;
    ConnInfoQueryMsg connInfoQueryMsg = new ConnInfoQueryMsg(connInfoArray);

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.common.model.ConnInfo#getCommParam(java.lang.String)}.
     */
    @Test
    public void testGetCommParam() {
        String key = "key";
        commParamMap.put(key, "value");
        connInfo.getCommParam(key);
        assertTrue(connInfo.getCommParam(key)!=null);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.common.model.ConnInfo#getControllerId()}.
     */
    @Test
    public void testGetControllerId() {
        String controllerId = "controllerId";
        connInfo.setControllerId(controllerId);
        connInfo.getControllerId();
        assertTrue(connInfo.getControllerId()!=null);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.model.ConnInfo#getHostName()}.
     */
    @Test
    public void testGetHostName() {
        String hostName = "hostName";
        connInfo.setHostName(hostName);
        connInfo.getHostName();
        assertTrue(connInfo.getHostName()!=null);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.model.ConnInfo#getProtocol()}.
     */
    @Test
    public void testGetProtocol() {
        String protocol = "protocol";
        connInfo.setProtocol(protocol);
        connInfo.getProtocol();
        assertTrue(connInfo.getProtocol()!=null);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.common.model.ConnInfo#getMaxConnectionsPerClient()}.
     */
    @Test
    public void testGetMaxConnectionsPerClient() {
        int maxConnectionsPerClient = 1;
        connInfo.setMaxConnectionsPerClient(maxConnectionsPerClient);
        connInfo.getMaxConnectionsPerClient();
        assertTrue(connInfo.getMaxConnectionsPerClient()!=0);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.model.ConnInfo#getPort()}.
     */
    @Test
    public void testGetPort() {
        int port = 1;
        connInfo.setPort(port);
        connInfo.getPort();
        assertTrue(connInfo.getPort()!=0); 
    }
   @Test
    public void testGetConnections() {
        String controllerId = "controllerId";
        connInfo.setControllerId(controllerId);
        connInfo.setHostName("hostName");
        connInfo.setMaxConnectionsPerClient(1);
        connInfo.setPort(1);
        connInfo.setProtocol("protocol");
        String controllerId1 = "controllerId1";
        connInfo1.setControllerId(controllerId1);
        connInfo1.setHostName("hostName");
        connInfo1.setMaxConnectionsPerClient(1);
        connInfo1.setPort(1);
        connInfo1.setProtocol("protocol");
        connInfoArray = new ConnInfo[10];
       connInfoArray[0] = connInfo;
       connInfoArray[1] = connInfo1;
       connInfoQueryMsg.getConnections();
     
    }
}
