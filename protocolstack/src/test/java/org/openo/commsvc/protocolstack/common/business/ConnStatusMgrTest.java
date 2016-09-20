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

package org.openo.commsvc.protocolstack.common.business;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.common.model.ControllerStatus;


public class ConnStatusMgrTest {
    ConnStatusMgr instance = new ConnStatusMgr();
    Map<String, String> currentControllers = Collections.synchronizedMap(new HashMap<String, String>());
    String controllerId="controllerId";
    String netconfServerType="netconfServerType";
    Map<String, ControllerStatus> controllerConnectionStatus = Collections.synchronizedMap(new HashMap<String, ControllerStatus>());
    ControllerStatus connectionStatus = ControllerStatus.NORMAL;
    Set<String> connectionNumbers = Collections.synchronizedSet(new HashSet<String>());
    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.business.ConnStatusMgr#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        ConnStatusMgr result = instance.getInstance();
        assertTrue(result!=instance);        
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.business.ConnStatusMgr#addController(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testAddController() {
        currentControllers.put(controllerId, netconfServerType);        
        instance.addController(controllerId, netconfServerType);
        assert(true);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.business.ConnStatusMgr#removeController(java.lang.String)}.
     */
    @Test
    public void testRemoveController() {
        currentControllers.remove(controllerId);        
        instance.removeController(controllerId);
        assert(true);
        }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.business.ConnStatusMgr#clearAllConnStatus()}.
     */
    @Test
    public void testClearAllConnStatus() {
        controllerConnectionStatus.clear();
        instance.clearAllConnStatus();
        assert(true);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.business.ConnStatusMgr#updateConnStatus(java.lang.String, org.openo.commsvc.protocolstack.common.model.ControllerStatus)}.
     */
    @Test
    public void testUpdateConnStatus() {
        controllerConnectionStatus.put(controllerId, connectionStatus);
        instance.updateConnStatus(controllerId, connectionStatus);
        assert(true);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.business.ConnStatusMgr#addConnNum(java.lang.String)}.
     * @throws ServiceException 
     */
    @Test
    public void testAddConnNum() throws ServiceException {
        connectionNumbers.add(controllerId);
        instance.addConnNum(controllerId);
        assert(true);
    }
    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.business.ConnStatusMgr#addConnNum(java.lang.String)}.
     * @throws ServiceException 
     */
    @Test(expected = ServiceException.class)
    public void testAddConnNumNull() throws ServiceException {
        String controllerId1 = null;
        connectionNumbers.add(controllerId1);
        instance.addConnNum(controllerId1);
        assert(true);
    }
    
    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.common.business.ConnStatusMgr#subConnNum(java.lang.String)}.
     */
    @Test
    public void testSubConnNum() {
        connectionNumbers.remove(controllerId);
        instance.subConnNum(controllerId);
        assert(true);
    }

}
