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

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

/**
 * Rpc interface <br>
 * 
 * @author
 */
public interface IRpc {

    /**
     * Being issued characteristic data, no return null
     * 
     * @author
     * @return Issued characteristic data
     */
    String getContent();

    /**
     * GetLrID
     * 
     * @author
     * @return lrID
     */
    Integer getLrID();

    /**
     * The timeout command.
     * 
     * @author
     * @return
     */
    long getTimeout();

    /**
     * Get Device character set
     * 
     * @author
     * @return Device character set
     */
    String getCharset();

    /**
     * Set the device character set
     * 
     * @author
     * @param charset
     */
    void setCharset(String charset);

    /**
     * GetvrID <br>
     * 
     * @author
     * @return vrID
     */
    Integer getVrID();

    /**
     * Set the timeout time of this command.
     * 
     * @author
     * @param timeout
     */
    void setTimeout(long timeout);

    /**
     * Converted into XML request string.
     * 
     * @author
     * @param messageID,
     *            for the corresponding request and response packets
     * @return article transmitters to String
     * @throws NetconfException
     *             Conversion failed
     */
    String toCommand(int messageID) throws NetconfException;
}
