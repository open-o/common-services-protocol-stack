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

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.NetconfAbility;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

/**
 * Client Hello
 * <br>
 * 
 * @author
 */
public class ClientHello implements IRpc {

    /**
     * HELLO The message-ID message
     */
    public static final int HELLO_MESSAGE_ID = 0;

    private long timeout;

    /**
     * Globalization requirements , equipment char set word
     */
    private String charset = CommunicateArg.ENCODING_UTF8;

    /**
     * Constructor<br/>
     * <p>
     * Client Hello
     * </p>
     * 
     * @param timeout.
     */

    public ClientHello(long timeout) {
        this.timeout = timeout;
    }

    /**
     * HELLO packets generated
     * 
     * @author
     * @return Hello packets
     *         <?xml version="1.0" encoding="UTF-8"?>
     *         <hello xmlns="urn:ietf:params:xml:ns:netconf:base:1.0">
     *         <capabilities>
     *         <capability>urn:ietf:params:netconf:base:1.0</capability>
     *         <capability>urn:ietf:params:netconf:capability:writable-running:1.0</capability>
     *         <capability>urn:ietf:params:netconf:capability:candidate:1.0</capability>
     *         <capability>urn:ietf:params:netconf:capability:confirmed-commit:1.0</capability>
     *         <capability>urn:ietf:params:netconf:capability:rollback-on-error:1.0</capability>
     *         <capability>urn:ietf:params:netconf:capability:validate:1.0</capability>
     *         <capability>urn:ietf:params:netconf:capability:startup:1.0</capability>
     *         <capability>urn:ietf:params:netconf:capability:url:1.0?protocol=ftp</capability>
     *         <capability>urn:ietf:params:netconf:capability:xpath:1.0</capability>
     *         </capabilities>
     *         </hello>
     */
    public String clientHello() {

        return "<?xml version=\"1.0\" encoding=\"" + this.charset + "\"?>"
                + "<hello xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">" + NetconfAbility.getClientabilityhello()
                + "</hello>";
    }

    /**
     * Implementation<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.datadefination.IRpc#getContent()
     * @author
     */
    @Override
    public String getContent() {
        return null;
    }

    /**
     * Implementation <br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.datadefination.IRpc#getLrID()
     * @author
     */
    @Override
    public Integer getLrID() {
        return null;
    }

    @Override
    public long getTimeout() {
        return this.timeout;
    }

    /**
     * Implementation<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.datadefination.IRpc#getVrID()
     * @author
     */
    @Override
    public Integer getVrID() {
        return null;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toCommand(int messageID) throws NetconfException {
        return clientHello();
    }

    @Override
    public String getCharset() {
        return this.charset;
    }

    @Override
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
