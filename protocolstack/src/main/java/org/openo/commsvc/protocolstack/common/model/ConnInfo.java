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

package org.openo.commsvc.protocolstack.common.model;

import java.util.Map;

/**
 * Controller connection informations
 * 
 * @author
 */
public class ConnInfo {

    private String controllerId;

    private String hostName;

    private String protocol;

    private int maxConnectionsPerClient;

    private int port;

    private Map<String, String> commParamMap;

    /**
     * @param commParamMap
     */
    public ConnInfo(Map<String, String> commParamMap) {
        this.commParamMap = commParamMap;
    }

    /**
     * @param key
     * @return
     */
    public String getCommParam(String key) {
        return commParamMap.get(key);
    }

    public String getControllerId() {
        return controllerId;
    }

    /**
     * @param controllerId
     * @return
     */
    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName
     * @return
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol
     * @return
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getMaxConnectionsPerClient() {
        return maxConnectionsPerClient;
    }

    /**
     * @param maxConnectionsPerClient
     * @return
     */
    public void setMaxConnectionsPerClient(int maxConnectionsPerClient) {
        this.maxConnectionsPerClient = maxConnectionsPerClient;
    }

    public int getPort() {
        return port;
    }

    /**
     * @param port
     * @return
     */
    public void setPort(int port) {
        this.port = port;
    }

}
