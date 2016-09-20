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

package org.openo.commsvc.protocolstack.netconf.service.svc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.INetconfMsgBusiness;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.impl.NetconfMsgBusinessImpl;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfReqMessage;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfResponse;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.RpcArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.AbstractNetconfClient;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.AbstractNetconfClientFactory;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.DefaultCacheNetconfClient;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ICacheSessionPool;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.INetconfAccessInfoLookup;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.INetconfClient;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.INetconfClientFactory;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISessionPool;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.service.NetconfROAService;

public class NetconfROAServiceTest {

    NetconfROAService service = new NetconfROAService();

    INetconfMsgBusiness netconfMsgBusiness;

    NetconfMsgBusinessImpl netconfMsgBusinessimpl = new NetconfMsgBusinessImpl();

    INetconfClientFactory<NetconfAccessInfo> netconfClientFactory;

    AbstractNetconfClientFactory<NetconfAccessInfo> abstractNetconfClientFactory;

    private INetconfClient netConfClient;

    private INetconfAccessInfoLookup INetconfAccessInfoLookup;

    private DefaultCacheNetconfClient defaultCacheNetconfClient;

    private AbstractNetconfClient abstractNetconfClient;

    private ISessionPool<NetconfAccessInfo> iSessionPool;

    ICacheSessionPool<NetconfAccessInfo> iCacheSessionPool;

    INetconfAccessInfoLookup iNetconfAccessInfoLookup;

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testDispatchMessageASYNC() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setInvokeMode("ASYNC");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);
        message.setPayload("<get><filter type=\"subtree\"> <system xmlns=\"http://www.huawei.com/netconf/vrp\" format-version=\"1.0\" "
                + "content- version=\"1.0\"><systemInfo></systemInfo></system></filter> </get>");
        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("resp is not null");
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessageASYNCcontrollerIdNull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setInvokeMode("ASYNC");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = null;
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);
        message.setPayload("<get><filter type=\"subtree\"> <system xmlns=\"http://www.huawei.com/netconf/vrp\" format-version=\"1.0\" "
                + "content- version=\"1.0\"><systemInfo></systemInfo></system></filter> </get>");
        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("resp is not null");
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessagecontrolleridnull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setInvokeMode("ASYNC");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = null;
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);
        message.setPayload("<get><filter type=\"subtree\"> <system xmlns=\"http://www.huawei.com/netconf/vrp\" format-version=\"1.0\" "
                + "content- version=\"1.0\"><systemInfo></systemInfo></system></filter> </get>");
        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNull(controllerId);
        } catch(ServiceException e) {

            e.printStackTrace();
        }

    }

    @Test
    public final void testDispatchMessagecontrolleridnotnull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setInvokeMode("ASYNC");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);
        message.setPayload("<get><filter type=\"subtree\"> <system xmlns=\"http://www.huawei.com/netconf/vrp\" format-version=\"1.0\" "
                + "content- version=\"1.0\"><systemInfo></systemInfo></system></filter> </get>");

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNotNull(controllerId);
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessageinvokemodenotnull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setInvokeMode("ASYNC");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);
        message.setPayload("<get><filter type=\"subtree\"> <system xmlns=\"http://www.huawei.com/netconf/vrp\" format-version=\"1.0\" "
                + "content- version=\"1.0\"><systemInfo></systemInfo></system></filter> </get>");

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNotNull(message.getInvokeMode());
            assertEquals(message.getInvokeMode(), "ASYNC");
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessageinvokemodenull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        // message.setInvokeMode("ASYNC");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);
        message.setPayload("<get><filter type=\"subtree\"> <system xmlns=\"http://www.huawei.com/netconf/vrp\" format-version=\"1.0\" "
                + "content- version=\"1.0\"><systemInfo></systemInfo></system></filter> </get>");

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNull(message.getInvokeMode());
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessagepayloadnotnull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setInvokeMode("ASYNC");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);
        message.setPayload("<get><filter type=\"subtree\"> <system xmlns=\"http://www.huawei.com/netconf/vrp\" format-version=\"1.0\" "
                + "content- version=\"1.0\"><systemInfo></systemInfo></system></filter> </get>");

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNotNull(message.getPayload());
            assertEquals(message.getPayload(),
                    "<get><filter type=\"subtree\"> <system xmlns=\"http://www.huawei.com/netconf/vrp\" format-version=\"1.0\" "
                            + "content- version=\"1.0\"><systemInfo></systemInfo></system></filter> </get>");
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessagepaylaodmodenull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNull(message.getPayload());
        } catch(ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessageprotocolnull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setInvokeMode("ASYNC");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNotNull(message.getProtocol());
            assertEquals(message.getProtocol(), "NETCONF");
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessageprotocolnotnull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNotNull(message.getProtocol());
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessagenull() {
        NetconfReqMessage message = new NetconfReqMessage();
        String controllerId = "123egh";
        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNull(message);
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessagenotnull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNotNull(message);
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessagecallbackurlnull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setInvokeMode("ASYNC");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNull(message.getCallbackPostUrl());
        } catch(ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testDispatchMessagecallbackurlnotnull() {
        NetconfReqMessage message = new NetconfReqMessage();
        message.setCallbackPostUrl("http://localhost:8080/connectorservice/rest/callback");
        message.setInvokeMode("ASYNC");
        message.setProtocol("NETCONF");
        RpcArg arg = new RpcArg();
        String controllerId = "123egh";
        arg.setName("controllerId");
        arg.setValue(controllerId);
        List<RpcArg> args = new ArrayList<>();
        message.setRpcArgList(args);

        try {
            System.out.println("before");
            NetconfResponse resp = netconfMsgBusinessimpl.dispatchMessage(controllerId, message);
            System.out.println("controlelrId is  null");
            assertNotNull(message.getCallbackPostUrl());
            assertEquals("http://localhost:8080/connectorservice/rest/callback", message.getCallbackPostUrl());
        } catch(ServiceException e) {

            e.printStackTrace();
        }
    }

    @Test
    public final void testGetNetconfMsgBusiness() {
        NetconfROAServiceTest serviceroa = new NetconfROAServiceTest();
    }

    @Test
    public final void testSetNetconfMsgBusiness() {

    }
}
