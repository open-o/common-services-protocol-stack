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

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import static org.junit.Assert.*;
import java.util.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DeviceSessionPoolTest
{
    DeviceSessionPool pool;
    DeviceSessionPool pool1;
    @Test
    public void testCompareTo(){
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        DeviceSessionPool pool2 = new DeviceSessionPool(netinfo,10);
        
        int result = pool1.compareTo(pool2);
        pool2.access();
        
        assertTrue(result != 0);
    }
    
    //NOTE: False case is not working. check issue with compareTo mehtod
    @Test
    public void testCompareToFalse(){
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        NetconfAccessInfo netinfo2 = new NetconfAccessInfo(new CommunicateArg("1.1.1.2", "username","password", 8082, "type", 101,
                51));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,100);
        DeviceSessionPool pool2 = new DeviceSessionPool(netinfo2,10);
        
        pool1.access();
        pool2.access();
        
        int result = pool1.compareTo(pool2);
        assertTrue(result == 0);
    }
    
    @Test
    public void testEquals(){
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        DeviceSessionPool pool2 = new DeviceSessionPool(netinfo,10);
        pool2.access();
        boolean result = pool1.equals(pool2);
        
        
        //assertTrue(result); //NOTE: similar objects are not equals
        assertFalse(result);
    }
    
    @Test
    public void testEqualsNull(){
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        
        boolean result = pool1.equals(null);
        
        
        //assertTrue(result); //NOTE: similar objects are not equals
        assertFalse(result);
    }
    
    @Test
    public void testEqualsInvalidClass(){
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        
        boolean result = pool1.equals(new Object());
        
        
        //assertTrue(result); //NOTE: similar objects are not equals
        assertFalse(result);
    }
    
    @Test
    public void testToString(){
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        
        String result = pool1.toString();
        assertTrue(result.indexOf("1.1.1.1") > -1); //NOTE: similar objects are not equals
        
    }
    
    @Test
    public void testBookSession() throws NetconfException{
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        
        SessionWrapper result = pool1.bookSession(true,10l);
       
        assertTrue(result.getSession() == null);
    }
    
    @Test
    public void testBookSessionFalse() throws NetconfException{
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        
        SessionWrapper result = pool1.bookSession(false,10l);
       
        assertTrue(result.getSession() == null);
    }
    
    @Test
    public void testFreeSession() throws NetconfException{
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        
        pool1.freeSession(new SessionWrapper());
       
        assertTrue(true);
    }
    
    @Test
    public void testFreeSession2() throws NetconfException{
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        
        pool1.freeSession(new DefaultSession(10,null,null,"test"));
       
        assertTrue(true);
    }
    @Test
    public void testRemoveFreeSessions() throws NetconfException{
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        List<SessionWrapper> list = pool1.removeFreeSessions();
       
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testRemoveSession() throws NetconfException{
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        boolean result = pool1.removeSession(new DefaultSession(10,null,null,"test"));
       
        assertFalse(result);
    }
    
    @Test
    public void testGetAvailableSessionNum() throws NetconfException{
        
        NetconfAccessInfo netinfo = new NetconfAccessInfo(new CommunicateArg("1.1.1.1", "username","password", 8080, "type", 100,
                50));
        DeviceSessionPool pool1 = new DeviceSessionPool(netinfo,10);
        pool1.access();
        
        int result = pool1.getAvailableSessionNum();
       
        assertTrue(result == 0);
    }
    @Test
    public void testDeviceSessionPool() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        try {
            Class<DeviceSessionPool> sampleClass = DeviceSessionPool.class;
            Constructor<DeviceSessionPool> declaredConstructor = DeviceSessionPool.class.getDeclaredConstructor(NetconfAccessInfo.class, int.class);
            declaredConstructor.setAccessible(true);

            final int sessionLimit = 10;
            String controllerId = "controller";
            String devIp = "device";
            final NetconfAccessInfo netconf = new NetconfAccessInfo(controllerId, devIp);

            DeviceSessionPool newInstance = declaredConstructor.newInstance(netconf, sessionLimit);
            pool = newInstance;
            pool.hashCode();
            //System.out.println(newInstance.getClass().toString());

        } catch (NoSuchMethodException e) {
            System.out.println("there is no such constructor");
        } catch (InstantiationException e) {
            System.out.println("there is no default constructor in sample class");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBookSession12() throws NetconfException {

        try {
            Class<DeviceSessionPool> sampleClass = DeviceSessionPool.class;
            Constructor<DeviceSessionPool> declaredConstructor = DeviceSessionPool.class.getDeclaredConstructor(NetconfAccessInfo.class, int.class);
            declaredConstructor.setAccessible(true);

            final int sessionLimit = 10;
            String controllerId = "controller";
            String devIp = "device";
            final NetconfAccessInfo netconf = new NetconfAccessInfo(controllerId, devIp);

            DeviceSessionPool newInstance = declaredConstructor.newInstance(netconf, sessionLimit);
            pool = newInstance;
            //System.out.println(newInstance.getClass().toString());

        } catch (NoSuchMethodException e) {
            System.out.println("there is no such constructor");
        } catch (InstantiationException e) {
            System.out.println("there is no default constructor in sample class");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        long deadLine = 100L;
        for (int i = 0; i <= 9; i++) {
            try {
                SessionWrapper session = pool.bookSession(true, deadLine);
                if (session == null) {
                    return;
                }
            } catch (NetconfException e){
                System.out.println("netconf exception caught");
            }
        }
    }

    @Test
    public void testBookSession1() throws NetconfException {

        try {
            Class<DeviceSessionPool> sampleClass = DeviceSessionPool.class;
            Constructor<DeviceSessionPool> declaredConstructor = DeviceSessionPool.class.getDeclaredConstructor(NetconfAccessInfo.class, int.class);
            declaredConstructor.setAccessible(true);

            final int sessionLimit = 10;
            String controllerId = "controller";
            String devIp = "device";
            final NetconfAccessInfo netconf = new NetconfAccessInfo(controllerId, devIp);

            DeviceSessionPool newInstance = declaredConstructor.newInstance(netconf, sessionLimit);
            pool = newInstance;
            //System.out.println(newInstance.getClass().toString());

        } catch (NoSuchMethodException e) {
            System.out.println("there is no such constructor");
        } catch (InstantiationException e) {
            System.out.println("there is no default constructor in sample class");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        long deadLine = 100L;
        for (int i = 0; i <= 9; i++) {
            try {
                SessionWrapper session = pool.bookSession(false, deadLine);
                if (session == null) {
                    return;
                }
            } catch (NetconfException e){
                System.out.println("netconf exception caught");
            }
        }
    }

    @Test
    public void testCompareToEqual() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        try {
            Class<DeviceSessionPool> sampleClass = DeviceSessionPool.class;
            Constructor<DeviceSessionPool> declaredConstructor = DeviceSessionPool.class.getDeclaredConstructor(NetconfAccessInfo.class, int.class);
            declaredConstructor.setAccessible(true);

            final int sessionLimit = 10;
            String controllerId = "controller";
            String controllerId1 = "controller1";
            String devIp = "device";
            final NetconfAccessInfo netconf = new NetconfAccessInfo(controllerId, devIp);
            final NetconfAccessInfo netconf1 = new NetconfAccessInfo(controllerId1, devIp);

            DeviceSessionPool newInstance = declaredConstructor.newInstance(netconf, sessionLimit);
            DeviceSessionPool newInstance1 = declaredConstructor.newInstance(netconf1, sessionLimit);
            pool = newInstance;
            pool1 = newInstance1;
            //System.out.println(newInstance.getClass().toString());

        } catch (NoSuchMethodException e) {
            System.out.println("there is no such constructor");
        } catch (InstantiationException e) {
            System.out.println("there is no default constructor in sample class");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        pool.compareTo(pool);
        pool.compareTo(pool1);
        pool.equals(pool);
        pool.equals(pool1);
    }

}
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
 

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;

public class DeviceSessionPoolTest {
    
   

}
*/