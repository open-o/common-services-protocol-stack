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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpcReply;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.RpcMessageConstant;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.RspCmd;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.BadMessageFormatException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfErrCode;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISessionListener;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.AbstractTransport;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.ITransport;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.ITransportHandler;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.ITransportListener;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.util.VTDNavAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netconf Session <br>
 * 
 * @author
 */
public abstract class AbstractSession implements ISession, ITransportHandler, ITransportListener {

    /**
     * thread name
     */
    private String createThreadName;

    /**
     * server hello message
     */
    private ServerHello deviceHello;

    /**
     * Session id
     */
    private final int id;

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSession.class);

    /**
     * message id
     */
    private AtomicInteger messageID = new AtomicInteger(ClientHello.HELLO_MESSAGE_ID);

    /**
     * Netconf info
     */
    private final NetconfAccessInfo netconfAccessInfo;

    /**
     * Session listner
     */
    private List<ISessionListener> sessionListeners = new ArrayList<ISessionListener>();

    /**
     * transport interface
     */
    private ITransport transport;

    /**
     * contains messages waiting for reply
     */
    private Map<Integer, MessageUnit> waitingForReply =
            Collections.synchronizedMap(new HashMap<Integer, MessageUnit>());

    /**
     * AbstractSession<br>
     * <br>
     * 
     * @author
     * @param id session id
     * @param netconfAccessInfo Netconf info
     * @param createThreadName thread name of netconf session
     */
    public AbstractSession(int id, NetconfAccessInfo netconfAccessInfo, String createThreadName) {
        this.id = id;
        this.netconfAccessInfo = netconfAccessInfo;
        this.createThreadName = createThreadName;
    }

    /**
     * add session listner<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISession#addSessionListener(org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISessionListener)
     * @author
     */
    @Override
    public void addSessionListener(ISessionListener sessionListener) {
        if(null != sessionListener) {
            this.sessionListeners.add(sessionListener);
        }
    }

    /**
     * close the session<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISession#close()
     * @author
     */
    @Override
    public boolean close() {
        // check if null
        if(null != this.transport) {
            return this.transport.close();
        }

        return false;
    }

    /**
     * connect to netconf server<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISession#connect()
     * @author
     */
    @Override
    public void connect() throws NetconfException {
        // check if transport is connected
        if((null != this.transport) && this.transport.isConnected()) {
            this.LOGGER.info("transport has been connected. {}", this);
            return;
        }

        this.transport = createTransport(this.netconfAccessInfo);
        this.transport.addConnectListener(this);

        /**
         * hello message<br>
         */
        IRpc helloRpc = new ClientHello(this.netconfAccessInfo.getCommArg().getResponseTimeout());
        MessageUnit helloUnit = allocateMessageUnit(helloRpc);

        this.transport.connect(this.netconfAccessInfo, this);
        boolean success = false;
        try {
            // send hello message
            send(helloUnit);
            helloUnit.waitForReply();
            checkServerHello(helloUnit);
            success = true;
        } finally {
            this.waitingForReply.remove(helloUnit.getMessageID());
            if(!success) {
                close();
            }
        }
    }

    /**
     * checks equality<br>
     * <br>
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * @author
     */
    @Override
    public final boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        AbstractSession other = (AbstractSession)obj;
        return this.id == other.id;
    }

    /**
     * get charset name<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.transport.ITransportHandler#getCharsetName()
     * @author
     */
    @Override
    public String getCharsetName() {
        String charset = this.netconfAccessInfo.getCommArg().getCharset();
        if(null != charset) {
            return charset;
        }

        return CommunicateArg.ENCODING_UTF8;
    }

    /**
     * get access info<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISession#getNetconfAccessInfo()
     * @author
     */
    @Override
    public NetconfAccessInfo getNetconfAccessInfo() {
        return this.netconfAccessInfo;
    }

    /**
     * get package end tag<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.transport.ITransportHandler#getPacketEndTag()
     * @author
     */
    @Override
    public String getPacketEndTag() {
        return AbstractTransport.END_TAG_SUFFIX;
    }

    /**
     * Handle error<br>
     * <br>
     * 
     * @see com.trilead.ssh2.channel.ISessionHandler#handleError(java.lang.String)
     * @author
     */
    @Override
    public void handleError(String error) {
        LOGGER.info("receive rpc-reply error:[session={}, rpc-reply={}]", new Object[] {toString(), error});
        handleTransportClosed(this.transport, null);
    }

    /**
     * Handle package<br>
     * <br>
     * 
     * @see com.trilead.ssh2.channel.ISessionHandler#handlePacket(java.lang.String)
     * @author
     */
    @Override
    public void handlePacket(String response) {
        String tResponse = response;
        if((null == response) || response.isEmpty()) {
            LOGGER.info("receive rpc-reply is empty:[session={}, rpc-reply={}]", toString(), response);
            return;
        }
        try {
            if(!(CommunicateArg.ENCODING_UTF8.equals(getCharsetName()))) {
                tResponse = response.replaceAll("encoding=\"" + getCharsetName() + "\"", "encoding=\"UTF-8\"");
            }
            // replace "GBK" with "UTF-8"
            String newResponse = tResponse.replaceAll("encoding=\"" + "GBK" + "\"", "encoding=\"UTF-8\"");

            XMLStreamReader reader = VTDNavAdapter.createStAXFactory(newResponse);
            MessageUnit unit = handleDeviceResponse(reader);
            if(null != unit) {
                unit.setResponse(newResponse);
            }
        } catch(NetconfException | XMLStreamException e) {
            LOGGER.error("bad format of response. session=" + toString(), e);
        }
    }

    /**
     * disconnect the session<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.transport.ITransportListener#handleTransportClosed()
     * @author
     */
    @Override
    public void handleTransportClosed(ITransport transport, NetconfException error) {
        clearWaitingRpc(error);
        List<ISessionListener> tmpListeners = new ArrayList<ISessionListener>(this.sessionListeners);
        for(ISessionListener tmpListener : tmpListeners) {
            tmpListener.disconnect(this);
        }
    }

    /**
     * returns hashcode<br>
     * <br>
     * 
     * @see java.lang.Object#hashCode()
     * @author
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + this.id;
        return result;
    }

    /**
     * check if connected<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISession#isConnected()
     * @author
     */
    @Override
    public boolean isConnected() {
        if(this.transport != null) {
            return this.transport.isConnected();
        }

        return false;
    }

    /**
     * Send rpc sync request<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISession#synSend(org.openo.commsvc.connector.netconf.service.svc.protocol.datadefination.IRpc)
     * @author
     */
    @Override
    public IRpcReply synSend(IRpc reqCmd) throws NetconfException {
        if(null == reqCmd) {
            throw new NetconfException(NetconfErrCode.INTERNAL_ERROR, "can't send null rpc request.");
        }

        MessageUnit unit = allocateMessageUnit(reqCmd);
        try {
            // send
            send(unit);

            // wait for reply
            unit.waitForReply();
        } finally {
            this.waitingForReply.remove(unit.getMessageID());
        }

        return unit.getResp();
    }

    /**
     * convert to string<br>
     * <br>
     * 
     * @see java.lang.Object#toString()
     * @author
     */
    @Override
    public String toString() {

        return "AbstractSession[" + this.id + "," + this.createThreadName + "," + this.netconfAccessInfo.getDevIp()
                + "]";
    }

    /**
     * Transport abstract class <br>
     * 
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     * @param netconfAccessInfo Netconf info
     * @return ITransport
     * @throws NetconfException
     */
    protected abstract ITransport createTransport(NetconfAccessInfo netconfAccessInfo) throws NetconfException;

    /**
     * @return Returns the LOGGER.
     */
    protected Logger getLogger() {
        return this.LOGGER;
    }

    /**
     * @return Returns the transport.
     */
    protected ITransport getTransport() {
        return this.transport;
    }

    /**
     * @param transport The transport to set.
     */
    protected void setTransport(ITransport transport) {
        this.transport = transport;
    }

    /**
     * add rpc command <br>
     * 
     * @author
     * @param reqCmd
     * @param timeout
     * @param handler
     * @return MessageUnit
     */
    private MessageUnit allocateMessageUnit(IRpc reqCmd) {
        // set timeout
        reqCmd.setTimeout(this.netconfAccessInfo.getCommArg().getResponseTimeout());
        String charset = this.netconfAccessInfo.getCommArg().getCharset();
        if(null != charset) {
            reqCmd.setCharset(charset);
        }
        int msgID = this.messageID.getAndIncrement();
        MessageUnit unit = new MessageUnit(this, reqCmd, msgID);

        this.waitingForReply.put(unit.getMessageID(), unit);
        return unit;
    }

    /**
     * Verify the hello reponse message <br>
     * 
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     * @param helloUnit Hello message
     * @throws NetconfException
     */
    private void checkServerHello(MessageUnit helloUnit) throws NetconfException {
        IRpcReply serverHello = helloUnit.getResp();
        if((null == serverHello) || !(serverHello instanceof ServerHello)) {
            throw new NetconfException(NetconfErrCode.INTERNAL_ERROR,
                    "serverHello is null or is not ServerHello. " + serverHello + this);
        }

        if(null == ((ServerHello)serverHello).getSessionID()) {
            throw new NetconfException(NetconfErrCode.SERVER_HELLO_SESSSION_ID_ERROR,
                    "serverHello session-id is null." + this);
        }
    }

    /**
     * clear all the waiting rpc replies<br>
     * 
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     * @param e netconf exception
     */
    private void clearWaitingRpc(NetconfException e) {
        synchronized(this.waitingForReply) {
            Iterator<MessageUnit> iter = this.waitingForReply.values().iterator();
            while(iter.hasNext()) {
                iter.next().setError(e);
                iter.remove();
            }
        }
    }

    /**
     * xml reader<br>
     * 
     * @author
     * @param reader
     * @throws BadMessageFormatException
     * @throws XMLStreamException
     */
    MessageUnit handleDeviceResponse(XMLStreamReader reader) throws BadMessageFormatException, XMLStreamException {
        MessageUnit unit = null;
        while(reader.hasNext()) {
            int event = reader.next();
            if(event != XMLStreamConstants.START_ELEMENT) {
                continue;
            }

            IRpcReply reply = null;
            if(ServerHello.HELLO_HEAD.equals(reader.getLocalName())) {
                this.deviceHello = new ServerHello(reader);
                reply = this.deviceHello;
            } else if(RpcMessageConstant.ELE_RPCREPLY.equals(reader.getLocalName())
                    || RpcMessageConstant.ELE_ACTIVE.equals(reader.getLocalName())) {
                reply = new RspCmd(reader);
            } else {
                this.LOGGER.info("didn't know reply tag:{}", reader.getLocalName());
            }

            unit = handleReplay(reply);

            break;
        }

        return unit;
    }

    /**
     * Handle netconf reply<br>
     * 
     * @author
     * @param unit
     * @param reply
     * @return MessageUnit
     */
    private MessageUnit handleReplay(IRpcReply reply) {
        MessageUnit unit = null;
        if(null != reply) {
            Integer messageId = reply.getMessageID();
            unit = this.waitingForReply.get(messageId);
            if(null != unit) {
                this.LOGGER.info("received message-id={}", reply.getMessageID());
                unit.setResp(reply);
                if(!unit.isActive()) {
                    this.waitingForReply.remove(messageId);
                }
            } else {
                this.LOGGER.info("drop message-id={}", reply.getMessageID());
            }
        }
        return unit;
    }

    /**
     * Send message<br>
     * 
     * @author
     * @param unit
     * @throws NetconfException
     */
    private void send(MessageUnit unit) throws NetconfException {
        send(unit, unit.serialize());
    }

    /**
     * Send request <br>
     * 
     * @author
     * @param request
     * @throws NetconfException
     */
    private void send(MessageUnit unit, String request) throws NetconfException {
        long start = System.currentTimeMillis();
        try {
            getTransport().send(request);
        } finally {
            long elapse = System.currentTimeMillis() - start;
            this.LOGGER.info("send rpc end:[session={}, elapse={}]", new Object[] {toString(), elapse});
        }
    }
}
