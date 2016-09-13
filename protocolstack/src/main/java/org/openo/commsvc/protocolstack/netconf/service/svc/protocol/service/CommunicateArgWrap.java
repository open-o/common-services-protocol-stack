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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfErrCode;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr.CommunicateArgChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CommunicateArgWrap class extends the CommunicateArg and holds the information for
 * netconf connection.
 * <br>
 * 
 * @author
 * @version     GSO 0.5  Sep 8, 2016
 */
class CommunicateArgWrap extends CommunicateArg {

    private static final int CORRECT_DEVID_SIZE = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunicateArgWrap.class);

    private static final int USER_LEVEL = 3;

    private CommunicateArg communicateArg;

    private Map<String, String> controllerIds = new HashMap<String, String>();

    private CommunicateArgChangeListener listener;

    /**
     * 
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @param communicateArg
     * @param listener
     * @since  GSO 0.5
     */
    CommunicateArgWrap(CommunicateArg communicateArg, CommunicateArgChangeListener listener) {
        super(null, null, null, 0, null, 0, 0);
        this.communicateArg = communicateArg;
        this.listener = listener;
    }

    /**
     * 
     * <br/>
     * 
     * @param obj
     * @return
     * @since   GSO 0.5
     */
    @Override
    public boolean equals(Object obj) {
        return this.communicateArg.equals(obj);
    }

    /**
     * 
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public String getCharset() {
        return this.communicateArg.getCharset();
    }

    /**
     * 
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public String getIp() {
        return this.communicateArg.getIp();
    }

    /**
     * è¦†ç›–æ–¹æ³•<br>
     * <br>
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg#getKey()
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     */

    /**
     * Get the login timeout.
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public int getLoginTimeout() {
        return this.communicateArg.getLoginTimeout();
    }

    /**
     * Get the password.
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public String getPassword() {
        return this.communicateArg.getPassword();
    }

    /**
     * Get the port number
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public int getPort() {
        return this.communicateArg.getPort();
    }

    /**
     * Get the protocol.
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public String getProtocol() {
        return this.communicateArg.getProtocol();
    }

    /**
     * Get the response timeout.
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public int getResponseTimeout() {
        return this.communicateArg.getResponseTimeout();
    }

    /**
     * 
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public String getType() {
        return this.communicateArg.getType();
    }

    /**
     * 
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public String getUserName() {
        return this.communicateArg.getUserName();
    }

    /**
     * 
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public int hashCode() {
        return this.communicateArg.hashCode();
    }

    /**
     * 
     * <br/>
     * 
     * @param charset
     * @since   GSO 0.5
     */
    @Override
    public void setCharset(String charset) {
        String oldCharset = this.communicateArg.getCharset();
        this.communicateArg.setCharset(charset);
        String newCharset = this.communicateArg.getCharset();
        if(!ObjectUtils.equals(oldCharset, newCharset)) {
            LOGGER.info("change charset:{} to {}. {},{},{}",
                    new Object[] {oldCharset, charset, this.controllerIds, this.communicateArg.getIp(), getAppInfo()});
            closeDevNetconfCache();
        }
    }

    /**
     * 
     * <br/>
     * 
     * @param ip
     * @since   GSO 0.5
     */
    @Override
    public void setIp(String ip) {
        String oldIp = this.communicateArg.getIp();
        this.communicateArg.setIp(ip);
        String newIp = this.communicateArg.getIp();
        if(!ObjectUtils.equals(oldIp, newIp)) {

            LOGGER.info("change ip:{} to {}. {},{},{}",
                    new Object[] {oldIp, ip, this.controllerIds, this.communicateArg.getIp(), getAppInfo()});

            for(String controllerId : getControllerIds()) {
                closeDevNetconfCache(controllerId, oldIp);
            }
        }
    }

    /**
     * 
     * <br/>
     * 
     * @param loginTimeout
     * @since   GSO 0.5
     */
    @Override
    public void setLoginTimeout(int loginTimeout) {
        long oldLoginTimeout = this.communicateArg.getLoginTimeout();
        this.communicateArg.setLoginTimeout(loginTimeout);
        long newLoginTimeout = this.communicateArg.getLoginTimeout();
        if(!ObjectUtils.equals(oldLoginTimeout, newLoginTimeout)) {

            LOGGER.info("change loginTimeout:{} to {}. {},{},{}", new Object[] {oldLoginTimeout, loginTimeout,
                            this.controllerIds, this.communicateArg.getIp(), getAppInfo()});

            closeDevNetconfCache();
        }
    }

    /**
     * 
     * <br/>
     * 
     * @param password
     * @since   GSO 0.5
     */
    @Override
    public void setPassword(String password) {
        String oldPassword = this.communicateArg.getPassword();
        this.communicateArg.setPassword(password);
        String newPassword = this.communicateArg.getPassword();
        if(!ObjectUtils.equals(oldPassword, newPassword)) {

            LOGGER.info("change p. {},{},{}",
                    new Object[] {this.controllerIds, this.communicateArg.getIp(), getAppInfo()});

            closeDevNetconfCache();
        }
    }

    /**
     * 
     * <br/>
     * 
     * @param port
     * @since   GSO 0.5
     */
    @Override
    public void setPort(int port) {
        int oldPort = this.communicateArg.getPort();
        this.communicateArg.setPort(port);
        int newPort = this.communicateArg.getPort();
        if(!ObjectUtils.equals(oldPort, newPort)) {
            LOGGER.info("change port:{} to {}. {},{},{}",
                    new Object[] {oldPort, port, this.controllerIds, this.communicateArg.getIp(), getAppInfo()});
            closeDevNetconfCache();
        }
    }

    /**
     * 
     * <br/>
     * 
     * @param protocol
     * @since   GSO 0.5
     */
    @Override
    public void setProtocol(String protocol) {
        String oldProtocol = this.communicateArg.getProtocol();
        this.communicateArg.setProtocol(protocol);
        String newProtocol = this.communicateArg.getProtocol();
        if(!ObjectUtils.equals(oldProtocol, newProtocol)) {
            LOGGER.info("change protocol:{} to {}. {},{},{}", new Object[] {oldProtocol, protocol, this.controllerIds,
                            this.communicateArg.getIp(), getAppInfo()});
            closeDevNetconfCache();
        }
    }

    /**
     * 
     * <br/>
     * 
     * @param responseTimeout
     * @since   GSO 0.5
     */
    @Override
    public void setResponseTimeout(int responseTimeout) {
        long oldResponseTimeout = this.communicateArg.getResponseTimeout();
        this.communicateArg.setResponseTimeout(responseTimeout);
        long newResponseTimeout = this.communicateArg.getResponseTimeout();
        if(!ObjectUtils.equals(oldResponseTimeout, newResponseTimeout)) {
            LOGGER.info("change responseTimeout:{} to {}. {},{},{}", new Object[] {oldResponseTimeout, responseTimeout,
                            this.controllerIds, this.communicateArg.getIp(), getAppInfo()});
            closeDevNetconfCache();
        }
    }

    /**
     * 
     * <br/>
     * 
     * @param type
     * @since   GSO 0.5
     */
    @Override
    public void setType(String type) {
        String oldType = this.communicateArg.getType();
        this.communicateArg.setType(type);
        String newType = this.communicateArg.getType();
        if(!ObjectUtils.equals(oldType, newType)) {
            LOGGER.info("change type:{} to {}. {},{},{}",
                    new Object[] {oldType, type, this.controllerIds, this.communicateArg.getIp(), getAppInfo()});

            closeDevNetconfCache();
        }
    }

    /**
     * 
     * <br/>
     * 
     * @param userName
     * @since   GSO 0.5
     */
    @Override
    public void setUserName(String userName) {
        String oldUserName = this.communicateArg.getUserName();
        this.communicateArg.setUserName(userName);
        String newUserName = this.communicateArg.getUserName();
        if(!ObjectUtils.equals(oldUserName, newUserName)) {
            closeDevNetconfCache();
        }
    }

    /**
     * 
     * <br/>
     * 
     * @return
     * @since   GSO 0.5
     */
    @Override
    public String toString() {
        return this.communicateArg.toString();
    }

    @Override
    public String getControllerId() {
        return this.communicateArg.getControllerId();
    }

    @Override
    public void setControllerId(String controllerId) {
        this.communicateArg.setControllerId(controllerId);
    }

    @Override
    public int getMaxConnNum() {
        return this.communicateArg.getMaxConnNum();
    }

    @Override
    public void setMaxConnNum(int maxConnNum) {
        this.communicateArg.setMaxConnNum(maxConnNum);
    }

    /**
     * 
     * <br>
     * 
     * @since  GSO 0.5
     */
    void closeDevNetconfCache() {
        for(String controllerId : getControllerIds()) {
            closeDevNetconfCache(controllerId, this.communicateArg.getIp());
        }
    }

    /**
     * 
     * <br>
     * 
     * @param controllerId
     * @since  GSO 0.5
     */
    void setDevId(String controllerId) {
        synchronized(this.controllerIds) {
            if(!this.controllerIds.containsKey(controllerId)) {
                this.controllerIds.put(controllerId, controllerId);
                if(this.controllerIds.size() > CORRECT_DEVID_SIZE) {
                    LOGGER.warn(
                            new StringBuilder("devIds=").append(this.controllerIds).append(", devIp=")
                                    .append(this.communicateArg.getIp()).toString(),
                            new NetconfException(NetconfErrCode.INTERNAL_ERROR));
                }
            }
        }
    }

    /**
     * 
     * <br>
     * 
     * @param controllerId
     * @param devIp
     * @since  GSO 0.5
     */
    private void closeDevNetconfCache(String controllerId, String devIp) {
        this.listener.handleChange(new NetconfAccessInfo(controllerId, devIp));
    }

    /**
     * 
     * <br>
     * 
     * @return
     * @since  GSO 0.5
     */
    private List<String> getControllerIds() {
        synchronized(this.controllerIds) {
            if(this.controllerIds.size() > CORRECT_DEVID_SIZE) {

                LOGGER.warn("devIds={}, devIp={}", this.controllerIds, this.communicateArg.getIp());

            }
            return new ArrayList<String>(this.controllerIds.keySet());
        }
    }

    /**
     * 
     * <br>
     * 
     * @return
     * @since  GSO 0.5
     */
    private String getAppInfo() {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        for(int i = 0; i < stackTraces.length; ++i) {
            if(USER_LEVEL == i) {
                return String.valueOf(stackTraces[i]);
            }
        }
        return null;
    }

}
