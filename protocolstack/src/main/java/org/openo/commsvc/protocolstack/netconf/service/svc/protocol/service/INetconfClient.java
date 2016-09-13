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

import java.util.List;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IOperation;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpcReply;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.RpcArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

/**
 * @author
 *         INetconfClient
 *         Defining as interface
 */
public interface INetconfClient {

    /**
     * Close the current session resource occupied by the client. <br>
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.INetconfClient#close()
     * @author
     */
    void close();

    /**
     * GSO extension: ICT only for packaging RPC layer encapsulation, operation
     * layer and the content layer is completed by the client, send a direct
     * call interface to send NETCONF request
     * 
     * @author
     * @see
     * @param enableNext
     *            Whether by the protocol stack packet splicing
     * @param rpcArgList
     *            RPC parameter list
     * @param operArg
     *            Operation packet layer parameters
     * @return Operating Results
     * @throws NetconfException
     *             NETCONF exception
     */
    IRpcReply send(boolean enableNext, List<RpcArg> rpcArgList, IOperation operArg) throws NetconfException;
}
