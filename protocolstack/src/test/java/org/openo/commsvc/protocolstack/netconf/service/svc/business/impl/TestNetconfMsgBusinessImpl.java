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

package org.openo.commsvc.protocolstack.netconf.service.svc.business.impl;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfReqMessage;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr;

public class TestNetconfMsgBusinessImpl {

    private NetconfMsgBusinessImpl ncMsgImpl = new NetconfMsgBusinessImpl();

    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_dispatchMessage_empty_controllerid() {

        String controllerID = "";
        NetconfReqMessage reqMsg = new NetconfReqMessage();

        reqMsg.setCallbackPostUrl("http://aaa.com/aaa");
        reqMsg.setInvokeMode("invokedmode");
        reqMsg.setPayload("payload");
        reqMsg.setProtocol("protocol");

        try {
            ncMsgImpl.dispatchMessage(controllerID, reqMsg);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void test_dispatchMessage_null_message() {
        String controllerID = "controllerID";
        NetconfReqMessage reqMsg = null;

        try {
            ncMsgImpl.dispatchMessage(controllerID, reqMsg);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void test_dispatchMessage_invalid_protocol() {
        String controllerID = "controllerID";
        NetconfReqMessage reqMsg = new NetconfReqMessage();

        reqMsg.setCallbackPostUrl("http://aaa.com/aaa");
        reqMsg.setInvokeMode("invokedmode");
        reqMsg.setPayload("payload");
        reqMsg.setProtocol("protocol");

        try {
            ncMsgImpl.dispatchMessage(controllerID, reqMsg);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void test_dispatchMessage_empty_message_payload() {
        String controllerID = "controllerID";
        NetconfReqMessage reqMsg = new NetconfReqMessage();

        reqMsg.setCallbackPostUrl("http://aaa.com/aaa");
        reqMsg.setInvokeMode("invokedmode");
        reqMsg.setPayload("");
        reqMsg.setProtocol("netconf");

        try {
            ncMsgImpl.dispatchMessage(controllerID, reqMsg);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void test_dispatchMessage() {
        String controllerID = "controllerID";

        NetconfReqMessage reqMsg = new NetconfReqMessage();
        reqMsg.setCallbackPostUrl("http://aaa.com/aaa");
        reqMsg.setInvokeMode("invokedmode");
        reqMsg.setPayload("payload");
        reqMsg.setProtocol("netconf");

        try {
            ncMsgImpl.dispatchMessage(controllerID, reqMsg);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void test_dispatchMessage_asyncMode() {
        String controllerID = "controllerID";

        NetconfReqMessage reqMsg = new NetconfReqMessage();
        reqMsg.setCallbackPostUrl("http://aaa.com/aaa");
        reqMsg.setInvokeMode("ASYNC");
        reqMsg.setPayload("payload");
        reqMsg.setProtocol("netconf");

        try {
            ncMsgImpl.dispatchMessage(controllerID, reqMsg);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void test_dispatchMessage_syncMode() {
        String controllerID = "controllerID";

        NetconfReqMessage reqMsg = new NetconfReqMessage();
        reqMsg.setCallbackPostUrl("http://aaa.com/aaa");
        reqMsg.setInvokeMode("SYNC");
        reqMsg.setPayload("payload");
        reqMsg.setProtocol("netconf");

        try {
            ncMsgImpl.dispatchMessage(controllerID, reqMsg);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void test_dispatchMessage_empty_callback() {
        String controllerID = "controllerID";

        NetconfReqMessage reqMsg = new NetconfReqMessage();
        reqMsg.setCallbackPostUrl("");
        reqMsg.setInvokeMode("ASYNC");
        reqMsg.setPayload("payload");
        reqMsg.setProtocol("netconf");

        try {
            ncMsgImpl.dispatchMessage(controllerID, reqMsg);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void test_sendNetconfRequest() {
        String controllerID = "controllerID";

        NetconfReqMessage reqMsg = new NetconfReqMessage();
        reqMsg.setCallbackPostUrl("http://aaa.com/aaa");
        reqMsg.setInvokeMode("invokedmode");
        reqMsg.setPayload("payload");
        reqMsg.setProtocol("netconf");

        CommunicateArg commArg = new CommunicateArg("controllerID", "password", 1, "username");
        try {
            CommunicationArgumentMgr.addCommunicateArg(commArg);
        } catch(NetconfException e1) {
        }

        try {
            ncMsgImpl.sendNetconfRequest(controllerID, reqMsg);
        } catch(ServiceException e) {
        }
    }
}
