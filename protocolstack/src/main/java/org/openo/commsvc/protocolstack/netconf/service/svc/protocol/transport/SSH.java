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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.AuthenticationException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfErrCode;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfTimeoutException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.TransportIOException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSubsystem;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * SSH Connection class<br>
 * 
 * @author
 */
public final class SSH extends AbstractTransport {

    /**
     * timeout factor
     */
    private static final int CONNECT_TIMEOUT_FACTOR = 3;

    /**
     * default Channel timeout
     */
    private static final int DEFAULT_CHANNEL_CONNECT_TIME_OUT = 20000;

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SSH.class);

    /**
     * <p>
     * Transport Handler
     * </p>
     */
    private ITransportHandler transportHandler;

    /**
     * Controller communication parameters
     */
    private CommunicateArg arg;

    /**
     * SSH via JSch
     */
    private JSch jsch;

    /**
     * SSH session
     */
    private Session sess;

    /**
     * Channel
     */
    private ChannelSubsystem subsysChannel;

    /**
     * input stream
     */
    private InputStream in;

    /**
     * output stream
     */
    private OutputStream out;

    /**
     * error stream
     */
    private InputStream err;

    /**
     * Close connection<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.transport.ITransport#close()
     * @author
     */
    @Override
    public synchronized boolean close() {
        boolean success = null != this.jsch;
        this.listeners.clear();
        closeSession();

        LOGGER.info("close transport ip={}, success={}", this.arg.getIp(), success);
        return success;
    }

    /**
     * Connect based on netconf access information<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.transport.ITransport#connect(org.openo.commsvc.connector.netconf.service.svc.protocol.service.NetconfAccessInfo,
     *      org.openo.commsvc.connector.netconf.service.svc.protocol.transport.ITransportHandler)
     * @author
     */
    @Override
    public synchronized void connect(final NetconfAccessInfo netconfAccessInfo,
            final ITransportHandler transportHandler) throws NetconfException {
        if(isConnected()) {
            return;
        }

        this.arg = netconfAccessInfo.getCommArg();
        if(null == this.arg) {
            throw new NetconfException(NetconfErrCode.INTERNAL_ERROR,
                    "can't connect to device with null communicate argument.");
        }

        if(null == transportHandler) {
            throw new NetconfException(NetconfErrCode.INTERNAL_ERROR, "transportHandler is null. arg=" + this.arg);
        }

        boolean success = false;
        final ConnectTimeRecord timeRecord = new ConnectTimeRecord();
        FutureTask<Void> future = new FutureTask<Void>(new Callable<Void>() {

            /**
             * Call<br>
             * <br>
             * 
             * @see java.util.concurrent.Callable#call()
             * @author
             */
            @Override
            public Void call() throws NetconfException {
                connectNetconf(netconfAccessInfo, transportHandler, timeRecord);
                return null;
            }
        });

        Executors.newSingleThreadExecutor().submit(future);

        long start = System.currentTimeMillis();
        try {
            future.get(getConnectTimeout(), TimeUnit.MILLISECONDS);
            success = true;
        } catch(TimeoutException e) {
            LOGGER.error(timeRecord.toString(), e);
            throw new NetconfTimeoutException("connectNetconf", this.arg.getLoginTimeout(),
                    System.currentTimeMillis() - start, e);
        } catch(ExecutionException e) {
            LOGGER.error("connect ex.", e);
            Throwable cause = e.getCause();
            if(cause instanceof NetconfException) {
                throw (NetconfException)cause;
            }
            throw new NetconfException(NetconfErrCode.INTERNAL_ERROR, cause);
        } catch(Exception e) {
            throw new NetconfException(NetconfErrCode.INTERNAL_ERROR, e);
        } finally {
            if(!success) {
                cancelConnectThread(future);
                close();
            }
        }
    }

    /**
     * Cancel connect thread <br>
     * 
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     * @param future
     */
    private void cancelConnectThread(FutureTask<Void> future) {
        try {
            future.cancel(true);
        } catch(RuntimeException e) {
            LOGGER.error("cancelConnectThread error!", e);
        }
    }

    /**
     * send netconf request<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.transport.ITransport#send(java.lang.String)
     * @author
     * @throws ServiceException
     */
    @Override
    public synchronized void send(String request) throws NetconfException {
        if(null == request) {
            LOGGER.warn("request is null, no need to be send.");
            return;
        }

        if(!isConnected()) {
            LOGGER.error("sendMessage error. " + toString());
            throw new TransportIOException(this.arg.getIp(), new IOException("ssh is not connected"));
        }

        long start = System.currentTimeMillis();

        String tRequest = appendSuffix(request);
        byte[] bytes = tRequest.getBytes();
        try {
            this.out.write(bytes);
            this.out.flush();
            resolveStdoutData();
            resolveErroutData();
        } catch(IOException e) {
            LOGGER.error("sendMessage error. " + toString(), e);
            throw new TransportIOException(this.arg.getIp(), e);
        } finally {
            clearBytes(bytes);
        }

        long end = System.currentTimeMillis();
        long elapse = end - start;
        averageSendRate(bytes.length, elapse);
    }

    /**
     * Parse stdout data<br>
     * 
     * @author
     * @throws IOException
     */
    private void resolveStdoutData() throws IOException {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(this.in);
        scanner.useDelimiter(END_TAG_SUFFIX);
        StringBuilder sb = new StringBuilder();
        Object lock = new Object();
        while(true) {
            if(scanner.hasNext()) {
                sb.append(scanner.next());
                break;
            }

            if(this.subsysChannel.isClosed()) {
                LOGGER.error("SSH exit status : " + this.subsysChannel.getExitStatus());
                throw new IOException("failed to read std stream!");
            }

            try {
                synchronized(lock) {
                    lock.wait(1000);
                }
            } catch(InterruptedException e) {
                LOGGER.error("read SSH response interrupted!", e);
                throw new IOException("failed to wait reading std stream!");
            }
        }

        this.transportHandler.handlePacket(sb.toString());
    }

    /**
     * Parse error data<br>
     * 
     * @author
     * @throws IOException
     */
    private void resolveErroutData() throws IOException {
        byte[] bytesIn = new byte[1024];
        int size = 0;
        int len = 0;

        List<byte[]> errList = new ArrayList<byte[]>();
        size = 0;
        InputStream errIn = this.err;
        while(errIn.available() > 0) {
            len = errIn.read(bytesIn);
            errList.add(Arrays.copyOfRange(bytesIn, 0, len));
            size += len;
        }
        String errOutString = genOutputString(errList, size);
        if(null != errOutString) {
            LOGGER.error("all err out : " + errOutString);
            this.transportHandler.handleError(errOutString);
        }
    }

    /**
     * get the output in string<br>
     * 
     * @author
     * @param bytesList
     * @param size
     * @return String
     */
    private String genOutputString(List<byte[]> bytesList, int size) {
        if(0 == size) {
            return null;
        }

        byte[] allBytes = new byte[size];
        int index = 0;

        for(byte[] bytes : bytesList) {
            for(byte b : bytes) {
                allBytes[index++] = b;
            }
        }
        return new String(allBytes);
    }

    /**
     * Covert to string<br>
     * <br>
     * 
     * @see java.lang.Object#toString()
     * @author
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SSH [arg=");
        builder.append(this.arg);
        builder.append(']');
        return builder.toString();
    }

    /**
     * authenticate the netconf server details<br>
     * 
     * @author
     * @throws AuthenticationException
     */
    private void authenticate() throws AuthenticationException {
        try {
            switch(this.arg.getAuthType()) {
                case CommunicateArg.PASSWORD_AUTH: {
                    // TODO: Whether password is clear or encrypted?
                    
                    this.sess.setPassword(this.arg.getPassword());
                    this.sess.connect(this.arg.getLoginTimeout());
                    break;
                }
                default: {
                    LOGGER.warn("illegal authenticate type, ignore.");
                }
            }
        } catch(JSchException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AuthenticationException(this.arg.getIp(), e);
        }
    }

    /**
     * Close session<br>
     * 
     * @author
     */
    private synchronized void closeSession() {
        try {
            closeStream(this.in);
            this.in = null;
            closeStream(this.out);
            this.out = null;
            closeStream(this.err);
            this.err = null;
            if(null != this.subsysChannel) {
                this.subsysChannel.disconnect();
            }

            if(null != this.sess) {
                this.sess.disconnect();
            }
        } catch(RuntimeException e) {
            LOGGER.error("close session error. ip=" + this.arg.getIp(), e);
        } finally {
            this.subsysChannel = null;
            this.sess = null;
            this.connected.set(false);
        }
    }

    /**
     * Close stream<br>
     * 
     * @author
     * @param stream
     */
    private void closeStream(Closeable stream) {
        try {
            if(null != stream) {
                stream.close();
            }
        } catch(IOException e) {
            LOGGER.error("failed to close stream", e);
        }
    }

    /**
     * Connect netconf server<br>
     * 
     * @author
     * @param netconfAccessInfo
     * @param transportHandler
     * @param timeRecord
     * @throws NetconfException
     */
    private void connectNetconf(NetconfAccessInfo netconfAccessInfo, ITransportHandler transportHandler,
            ConnectTimeRecord timeRecord) throws NetconfException {
        boolean success = false;
        try {
            timeRecord.record("new Connection");
            this.jsch = new JSch();
            timeRecord.record("connect");
            this.sess = this.jsch.getSession(this.arg.getUserName(), this.arg.getIp(), this.arg.getPort());
            if(null == this.sess) {
                LOGGER.error("failed to create session!");
                throw new NetconfException(NetconfErrCode.INTERNAL_ERROR);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            this.sess.setConfig(config);

            timeRecord.record("authenticate");
            authenticate();

            // Create a session
            timeRecord.record("openSession");
            Channel channel = this.sess.openChannel("subsystem");
            if(null == channel) {
                LOGGER.error("failed to open SSH channel");
                throw new NetconfException(NetconfErrCode.INTERNAL_ERROR);
            }
            this.subsysChannel = (ChannelSubsystem)channel;
            this.subsysChannel.setSubsystem("netconf");

            this.transportHandler = transportHandler;
            this.in = this.subsysChannel.getInputStream();
            this.out = this.subsysChannel.getOutputStream();
            this.err = this.subsysChannel.getErrStream();
            this.subsysChannel.connect();

            timeRecord.record("set");
            this.connected.set(true);
            timeRecord.record("end");
            success = true;
        } catch(AuthenticationException e) {
            LOGGER.error("authenticate fail, close transport.");
            throw e;
        } catch(Exception e) {
            LOGGER.error("1failed to login the ssh server", e);
            throw new NetconfException(NetconfErrCode.INTERNAL_ERROR, e);
        } finally {
            if(!success) {
                close();
            }

        }
    }

    /**
     * Get connection timeout<br>
     * 
     * @author
     * @return long
     */
    private long getConnectTimeout() {
        return (this.arg.getLoginTimeout() * CONNECT_TIMEOUT_FACTOR) + DEFAULT_CHANNEL_CONNECT_TIME_OUT;
    }

    /**
     * check if connected<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.transport.ITransport#isConnected()
     * @author
     */
    @Override
    public synchronized boolean isConnected() {
        return super.isConnected() && ((null != this.sess) && this.sess.isConnected())
                && ((null != this.subsysChannel) && this.subsysChannel.isConnected());
    }

    /**
     * clear the bytes<br>
     * 
     * @author
     * @see
     * @param bytes
     */
    private void clearBytes(byte[] bytes) {
        if(null == bytes) {
            return;
        }

        int length = bytes.length;
        for(int i = 0; i < length; i++) {
            bytes[i] = 0;
        }
    }
}

/**
 * ConnectTimeRecord<br>
 * 
 * @author
 */
class ConnectTimeRecord {

    /**
     * line separator
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * builder
     */
    private StringBuilder builder = new StringBuilder("ConnectTimeRecord ");

    /**
     * date format
     */
    private static final String DATE_FORMAT = "dd HH:mm:ss.SSSZ";

    /**
     * operation name
     */
    private String operName = null;

    /**
     * start time
     */
    private long startTime = System.currentTimeMillis();

    /**
     * convert to string<br>
     * <br>
     * 
     * @see java.lang.Object#toString()
     * @author
     */
    @Override
    public String toString() {
        return this.builder.toString();
    }

    /**
     * Record the operations<br>
     * 
     * @author
     * @param operName
     */
    void record(String operName) {
        long tmpTime = System.currentTimeMillis();
        if(null != this.operName) {
            this.builder.append("end ").append(this.operName).append(':');
            this.builder.append(tmpTime - this.startTime).append(LINE_SEPARATOR);
        }
        this.operName = operName;
        this.startTime = tmpTime;
        this.builder.append("start ").append(operName).append(':').append(tmpTime).append(',')
                .append(getFormatTime(tmpTime)).append(LINE_SEPARATOR);
    }

    /**
     * Get formatted time<br>
     * 
     * @author
     * @param time
     * @return String
     */
    private String getFormatTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(new Date(time));
    }

}
