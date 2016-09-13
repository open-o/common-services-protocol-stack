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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.common.model.ControllerStatus;

/**
 * @author
 */
public final class ConnStatusMgr {

    /**
     * The only instance.
     */
    private static final ConnStatusMgr INSTANCE = new ConnStatusMgr();

    private static final Map<String, String> currentControllers =
            Collections.synchronizedMap(new HashMap<String, String>());

    private static final Map<String, ControllerStatus> controllerConnectionStatus =
            Collections.synchronizedMap(new HashMap<String, ControllerStatus>());

    private static final Set<String> connectionNumbers = Collections.synchronizedSet(new HashSet<String>());

    /**
     * @return ConnStatusMgr instance of the ConnStatusMgr.
     */
    public static ConnStatusMgr getInstance() {
        return INSTANCE;
    }

    /**
     * Add Controller information<br>
     * 
     * @param controllerId - Controller ID
     * @param netconfServerType - Type of communication controller supports
     * @since GSO 0.5
     */
    public void addController(String controllerId, String netconfServerType) {
        currentControllers.put(controllerId, netconfServerType);
    }

    /**
     * @param controllerId
     */
    public void removeController(String controllerId) {

        currentControllers.remove(controllerId);
    }

    /**
     * clearAllConnStatus method
     */
    public void clearAllConnStatus() {
        controllerConnectionStatus.clear();
    }

    /**
     * @param controllerId
     * @param connectionStatus
     */
    public void updateConnStatus(String controllerId, ControllerStatus connectionStatus) {
        controllerConnectionStatus.put(controllerId, connectionStatus);
    }

    /**
     * @param controllerId
     * @throws ServiceException
     */
    public void addConnNum(String controllerId) throws ServiceException {
        if(controllerId == null) {
            throw new ServiceException("Controller Id is null");
        }
        connectionNumbers.add(controllerId);
    }

    /**
     * @param controllerId
     */
    public void subConnNum(String controllerId) {

        connectionNumbers.remove(controllerId);
    }
}
