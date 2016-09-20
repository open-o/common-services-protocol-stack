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

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;

public class NetconfAccessInfoTest {

    @Test(expected = IllegalArgumentException.class)
    public void NetconfAccessInfoTestNull()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void NetconfAccessInfoTestIpNull()
    {
        CommunicateArg commArg = new CommunicateArg(null, "password", 102, "userName");
        NetconfAccessInfo obj = new NetconfAccessInfo(commArg);
    }
    
    @Test
    public void NetconfAccessInfoTest()
    {
        CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 102, "userName");
        NetconfAccessInfo obj = new NetconfAccessInfo(commArg);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void NetconfAccessInfoTestAbnormal1()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo(null,null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void NetconfAccessInfoTestAbnormal2()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo("10",null);
    }
    
    @Test
    public void NetconfAccessInfoTestNormal()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo("10","1.1.1.1");
    }
    
    @Test
    public void equalsTest()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo("10","1.1.1.1");
        assertTrue(obj.equals(obj));
    }
    
    @Test
    public void equalsTestNull()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo("10","1.1.1.1");
        assertFalse(obj.equals(null));
    }
    
    @Test
    public void equalsTestGetClass()
    {
        CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 102, "userName");
        NetconfAccessInfo obj = new NetconfAccessInfo("10","1.1.1.1");
        assertFalse(obj.equals(commArg));
    }
    
    @Test
    public void hashCodeTestCtrNull()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo("10","1.1.1.1");
        obj.setControllerId(null);
        int result;
        result = obj.hashCode();
        boolean val;
        if(result == 1901620795)
        {
            val = true;
        }
        else
        {
            val = false;
        }
        
        assertTrue(val);
    }
    
    @Test
    public void hashCodeTestIpNull()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo("10","1.1.1.1");
        int result;
        result = obj.hashCode();
        boolean val;
        if(result == 1901669372)
        {
            val = true;
        }
        else
        {
            val = false;
        }
        
        assertTrue(val);
    }
    
    @Test
    public void testToStringIpEmpty()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo("10","");
        CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 102, "userName");
        obj.setCommArg(commArg);
        assertTrue(obj.toString() != null);
        
    }
    
    @Test
    public void testToStringIp()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo("10","1.1.1.1");
        CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 102, "userName");
        obj.setCommArg(commArg);
        assertTrue(obj.toString() != null);
        
    }
    
    @Test
    public void testToStringCommunicateArgNull()
    {
        NetconfAccessInfo obj = new NetconfAccessInfo("10","");
        CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 102, "userName");
        obj.setCommArg(null);
        assertTrue(obj.toString() != null);
        
    }
}
