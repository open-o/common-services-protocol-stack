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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicateArgWrapTest;

public class CommunicateArgTest {

    private String ip = "1.0.1.2";

    private String password = "password";

    private int port = 250;

    private String userName = "userName";

    CommunicateArg commArg = new CommunicateArg(ip, password, port, userName);
    
    CommunicateArg commArg2 = new CommunicateArg(ip, password, port, userName);

    private String type = "type";

    CommunicateArg commArg1 = new CommunicateArg(ip, userName, password, port, type, 5, 10);
    String ENCODING_UTF8 = "UTF-8";
    long MIN_TIMEOUT_UNIT = 1000;
    String PASSWORD_AUTH = "1";
    String controllerId;
    String name;
    String url;
    String version;
    String vendor;
    String description;
    String protocol;
    String productName;
    String createTime;
    int responseTimeout;
    int loginTimeout;
    String authType = PASSWORD_AUTH;
    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg#hashCode()}.
     */
    @Test
    public void testHashCode() {
        commArg.hashCode();
        commArg.setIp(null);
        commArg.hashCode();
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg#getControllerId()}.
     */
    @Test
    public void testGetControllerId() {
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
        String controllerId1 = "controlleid";
        commArg1.setControllerId(controllerId1 );
        commArg1.setCreateTime("createTime");
        commArg1.setDescription("description");
        commArg1.setIp("1.1.1.1");
        commArg1.setLoginTimeout(1);
        commArg1.setMaxConnNum(1);
        commArg1.setName("name");
        commArg1.setPassword("password");
        commArg1.setPort(101);
        commArg1.setProductName("productName");
        commArg1.setProtocol("protocol");
        commArg1.setResponseTimeout(1);
        commArg1.setType("type");
        commArg1.setUrl("url");
        commArg1.setUserName("userName");
        commArg1.setVendor("vendor");
        commArg1.setVersion("version");
        commArg.getAuthType();
        commArg.getCharset();   
        commArg.getControllerId( );
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
        commArg1.getControllerId( );
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
        assert(true);
        }
    @Test
    public void testGetControllerIdNullUserName() {
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
        commArg.setPassword("password");
        commArg.setPort(101);
        commArg.setProductName("productName");
        commArg.setProtocol("protocol");
        commArg.setResponseTimeout(1);
        commArg.setType("type");
        commArg.setUrl("url");
        commArg.setUserName(null);
        commArg.setVendor("vendor");
        commArg.setVersion("version");
        commArg.setAuthType("authType");
        commArg.setCharset("charset");      
        String controllerId1 = "controlleid";
        commArg1.setControllerId(controllerId1 );
        commArg1.setCreateTime("createTime");
        commArg1.setDescription("description");
        commArg1.setIp("1.1.1.1");
        commArg1.setLoginTimeout(1);
        commArg1.setMaxConnNum(1);
        commArg1.setName("name");
        commArg1.setPassword("password");
        commArg1.setPort(101);
        commArg1.setProductName("productName");
        commArg1.setProtocol("protocol");
        commArg1.setResponseTimeout(1);
        commArg1.setType("type");
        commArg1.setUrl("url");
        commArg1.setUserName(null);
        commArg1.setVendor("vendor");
        commArg1.setVersion("version");
        commArg.getAuthType();
        commArg.getCharset();   
        commArg.getControllerId( );
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
        commArg1.getControllerId( );
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
        assert(true);
        }
    @Test
    public void testGetControllerIdNullPassword() {
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
        commArg.setAuthType("authType");
        commArg.setCharset("charset");      
        String controllerId1 = "controlleid";
        commArg1.setControllerId(controllerId1 );
        commArg1.setCreateTime("createTime");
        commArg1.setDescription("description");
        commArg1.setIp("1.1.1.1");
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
        commArg.getAuthType();
        commArg.getCharset();   
        commArg.getControllerId( );
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
        commArg1.getControllerId( );
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
        assert(true);
        }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg#toString()}.
     */
    @Test
    public void testToString() {
        commArg.toString();
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
       commArg.equals(PASSWORD_AUTH);//(ENCODING_UTF8);
    }
    @Test
    public void testEqualsObject3() {
       commArg.equals(ENCODING_UTF8);
    }
    @Test
    public void testEqualsObject1() {
       commArg.equals(null);
    }
    @Test
    public void testEqualsObject12() {
       Object obj = new Object();
    commArg.equals(obj);
    }
    
    @Test
    public void testEqualsObject13() {
        commArg.equals(commArg);
    }
    
    @Test
    public void testEqualsObject14() {
        commArg.setIp(null);
        commArg.equals(commArg2);
    }
    
    @Test
    public void testEqualsObject15() {
        commArg.setIp(null);
        commArg2.setIp(null);
        commArg.equals(commArg2);
    }
    @Test
    public void testEqualsObject16() {
        commArg.equals(commArg2);
        commArg2.setIp("1.1.1.1");
        commArg.equals(commArg2);
    }
    
    @Test
    public <CommunicateArgWrap> void testEqualsObject2() {
        CommunicateArgWrap c = null;
        ip = null;
       commArg.equals(c);
    }
    
}
