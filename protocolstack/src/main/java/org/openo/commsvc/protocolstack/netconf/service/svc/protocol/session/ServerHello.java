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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session;

import java.util.Collection;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpcReply;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.NetconfAbility;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.RpcErrorInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.BadMessageFormatException;

/**
 * Hello Packets.
 * 
 * @author
 * @see
 */
public class ServerHello implements IRpcReply {

    public static final String HELLO_HEAD = "hello";

    private NetconfAbility ability;

    private Integer sessionID;

    /**
     * Handshake for netconf server<br/>
     * <p>
     * </p>
     * 
     * @param reader - XMl stream reader
     * @throws BadMessageFormatException -when reading output encounters any error
     * @since SDNO 0.5
     */
    public ServerHello(XMLStreamReader reader) throws BadMessageFormatException {
        if(HELLO_HEAD.equals(reader.getLocalName())) {
            try {
                this.ability = new NetconfAbility(reader);
                while(reader.hasNext()) {
                    int event = reader.next();
                    if(event == XMLStreamConstants.START_ELEMENT) {
                        if("session-id".equals(reader.getLocalName())) {
                            this.sessionID = Integer.parseInt(reader.getElementText().trim());
                        }
                    } else if((event == XMLStreamConstants.END_ELEMENT) && HELLO_HEAD.equals(reader.getLocalName())) {
                        return;
                    }
                }
            } catch(NumberFormatException e) {
                throw new BadMessageFormatException("session-id is not illegal.", e);
            } catch(XMLStreamException e) {
                throw new BadMessageFormatException("hello is not illegal.", e);
            }
            return;
        }
        throw new BadMessageFormatException("expected <hello> but actual is " + reader.getLocalName(), null);
    }

    public NetconfAbility getAbility() {
        return this.ability;
    }

    public Integer getSessionID() {
        return this.sessionID;
    }

    @Override
    public boolean isOK() {
        return true;
    }

    @Override
    public Collection<RpcErrorInfo> getErrorInfos() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public int getMessageID() {
        return ClientHello.HELLO_MESSAGE_ID;
    }

    @Override
    public String getResponse() {
        return null;
    }

    @Override
    public void setResponse(String response) {
        // No need to handle this
    }
}
