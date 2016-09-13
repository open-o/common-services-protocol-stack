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

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpcReply;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

/**
 * And equipment NETCONF session.
 * 
 * @author
 */
public interface ISession {

    /**
     * Increase Session listening classes, such as for monitoring Session
     * disconnection
     * 
     * @author
     * @param sessionListener
     *            Session listener class
     */
    void addSessionListener(ISessionListener sessionListener);

    /**
     * shut down
     * 
     * @author
     * @return true:Close success, false: it has shut down before
     */
    boolean close();

    /**
     * establish connection
     * 
     * @author
     * @throws NetconfException
     */
    void connect() throws NetconfException;

    /**
     * Get the current Session of the access parameters Netconf
     * 
     * @author
     * @return Netconf access parameters
     */
    NetconfAccessInfo getNetconfAccessInfo();

    /**
     * Whether the session together.
     * 
     * @author
     * @return
     */
    boolean isConnected();

    /**
     * Synchronous Transmission Interface
     * <p>
     * Thread synchronization request using the user sends a command to
     * complete, and then start polling thread asynchronous reads while the user
     * thread into a wait state until the message is returned.
     * 
     * @param reqCmd
     *            Request command
     * @param rspCmdList
     *            Response command
     * @param millisecondTimeout
     *            Timeout ms
     * @return error code
     */
    IRpcReply synSend(IRpc reqCmd) throws NetconfException;

}
