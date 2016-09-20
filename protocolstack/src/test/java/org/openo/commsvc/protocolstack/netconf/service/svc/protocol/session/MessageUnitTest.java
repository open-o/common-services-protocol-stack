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

import static org.junit.Assert.*;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.RspCmd;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

public class MessageUnitTest {
    
    @Test
    public void testEquals(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        MessageUnit message2 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        boolean result = message1.equals(message2);
        assertTrue(result);
    }
    
    @Test
    public void testEquals2(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
       
        boolean result = message1.equals(message1);
        assertTrue(result);
    }
    
    @Test
    public void testEqualsFalse(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        MessageUnit message2 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,1234,null);
        boolean result = message1.equals(message2);
        assertFalse(result);
    }
    @Test
    public void testEqualsFalse2(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
       
        boolean result = message1.equals(null);
        assertFalse(result);
    }
    
    @Test
    public void testEqualsFalse3(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        boolean result = message1.equals(new Object());
        assertFalse(result);
    }
    @Test
    public void testIsActive(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        message1.setResp(new RspCmd());
        boolean result = message1.isActive();
        assertFalse(result);
    }
    
    @Test
    public void testIsActive2(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        message1.setResp(new RspCmd());
        boolean result = message1.isActive();
        assertFalse(result);
    }
    
    @Test
    public void testIsAsynchronized(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        boolean result = message1.isAsynchronized();
        assertFalse(result);
    }
    
    @Test
    public void testIsError(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        boolean result = message1.isError();
        assertFalse(result);
    }
    
    @Test
    public void testIsFinished(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        boolean result = message1.isFinished();
        assertFalse(result);
    }
    
    @Test
    public void testIsFinished2(){
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        message1.setResp(new RspCmd());
        boolean result = message1.isFinished();
        assertTrue(result);
    }
    
    @Test(expected = Exception.class)
    public void testWaitForReply() throws NetconfException {
        MessageUnit message1 = new MessageUnit(new DefaultSession(10,null,null,"test"),null,123,null);
        message1.setResp(new RspCmd());
        message1.waitForReply();
    }
    

}
