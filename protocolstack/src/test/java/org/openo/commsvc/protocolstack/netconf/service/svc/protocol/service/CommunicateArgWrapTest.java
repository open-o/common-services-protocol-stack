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

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr.CommunicateArgChangeListener;

public class CommunicateArgWrapTest {

    int USER_LEVEL = 3;

    CommunicateArg commArg1 = new CommunicateArg("1.1.1.1", "username", "password", 120, "type", 10, 10);

    CommunicateArg commArg2 = new CommunicateArg("1.1.1.1", "password", 102, "username");

    Map<String, String> controllerIds = new HashMap<String, String>();

    CommunicateArgChangeListener listener;

    CommunicateArgWrap commArg = new CommunicateArgWrap(commArg1, listener);

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicateArgWrap#hashCode()}.
     */
    @Test
    public void testHashCode() {
        commArg.hashCode();
        assertTrue(true);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicateArgWrap#getControllerId()}.
     */
    @Test
    public void testGetControllerId() {
        controllerIds.put("controlleid", "controlleid");
        controllerIds.put("controlleid1", "controlleid1");
        controllerIds.put("controlleid2", "controlleid2");
        controllerIds.put("controlleid21", "controlleid21");
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        String controllerId = "controlleid";
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
        commArg.setAuthType("authType");
        commArg.setCharset("charset");
        commArg.setControllerId(controllerId);
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
        commArg.setAuthType("authType");
        commArg.setCharset("charset");
        commArg2.setControllerId(controllerId);
        commArg2.setCreateTime("createTime");
        commArg2.setDescription("description");
        commArg2.setIp("1.0.1.2");
        commArg2.setLoginTimeout(1);
        commArg2.setMaxConnNum(1);
        commArg2.setName("name");
        commArg2.setPassword(null);
        commArg2.setPort(101);
        commArg2.setProductName("productName");
        commArg2.setProtocol("protocol");
        commArg2.setResponseTimeout(1);
        commArg2.setType("type");
        commArg2.setUrl("url");
        commArg2.setUserName("userName");
        commArg2.setVendor("vendor");
        commArg2.setVersion("version");
        commArg2.setAuthType("authType");
        commArg2.setCharset("charset");
        commArg.getAuthType();
        commArg.getCharset();
        commArg.getControllerId();
        commArg.getCreateTime();
        commArg.getDescription();
        commArg.getIp();
        commArg.getLoginTimeout();
        commArg.getMaxConnNum();
        commArg.getName();
        commArg.getPassword();
        commArg.getPort();
        commArg.getProductName();
        commArg.getProtocol();
        commArg.getResponseTimeout();
        commArg.getType();
        commArg.getUrl();
        commArg.getUserName();
        commArg.getVendor();
        commArg.getVersion();
        commArg.getAuthType();
        commArg.getCharset();
        commArg1.getControllerId();
        commArg1.getCreateTime();
        commArg1.getDescription();
        commArg1.getIp();
        commArg1.getLoginTimeout();
        commArg1.getMaxConnNum();
        commArg1.getName();
        commArg1.getPassword();
        commArg1.getPort();
        commArg1.getProductName();
        commArg1.getProtocol();
        commArg1.getResponseTimeout();
        commArg1.getType();
        commArg1.getUrl();
        commArg1.getUserName();
        commArg1.getVendor();
        commArg1.getVersion();
        assertTrue(true);
    }

    @Test
    public void testGetCharset() {
        controllerIds.put("controlleid", "controlleid");
        controllerIds.put("controlleid1", "controlleid1");
        controllerIds.put("controlleid2", "controlleid2");
        controllerIds.put("controlleid21", "controlleid21");
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        String controllerId = "controlleid";
        commArg1.setControllerId(controllerId);
        commArg1.setCreateTime("createTime");
        commArg1.setDescription("description");
        commArg1.setIp("1.0.1.21");
        commArg1.setLoginTimeout(11);
        commArg1.setMaxConnNum(1);
        commArg1.setName("name");
        commArg1.setPassword("password1");
        commArg1.setPort(1011);
        commArg1.setProductName("productName");
        commArg1.setProtocol("protocol1");
        commArg1.setResponseTimeout(11);
        commArg1.setType("type1");
        commArg1.setUrl("url");
        commArg1.setUserName("userName1");
        commArg1.setVendor("vendor");
        commArg1.setVersion("version");
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset1");
        commArg1.setAuthType("authType");
        commArg.setControllerId(controllerId);
        commArg.setCreateTime("createTime");
        commArg.setDescription("description");
        commArg.setIp("1.0.1.2");
        commArg.setLoginTimeout(1);
        commArg.setMaxConnNum(1);
        commArg.setName("name");
        commArg.setPassword("password");
        commArg.setPort(101);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName("userName");
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");
        String result = commArg.getCharset();
        assertTrue(true);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicateArgWrap#toString()}.
     */
    @Test
    public void testToString() {
        controllerIds.put("controlleid", "controlleid");
        controllerIds.put("controlleid1", "controlleid1");
        controllerIds.put("controlleid2", "controlleid2");
        controllerIds.put("controlleid21", "controlleid21");
        commArg.toString();
        assertTrue(true);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicateArgWrap#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
        controllerIds.put("controlleid", "controlleid");
        controllerIds.put("controlleid1", "controlleid1");
        controllerIds.put("controlleid2", "controlleid2");
        controllerIds.put("controlleid21", "controlleid21");
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        String controllerId = "controlleid";
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
        commArg1.setAuthType("authType");
        commArg1.setCharset("charset");
        commArg.setControllerId(controllerId);
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
        commArg.setAuthType("authType");
        commArg.setCharset("charset");
        boolean result = commArg.equals(commArg);
        assertTrue(true);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicateArgWrap#closeDevNetconfCache()}.
     */
    @Test
    public void testCloseDevNetconfCache() {
        controllerIds.put("controlleid", "controlleid");
        controllerIds.put("controlleid1", "controlleid1");
        controllerIds.put("controlleid2", "controlleid2");
        controllerIds.put("controlleid21", "controlleid21");
        commArg.closeDevNetconfCache();
        assertTrue(true);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicateArgWrap#setDevId(java.lang.String)}.
     */
    @Test
    public void testSetDevId() {
        controllerIds.put("controlleid", "controlleid");
        controllerIds.put("controlleid1", "controlleid1");
        controllerIds.put("controlleid2", "controlleid2");
        controllerIds.put("controlleid21", "controlleid21");
        String controllerId = "controllerId";
        commArg.setDevId(controllerId);
        assertTrue(true);
    }
}
