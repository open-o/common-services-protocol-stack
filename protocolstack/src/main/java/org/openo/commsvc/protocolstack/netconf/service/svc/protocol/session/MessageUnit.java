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

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpcReply;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfTimeoutException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.IAsyncRpcHandler;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message unit for netconf communication<br>
 * <p>
 * </p>
 * 
 * @author
 * @version GSO 0.5 Sep 7, 2016
 */
public class MessageUnit {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageUnit.class);

    private IRpc req;

    private int messageID;

    private IRpcReply resp;

    private NetconfException error;

    private IAsyncRpcHandler handler;

    private long start;

    private boolean sent;

    private ISession session;

    /**
     * Message unit for RPC communication<br/>
     * <p>
     * </p>
     * 
     * @param session - Session interface
     * @param req - IPC request
     * @param messageID - Message ID
     * @param handler - Aysnch handler
     * @since GSO 0.5
     */
    public MessageUnit(ISession session, IRpc req, int messageID, IAsyncRpcHandler handler) {
        this.session = session;
        this.req = req;
        this.messageID = messageID;
        this.handler = handler;
        this.start = System.currentTimeMillis();
    }

    /**
     * Message unit for RPC communication<br/>
     * <p>
     * </p>
     * 
     * @param session - Session interface
     * @param req - IPC request
     * @param messageID - Message ID
     * @since GSO 0.5
     */
    public MessageUnit(ISession session, IRpc req, int messageID) {
        this(session, req, messageID, null);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        MessageUnit other = (MessageUnit)obj;
        if(this.messageID != other.messageID) {
            return false;
        }
        return true;
    }

    public NetconfException getError() {
        return this.error;
    }

    public IAsyncRpcHandler getHandler() {
        return this.handler;
    }

    public int getMessageID() {
        return this.messageID;
    }

    public IRpc getReq() {
        return this.req;
    }

    public IRpcReply getResp() {
        return this.resp;
    }

    public long getTimeout() {
        return this.req.getTimeout();
    }

    /**
     * Get remaining time<br>
     * 
     * @return remaining time
     * @since GSO 0.5
     */
    public long getRemaining() {
        return this.req.getTimeout() - (System.currentTimeMillis() - this.start);
    }

    public long getElapse() {
        return System.currentTimeMillis() - this.start;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.messageID;
        return result;
    }

    /**
     * Is the session active<br>
     * 
     * @return
     * @since GSO 0.5
     */
    public boolean isActive() {
        return (this.resp != null) && this.resp.isActive();
    }

    /**
     * Whether asynchronized<br>
     * 
     * @return c
     * @since GSO 0.5
     */
    public boolean isAsynchronized() {
        return this.handler != null;
    }

    /**
     * Is Error<br>
     * 
     * @return DEFAULT_STRING_LENGTH_64
     * @since GSO 0.5
     */
    public boolean isError() {
        return this.error != null;
    }

    /**
     * Is finished <br>
     * 
     * @return true or false
     * @since GSO 0.5
     */
    public boolean isFinished() {
        return !isActive() && ((this.resp != null) || (this.error != null));
    }

    /**
     * serialize the message id for rpc communication<br>
     * 
     * @return serialized string
     * @throws NetconfException
     * @since GSO 0.5
     */
    public String serialize() throws NetconfException {
        return this.req.toCommand(this.messageID);
    }

    /**
     * Set the netconf error<br>
     * 
     * @param error
     * @since GSO 0.5
     */
    public void setError(NetconfException error) {
        synchronized(this) {
            this.error = error;
            notifyAll();
        }
    }

    public void setHandler(IAsyncRpcHandler handler) {
        this.handler = handler;
    }

    public void setReq(IRpc req) {
        this.req = req;
    }

    /**
     * The response has been received . Wake waiting thread
     * 
     * @author
     * @see
     * @param resp
     */
    public void setResp(IRpcReply resp) {
        if(isAsynchronized()) {
            this.handler.handle(this.req, resp);
        } else {
            synchronized(this) {
                this.resp = resp;
                notifyAll();
            }
        }
    }

    /**
     * Receipt<active>After restarting . <br>
     * 
     * @author
     * @see
     */
    public void setStart() {
        this.start = System.currentTimeMillis();
        this.resp = null;
    }

    public boolean isSent() {
        return this.sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setResponse(String response) {
        resp.setResponse(response);
    }

    /**
     * <br>
     * 
     * @author
     * @see
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     * @param timeout
     * @throws NetconfException
     */
    public void waitForReply() throws NetconfException {
        long tmpStart = this.start;
        synchronized(this) {
            while(!isFinished()) {
                if(getRemaining() <= 0) {
                    throw new NetconfTimeoutException(toString(), getTimeout(), getElapse());
                }

                try {
                    wait(getRemaining());
                } catch(InterruptedException e) {
                    // Since receiving the news they wait too long
                    throw new NetconfTimeoutException(toString(), getTimeout(), getElapse(), e);
                }

                // If you receive <active> packet , reset timeout。
                if(isActive()) {
                    setStart();
                }
            }
        }

        LOGGER.info(toString() + " use time:" + (System.currentTimeMillis() - tmpStart));

        // If you receive an exception is encountered .
        if(isError()) {
            throw getError();
        }

    }

    @Override
    public String toString() {
        return "MessageUnit [messageID=" + this.messageID + ", session=" + this.session + "]";
    }
}
