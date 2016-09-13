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

package org.openo.commsvc.protocolstack.netconf.service.svc.model;

import java.util.List;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.RpcArg;

/**
 * Model to represent the netconf request message.
 * <br>
 * 
 * @author
 * @version     GSO 0.5  Sep 7, 2016
 */
public class NetconfReqMessage {

    /**
     * Protocol type: netconf
     */
    private String protocol;

    /**
     * RPC Parameter List
     */
    private List<RpcArg> rpcArgList;

    /**
     * Forwarding methods: synchronous (sync), asynchronous (async)
     */
    private String invokeMode;

    /**
     * Callback URL
     */
    private String callbackPostUrl;

    /**
     * Request message body
     */
    private String payload;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getInvokeMode() {
        return invokeMode;
    }

    public void setInvokeMode(String invokeMode) {
        this.invokeMode = invokeMode;
    }

    public String getCallbackPostUrl() {
        return callbackPostUrl;
    }

    public void setCallbackPostUrl(String callbackPostUrl) {
        this.callbackPostUrl = callbackPostUrl;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public List<RpcArg> getRpcArgList() {
        return rpcArgList;
    }

    public void setRpcArgList(List<RpcArg> rpcArgList) {
        this.rpcArgList = rpcArgList;
    }
}
