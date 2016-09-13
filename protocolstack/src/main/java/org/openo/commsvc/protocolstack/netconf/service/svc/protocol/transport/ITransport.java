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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;

/**
 * Interface for the trasport (e.g. ssh).
 * <br>
 * 
 * @author
 * @version     GSO 0.5  Sep 7, 2016
 */
public interface ITransport {

    /**
     * Add a connection listener.
     * 
     * @author
     * @param connectListener Listener connections
     */
    void addConnectListener(ITransportListener connectListener);

    /**
     * Close the connection.
     * 
     * @author
     * @return true:Close success , false: Close unsuccessful , previously closed
     */
    boolean close();

    /**
     * Connect <br>
     * 
     * @author
     * @param netconfAccessInfo Netconf access parameters
     * @param transportHandler Transport Layer Interface Information Processing
     */
    void connect(NetconfAccessInfo netconfAccessInfo, ITransportHandler transportHandler) throws NetconfException;

    /**
     * The current connection.
     * 
     * @author
     * @return true:Connection , false: not connected
     */
    boolean isConnected();

    /**
     * Blocking transmission interface to ensure that the conditions to achieve successful returns
     * all bytes have been sent。 <br>
     * 
     * @author
     * @param request Rpc request packets
     * @exception NetconfException NETCONFabnormal
     */
    void send(String request) throws NetconfException;

}
