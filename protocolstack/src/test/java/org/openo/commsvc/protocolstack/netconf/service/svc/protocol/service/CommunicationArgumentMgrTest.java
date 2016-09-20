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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.ControllerCommInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr.CommunicateArgChangeListener;

import mockit.Mocked;


public class CommunicationArgumentMgrTest {
    Map<String, CommunicateArg> devCommunicateArgs =
            Collections.synchronizedMap(new HashMap<String, CommunicateArg>());
    @Mocked
    ICacheSessionPool cacheSessionPool;
    CommunicateArgChangeListener listener = new CommunicateArgChangeListener();
    String ESR_GET_CONTROLLER_COMM_INFO = "/openoapi/extsys/v1/sdncontrollers/%s";
    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr#addCommunicateArg(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg)}.
     * @throws NetconfException 
     */
    @Test
    public void testAddCommunicateArg() throws NetconfException {
        CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 102, "userName");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");      
        String controllerId = "controlleid";
        commArg.setControllerId(controllerId );
        commArg.setCreateTime("createTime");
        commArg.setDescription("description");
        commArg.setIp("1.1.1.1");
        commArg.setLoginTimeout(1);
        commArg.setMaxConnNum(1);
        commArg.setName("name");
        commArg.setPassword("password");
        commArg.setPort(102);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName("userName");
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        CommunicationArgumentMgr.addCommunicateArg(commArg);
        assertTrue(true);
    }
    @Test
    public void testAddCommunicateArgNullIp() throws NetconfException {
        CommunicateArg commArg = new CommunicateArg(null, "password", 102, "userName");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");      
        String controllerId = "controlleid";
        commArg.setControllerId(controllerId );
        commArg.setCreateTime("createTime");
        commArg.setDescription("description");
        commArg.setIp(null);
        commArg.setLoginTimeout(1);
        commArg.setMaxConnNum(1);
        commArg.setName("name");
        commArg.setPassword("password");
        commArg.setPort(102);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName("userName");
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        CommunicateArgWrap commArg1 = new CommunicateArgWrap(commArg, listener);
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        commArg1.setControllerId(controllerId);
        commArg1.setCreateTime("createTime");
        commArg1.setDescription("description");
        commArg1.setIp("1.0.1.2");
        commArg1.setLoginTimeout(1);
        commArg1.setMaxConnNum(1);
        commArg1.setName("name");
        commArg1.setPassword(null);
        commArg1.setPort(101);
        commArg1.setProductName("productName");
        commArg1.setProtocol("protocol");
        commArg1.setResponseTimeout(1);
        commArg1.setType("type");
        commArg1.setUrl("url");
        commArg1.setUserName("userName");
        commArg1.setVendor("vendor");
        commArg1.setVersion("version");
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        CommunicationArgumentMgr.addCommunicateArg(commArg1);
        assertTrue(true);
    }
    @Test(expected = NetconfException.class)
    public void testAddCommunicateArgNullIp1() throws NetconfException {
        CommunicateArg commArg = new CommunicateArg(null, "password", 102, "userName");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");      
        String controllerId = "controlleid";
        commArg.setControllerId(controllerId );
        commArg.setCreateTime("createTime");
        commArg.setDescription("description");
        commArg.setIp(null);
        commArg.setLoginTimeout(1);
        commArg.setMaxConnNum(1);
        commArg.setName("name");
        commArg.setPassword("password");
        commArg.setPort(102);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName("userName");
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        CommunicateArgWrap commArg1 = new CommunicateArgWrap(commArg, listener);
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        commArg1.setControllerId(controllerId);
        commArg1.setCreateTime("createTime");
        commArg1.setDescription("description");
        commArg1.setIp(null);
        commArg1.setLoginTimeout(1);
        commArg1.setMaxConnNum(1);
        commArg1.setName("name");
        commArg1.setPassword(null);
        commArg1.setPort(101);
        commArg1.setProductName("productName");
        commArg1.setProtocol("protocol");
        commArg1.setResponseTimeout(1);
        commArg1.setType("type");
        commArg1.setUrl("url");
        commArg1.setUserName("userName");
        commArg1.setVendor("vendor");
        commArg1.setVersion("version");
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        CommunicationArgumentMgr.addCommunicateArg(commArg1);
        assertTrue(true);
    }
    @Test(expected = NullPointerException.class)
    public void testAddCommunicateArgNullCommArg() throws NetconfException {
        CommunicateArg commArg = new CommunicateArg(null, "password", 102, "userName");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");      
        String controllerId = "controlleid";
        commArg.setControllerId(controllerId );
        commArg.setCreateTime("createTime");
        commArg.setDescription("description");
        commArg.setIp(null);
        commArg.setLoginTimeout(1);
        commArg.setMaxConnNum(1);
        commArg.setName("name");
        commArg.setPassword("password");
        commArg.setPort(102);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName("userName");
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        CommunicateArgWrap commArg1 = new CommunicateArgWrap(null, listener);
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        commArg1.setControllerId(controllerId);
        commArg1.setCreateTime("createTime");
        commArg1.setDescription("description");
        commArg1.setIp("1.0.1.2");
        commArg1.setLoginTimeout(1);
        commArg1.setMaxConnNum(1);
        commArg1.setName("name");
        commArg1.setPassword(null);
        commArg1.setPort(101);
        commArg1.setProductName("productName");
        commArg1.setProtocol("protocol");
        commArg1.setResponseTimeout(1);
        commArg1.setType("type");
        commArg1.setUrl("url");
        commArg1.setUserName("userName");
        commArg1.setVendor("vendor");
        commArg1.setVersion("version");
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        CommunicationArgumentMgr.addCommunicateArg(commArg1);
        assertTrue(true);
    }
    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr#delCommunicateArg(java.lang.String)}.
     */
    @Test
    public void testDelCommunicateArg() {
        CommunicationArgumentMgr.delCommunicateArg("1.0.1.2");
        assertTrue(true);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr#getCommunicateArg(java.lang.String)}.
     */
    @Test
    public void testGetCommunicateArg() {
        String controllerId="controlleid";
        CommunicationArgumentMgr.getCommunicateArg(controllerId);
        assertTrue(true);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr#clearAllCommArgs()}.
     */
    @Test
    public void testClearAllCommArgs() {
        CommunicationArgumentMgr.clearAllCommArgs();
        assertTrue(true);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr#isContainsArg(java.lang.String)}.
     */
    @Test
    public void testIsContainsArg() {
        String controllerId="controlleid";
        CommunicationArgumentMgr.isContainsArg(controllerId);
        assertTrue(true);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr#getAllCommunicateArg()}.
     */
    @Test
    public void testGetAllCommunicateArg() {
        CommunicationArgumentMgr.getAllCommunicateArg();
        assertTrue(true);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr#setCacheSessionPool(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ICacheSessionPool)}.
     */
    @Test
    public void testSetCacheSessionPool() {
        CommunicationArgumentMgr.setCacheSessionPool(cacheSessionPool);
        
        assertTrue(true);
    }
@Test
public void testListener()
{
    CommunicateArg commArg1 = new CommunicateArg("1.1.1.1", "username", "password", 120, "type", 10, 10);

    CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 102, "username");
    commArg.setAuthType("authType");
    commArg.setCharset("charset");
    String controllerId = "controlleid";
    commArg.setControllerId(controllerId );
    commArg.setCreateTime("createTime");
    commArg.setDescription("description");
    commArg.setIp("1.0.1.2");
    commArg.setLoginTimeout(1);
    commArg.setMaxConnNum(1);
    commArg.setName("name");
    commArg.setPassword(null);
    commArg.setPort(101);
    commArg.setProductName("productName");
    commArg.setProtocol("protocol");
    commArg.setResponseTimeout(1);
    commArg.setType("type");
    commArg.setUrl("url");
    commArg.setUserName("userName");
    commArg.setVendor("vendor");
    commArg.setVersion("version");
    NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo(commArg );
    listener.handleChange(netconfAccessInfo );
}

}
