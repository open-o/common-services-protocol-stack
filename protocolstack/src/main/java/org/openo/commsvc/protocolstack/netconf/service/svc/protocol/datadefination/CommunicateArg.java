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

import org.openo.commsvc.protocolstack.common.constant.CommonConstant;

/**
 * CommunicateArg controller communication argument parameters
 * 
 * @author
 */
public class CommunicateArg {

    public static final String ENCODING_UTF8 = "UTF-8";

    protected int maxConnNum = CommonConstant.DEFAULT_MAX_CONNECTION_PER_CONTROLLER;

    /**
     * Globalization requirements, equipment charset field
     */
    private String charset = ENCODING_UTF8;

    /**
     * Minimum timeout time (ms)
     */
    private static final long MIN_TIMEOUT_UNIT = 1000;

    public static final String PASSWORD_AUTH = "1";

    /**
     * Controller Device IP - derived from url
     */
    protected String ip;

    /**
     * Controller unique Id
     */
    String controllerId;

    /**
     * Controller Name
     */
    String name;

    /**
     * Controller url
     */
    String url;

    /**
     * username
     */
    String userName;

    /**
     * password - not encrypted?
     */
    String password;

    /**
     * controller version
     */
    String version;

    /**
     * Controller Vendor
     */
    String vendor;

    /**
     * Controller description
     */

    String description;

    /**
     * Controller protocol
     */

    String protocol;

    /**
     * Controller product Name
     */

    String productName;

    /**
     * Controller Type
     */
    String type;

    /**
     * Controller create time
     */
    String createTime;

    /**
     * Controller port - derived from url
     */
    int port;

    /**
     * Response message time out (default - 30 seconds)
     */
    protected int responseTimeout;

    /**
     * Login time out (default - 30 seconds)
     */
    protected int loginTimeout;

    String authType = PASSWORD_AUTH;

    /**
     * Corresponding argument constructor
     * 
     * @param ip
     * @param username
     * @param password
     * @param port
     * @param type
     * @param responseTimeout
     * @param loginTimeout
     */
    public CommunicateArg(String ip, String username, String password, int port, String type, int responseTimeout,
            int loginTimeout) {
        super();
        this.ip = ip;

        this.userName = username;
        this.password = password;
        this.port = port;
        this.type = type;
        this.responseTimeout = reviseTimeOut(responseTimeout, Netconf.getDefaultResponseTimeout());
        this.loginTimeout = reviseTimeOut(loginTimeout, Netconf.getDefaultLoginTimeout());
    }

    /**
     * Key authentication Constructor
     * 
     * @param ip
     * @param password
     * @param port
     * @param userName
     */
    public CommunicateArg(String ip, String password, int port, String userName) {
        this(ip, userName, password, port, null, 0, 0);
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public int getMaxConnNum() {
        return maxConnNum;
    }

    public void setMaxConnNum(int maxConnNum) {
        this.maxConnNum = maxConnNum;
    }

    public String getIp() {
        return ip;
    }

    private static String remedyPrivateKeyContent(String key) {
        if(key == null) {
            return "";
        }

        String result = key.trim();
        if(!(result.contains("-----BEGIN RSA PRIVATE KEY-----\n")
                || result.contains("-----BEGIN RSA PRIVATE KEY-----\r\n"))) {
            result = result.replaceFirst("-----BEGIN RSA PRIVATE KEY-----", "-----BEGIN RSA PRIVATE KEY-----\n");
        }

        if(!(result.contains("\n-----END RSA PRIVATE KEY-----")
                || result.contains("\r\n-----END RSA PRIVATE KEY-----"))) {
            result = result.replace("-----END RSA PRIVATE KEY-----", "\n-----END RSA PRIVATE KEY-----");
        }

        return result;
    }

    public String getPassword() {
        if(password == null) {
            return "";
        }
        return password;
    }

    public int getPort() {
        return port;
    }

    public String getType() {
        return type;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getResponseTimeout() {
        return responseTimeout;
    }

    public void setResponseTimeout(int responseTimeout) {
        this.responseTimeout = reviseTimeOut(responseTimeout, Netconf.getDefaultResponseTimeout());
    }

    public int getLoginTimeout() {
        return loginTimeout;
    }

    public void setLoginTimeout(int loginTimeout) {
        this.loginTimeout = reviseTimeOut(loginTimeout, Netconf.getDefaultLoginTimeout());
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUserName() {
        if(userName == null) {
            return "";
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return Returns the charset.
     */
    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    private static int reviseTimeOut(final int timeout, final int defaultTimeout) {
        int ret = timeout;
        if(ret <= 0) {
            ret = defaultTimeout;
        }

        if(ret < MIN_TIMEOUT_UNIT) {
            ret = timeout * 1000;
        }

        return ret;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(CommonConstant.DEFAULT_STRING_LENGTH_64);
        builder.append("CommunicateArg{hostName=").append(this.ip);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ip == null) ? 0 : ip.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof CommunicateArg)) {
            return false;
        }
        CommunicateArg other = (CommunicateArg)obj;
        if(ip == null) {
            if(other.ip != null) {
                return false;
            }
        } else if(!ip.equals(other.ip)) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}
