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

package org.openo.commsvc.protocolstack.netconf.service.svc.model;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.OperationArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

/**
 * NetconfOperationArg manages the argument of the Netconf Operation message.
 * <br>
 * 
 * @author
 * @version GSO 0.5 Sep 7, 2016
 */
public class NetconfOperationArg extends OperationArg {

    /**
     * NETCONF commands: here is the result package operation layer and content
     * layer,By the caller passed directly over, after this plug-in
     * package RPC layer next to controllers, such as:
     * <get>
     * <filter type="subtree"> <top xmlns="http://example.com/schema/1.2/stats">
     * <interfaces> <interface> <ifName>eth0</ifName> </interface> </interfaces>
     * </top> </filter>
     * </get>
     */
    private String operationCmd;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @param operationCmd - Operation command
     * @since SDNO 0.5
     */
    public NetconfOperationArg(String operationCmd) {
        super();
        this.operationCmd = operationCmd;
    }

    /**
     * Back NETCONF operational level command
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IOperation#toCommand()
     * @author
     * @see
     * @return NETCONF contents of the command
     * @throws NetconfException
     *             NETCONFException
     */
    @Override
    public String toCommand() throws NetconfException {
        return operationCmd;
    }
}
