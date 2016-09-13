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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transport layer abstraction, implementations must ensure that all methods are thread<br>
 * 
 * @author
 * @see
 */
public abstract class AbstractTransport implements ITransport {

    public static final String END_TAG_SUFFIX = "]]>]]>";

    public static final byte[] END_TAG_BYTES = END_TAG_SUFFIX.getBytes();

    public static final int END_TAG_LEN = END_TAG_BYTES.length;

    protected static final String SUBSYSTEM_NETCONF = "netconf";

    protected StringBuffer recvBuffer;

    protected AtomicBoolean connected;

    protected double sendRate = 0.0;

    protected double recvRate = 0.0;

    protected List<ITransportListener> listeners;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTransport.class);

    /**
     * Abstract Transport Constructor
     * 
     * @author
     * @see
     */
    public AbstractTransport() {
        this.listeners = new ArrayList<ITransportListener>();
        this.connected = new AtomicBoolean(false);
    }

    /**
     * Get Received Buffer
     * 
     * @author
     * @see
     * @return recvBuffer
     */
    public StringBuffer getRecvBuffer() {
        return this.recvBuffer;
    }

    @Override
    public boolean isConnected() {
        return this.connected.get();
    }

    /**
     * Get Bytes
     * 
     * @author
     * @see
     * @param data
     * @return bytes
     */
    public static byte[] getBytes(String data) {
        if(data == null) {
            return new byte[0];
        }

        byte[] bytes = null;
        try {
            bytes = data.getBytes("UTF-8");
        } catch(UnsupportedEncodingException e) {
            LOGGER.error("Unsupported Encoding exception", e);
            bytes = data.getBytes();
        }

        return bytes;
    }

    /**
     * Append Suffix
     * 
     * @author
     * @see
     * @param data
     * @return data suffix
     */
    public static String appendSuffix(String data) {
        if(data == null) {
            return data;
        }

        if(data.endsWith(END_TAG_SUFFIX)) {
            return data;
        } else {
            return data + END_TAG_SUFFIX;
        }
    }

    protected void averageSendRate(long bytes, long elapse) {
        if(elapse == 0) {
            return;
        }

        double rate = (double)bytes / (double)elapse / 1000;

        if(this.sendRate == 0.0) {
            this.sendRate = rate;
        } else {
            this.sendRate = (this.sendRate + rate) / 2.0;
        }
    }

    protected void averageRecvRate(long bytes, long elapse) {
        if(elapse == 0) {
            return;
        }

        double rate = (double)bytes / (double)elapse / 1000;
        if(this.recvRate == 0.0) {
            this.recvRate = rate;
        } else {
            this.recvRate = (this.recvRate + rate) / 2.0;
        }
    }

    public double getSendRate() {
        return this.sendRate;
    }

    public double getRecvRate() {
        return this.recvRate;
    }

    /**
     * Implementation
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.ITransport#addConnectListener(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.ITransportListener)
     * @author
     * @param connectListener
     */
    @Override
    public void addConnectListener(ITransportListener connectListener) {
        if(null != connectListener) {
            this.listeners.add(connectListener);
        }
    }

}
