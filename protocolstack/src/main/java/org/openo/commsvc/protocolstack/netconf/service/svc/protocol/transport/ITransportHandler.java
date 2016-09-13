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

/**
 * Transport layer information processing interface
 * 
 * @author
 */
public interface ITransportHandler {

    /**
     * Processing device returned messages
     * 
     * @author
     * @param devReplyStr Message information returned by the device
     */
    void handlePacket(String devReplyStr);

    /**
     * Error information processing apparatus returns <br>
     * 
     * @author
     * @param devReplyErr Error messages returned by the device
     */
    void handleError(String devReplyErr);

    /**
     * Device interaction with the end of the glyph packets <br>
     * 
     * @author
     * @return End glyph packets
     */
    String getPacketEndTag();

    /**
     * Encoding packets
     * 
     * @author
     * @return Encoding packets
     */
    String getCharsetName();

}
