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

package org.openo.commsvc.protocolstack.netconf.service.svc.service.impl;

import org.openo.commsvc.protocolstack.common.api.internalsvc.IConnParamsService;
import org.openo.commsvc.protocolstack.common.model.ConnInfoQueryMsg;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.impl.ConnParamsMgr;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr;

/**
 * @author
 */
public class ConnParamsServiceImpl implements IConnParamsService {

    /**
     * Connection parameter management objects
     */
    private ConnParamsMgr connParamsMgr;

    /**
     * Update connection parameters
     * 
     * @author
     * @see
     * @param connInfoMsg Connection information objects
     */
    @Override
    public void updateConnParams(ConnInfoQueryMsg connInfoMsg) {
        connParamsMgr.updateConnParams(connInfoMsg);
    }

    /**
     * Get the maximum number of connections
     * 
     * @author
     * @see
     * @param controllerId ControllerID
     * @return Maximum number of connections
     */
    @Override
    public int getMaxConnNum(String controllerId) {
        CommunicateArg arg = CommunicationArgumentMgr.getCommunicateArg(controllerId);
        if(null == arg) {
            return 0;
        }
        return arg.getMaxConnNum();
    }

    public ConnParamsMgr getConnParamsMgr() {
        return connParamsMgr;
    }

    public void setConnParamsMgr(ConnParamsMgr connParamsMgr) {
        this.connParamsMgr = connParamsMgr;
    }
}
