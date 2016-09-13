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

package org.openo.commsvc.protocolstack.common.util;

import java.util.HashMap;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.common.business.ConnStatusMgr;
import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.impl.NetconfMsgBusinessImpl;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfExcepMessage;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfReqMessage;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AsyncNetconfTask class
 * <br>
 * 
 * @author
 * @version     GSO 0.5  Sep 7, 2016
 */
public class AsyncNetconfTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncNetconfTask.class);

    private String controllerId;

    private NetconfReqMessage message;

    private NetconfMsgBusinessImpl netconfMsgBusiness;

    /**
     * 
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @param controllerId
     * @param message
     * @param netconfMsgBusiness
     * @since  GSO 0.5
     */
    public AsyncNetconfTask(String controllerId, NetconfReqMessage message, NetconfMsgBusinessImpl netconfMsgBusiness) {
        this.controllerId = controllerId;
        this.message = message;
        this.netconfMsgBusiness = netconfMsgBusiness;
    }

    /**
     * 
     * <br/>
     * 
     * @since   GSO 0.5
     */
    @Override
    public void run() {
        if(!addConnNum()) {
            return;
        }

        try {
            sendNetconfRequest();
        } finally {
            ConnStatusMgr.getInstance().subConnNum(controllerId);
        }
    }

    private boolean addConnNum() {
        try {
            ConnStatusMgr.getInstance().addConnNum(controllerId);
            return true;
        } catch(ServiceException e) {

            NetconfExcepMessage msg = new NetconfExcepMessage(e);
            String postParam = JsonUtil.toJson(msg);
            callback(postParam);
            return false;
        }
    }

    private void sendNetconfRequest() {
        String postParam = null;
        try {
            NetconfResponse replay = netconfMsgBusiness.sendNetconfRequest(controllerId, message);
            postParam = JsonUtil.toJson(replay);
        } catch(ServiceException e) {

            NetconfExcepMessage msg = new NetconfExcepMessage(e);
            postParam = JsonUtil.toJson(msg);
        }

        callback(postParam);
    }

    private void callback(String postParam) {

        Map<String, String> paramsMap = new HashMap<String, String>();

        // Step 1: Prepare url and method type
        paramsMap.put(CommonConstant.HttpContext.URL, message.getCallbackPostUrl());
        paramsMap.put(CommonConstant.HttpContext.METHOD_TYPE, CommonConstant.MethodType.POST);

        RestfulUtil.getRemoteResponse(paramsMap, postParam, null);

        LOGGER.warn("secceed in executing the async netconf request and calling the callback URL!");
    }

}
