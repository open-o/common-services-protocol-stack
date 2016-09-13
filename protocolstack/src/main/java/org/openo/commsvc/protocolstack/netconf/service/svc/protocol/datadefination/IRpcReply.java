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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination;

import java.util.Collection;

/**
 * <rpc-reply>Element is used to encapsulate RPC requests a reply
 * message，NETCONF server to each<rpc>A response operation using <rpc-reply>
 * reply message element package.
 * 
 * @author
 * @see
 */
public interface IRpcReply {

    /**
     * Determine whether the request was successful
     * 
     * @author
     * @see
     * @return
     */
    boolean isOK();

    /**
     * Determine whether the request is active packets.
     * 
     * @author
     * @see
     * @return
     */
    boolean isActive();

    /**
     * Get the message ID.
     * 
     * @author
     * @see
     * @return
     */
    int getMessageID();

    /**
     * Get response to the content of this request.
     * 
     * @author
     * @see
     * @return
     */
    String getResponse();

    /**
     * set response to the content of this request.
     * 
     * @author
     * @see
     * @param response
     * @return
     */
    void setResponse(String response);

    /**
     * Get error message responses.
     * 
     * @author
     * @see
     * @return
     */
    Collection<RpcErrorInfo> getErrorInfos();
}
