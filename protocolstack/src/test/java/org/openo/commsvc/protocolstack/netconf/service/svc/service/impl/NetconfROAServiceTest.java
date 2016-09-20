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

package org.openo.commsvc.protocolstack.netconf.service.svc.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.INetconfMsgBusiness;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfReqMessage;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.RpcArg;

public class NetconfROAServiceTest {

    INetconfMsgBusiness netconfMsgBusiness;

    org.openo.commsvc.protocolstack.netconf.service.svc.service.NetconfROAService netconfROAService =
            new org.openo.commsvc.protocolstack.netconf.service.svc.service.NetconfROAService();

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.service.NetconfROAService#dispatchMessage(java.lang.String, java.lang.String)}.
     * 
     * @throws ServiceException
     */
    @Test
    public void testDispatchMessage_invalid_netconf_msg() throws ServiceException {
        netconfROAService.setNetconfMsgBusiness(netconfMsgBusiness);
        NetconfReqMessage req = new NetconfReqMessage();
        req.setCallbackPostUrl("callbackPostUrl");
        req.setInvokeMode("invokeMode");
        req.setPayload("payload");
        req.setProtocol("protocol");
        List<RpcArg> rpcArgList = new ArrayList<>();
        RpcArg rpcArg = new RpcArg();
        rpcArg.setName("name");
        rpcArg.setValue("value");
        rpcArgList.add(rpcArg);
        req.setRpcArgList(rpcArgList);
        String controllerId = "controllerId";

        try {
            netconfROAService.dispatchMessage("message", controllerId);
            assertFalse(true);
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.service.NetconfROAService#getNetconfMsgBusiness()}.
     */
    @Test
    public void testGetNetconfMsgBusiness() {
        netconfROAService.setNetconfMsgBusiness(netconfMsgBusiness);
        netconfROAService.getNetconfMsgBusiness();
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.netconf.service.svc.service.NetconfROAService#setNetconfMsgBusiness(org.openo.commsvc.protocolstack.netconf.service.svc.business.INetconfMsgBusiness)}.
     */
    @Test
    public void testSetNetconfMsgBusiness() {
    }

}
