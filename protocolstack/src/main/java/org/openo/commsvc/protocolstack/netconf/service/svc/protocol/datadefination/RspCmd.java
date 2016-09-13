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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.BadMessageFormatException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.tool.XmlUtil;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.util.VTDNavAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Return command
 * 
 * @author
 * @see
 */
public class RspCmd implements IRpcReply {

    private static final Logger LOGGER = LoggerFactory.getLogger(IRpcReply.class);
    
    /**
     * Response packet
     */
    private String response;

    /**
     * Error message in response to a command error may have several
     */
    private List<RpcErrorInfo> errorInfos;

    /**
     * Returns the message sequence number
     */
    private int messageID = -1;

    private boolean active;

    /**
     * Constructor
     */
    public RspCmd() {
        LOGGER.info("Empty method body.");
    }

    /**
     * 
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @param rpcReply
     * @throws BadMessageFormatException
     * @since  GSO 0.5
     */
    public RspCmd(String rpcReply) throws BadMessageFormatException {
        if(rpcReply == null) {
            return;
        }

        XMLInputFactory factory = XmlUtil.newXMLInputFactory();
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(rpcReply.trim()));
            while(reader.hasNext()) {
                int event = reader.next();
                if(event == XMLStreamConstants.START_ELEMENT
                        && (RpcMessageConstant.ELE_RPCREPLY.equals(reader.getLocalName())
                                || RpcMessageConstant.ELE_ACTIVE.equals(reader.getLocalName()))) {
                    init(reader);
                    break;
                }
            }
        } catch(XMLStreamException e) {
            throw new BadMessageFormatException("rpc-reply is not illegal.", e);
        }
    }

    /**
     * Passed in the reader position
     * 
     * @author
     * @see
     * @param reader
     * @throws BadMessageFormatException
     */
    public RspCmd(XMLStreamReader reader) throws BadMessageFormatException {
        try {
            init(reader);
        } catch(XMLStreamException e) {
            throw new BadMessageFormatException("rpc-reply is not illegal.", e);
        }
    }

    private void init(XMLStreamReader reader) throws BadMessageFormatException, XMLStreamException {
        if(RpcMessageConstant.ELE_RPCREPLY.equals(reader.getLocalName())) {
            // message-id, flow-id, set-id
            this.messageID = VTDNavAdapter.getIntAttribute(reader, RpcMessageConstant.ATTR_MESSAGE_ID, -1);

            while(reader.hasNext()) {
                int event = reader.next();
                // If this is the beginning of the element
                if(event == XMLStreamConstants.START_ELEMENT) {
                    parseElement(reader);
                } else if((event == XMLStreamConstants.END_ELEMENT)
                        && RpcMessageConstant.ELE_RPCREPLY.equals(reader.getLocalName())) {
                    // </rpc-reply>On behalf of the end.
                    return;
                }
            }
        } else if(RpcMessageConstant.ELE_ACTIVE.equals(reader.getLocalName())) {
            this.messageID = VTDNavAdapter.getIntAttribute(reader, RpcMessageConstant.ATTR_MESSAGE_ID, -1);
            this.active = true;
        } else {
            throw new BadMessageFormatException("expected <rpc-reply> but actual is " + reader.getLocalName(), null);
        }

    }

    private void parseElement(XMLStreamReader reader) throws XMLStreamException, BadMessageFormatException {
        String name = reader.getLocalName();
        if(RpcMessageConstant.ELE_RPC_ERROR.equals(name)) {
            RpcErrorInfo error = new RpcErrorInfo(reader);
            if(this.errorInfos == null) {
                this.errorInfos = new ArrayList<RpcErrorInfo>();
            }
            this.errorInfos.add(error);
        }
    }

    @Override
    public Collection<RpcErrorInfo> getErrorInfos() {
        return this.errorInfos;
    }

    /**
     * Increasing the error message
     * 
     * @author
     * @param errorInfo
     */
    public void addErrorInfo(final RpcErrorInfo errorInfo) {
        if(this.errorInfos == null) {
            this.errorInfos = new LinkedList<RpcErrorInfo>();
        }

        this.errorInfos.add(errorInfo);
    }

    @Override
    public int getMessageID() {
        return this.messageID;
    }

    @Override
    public boolean isOK() {
        return (this.errorInfos == null) || this.errorInfos.isEmpty();
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(CommonConstant.DEFAULT_STRING_LENGTH_128);
        builder.append("RspCmd [messageID=");
        builder.append(this.messageID);
        builder.append(']');
        return builder.toString();
    }

    @Override
    public String getResponse() {
        return response;
    }

    @Override
    public void setResponse(String response) {
        this.response = response;
    }

}
