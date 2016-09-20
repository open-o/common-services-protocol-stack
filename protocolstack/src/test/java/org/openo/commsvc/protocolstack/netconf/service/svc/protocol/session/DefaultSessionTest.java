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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpcReply;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;

public class DefaultSessionTest {

    @Test
    public void testAddSessionListener() {
        DefaultSession session = new DefaultSession(100, null, null, "test");
        session.addSessionListener(null);
        assertTrue(true);
    }

    @Test
    public void testAddSessionListener2() {
        DefaultSession session = new DefaultSession(100, null, null, "test");
        session.addSessionListener(new CacheSessionPool());
        assertTrue(true);
    }

    @Test
    public void testClose() {
        DefaultSession session = new DefaultSession(100, null, null, "test");
        boolean res = session.close();
        assertFalse(res);
    }

    @Test
    public void testEquals() {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        DefaultSession session2 = new DefaultSession(100, null, null, "test");
        boolean res = session1.equals(session2);
        assertTrue(res);
    }

    @Test
    public void testEqualsFalse() {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        DefaultSession session2 = new DefaultSession(101, null, null, "test");
        boolean res = session1.equals(session2);
        assertFalse(res);
    }

    @Test
    public void testEqualsFalse2() {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        boolean res = session1.equals(session1);
        assertTrue(res);
    }

    @Test
    public void testEqualsFalse3() {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        boolean res = session1.equals(null);
        assertFalse(res);
    }

    @Test
    public void testEqualsFalse4() {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        boolean res = session1.equals(new Object());
        assertFalse(res);
    }

    @Test(expected = Exception.class)
    public void testHandlePacket() {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        session1.handlePacket("test");
    }

    @Test(expected = Exception.class)
    public void testHandlePacket2() {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        session1.handlePacket("");
    }

    @Test(expected = Exception.class)
    public void testHandlePacket3() {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        session1.handlePacket(null);
    }

    @Test
    public void testIsConnected() {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        boolean res = session1.isConnected();
        assertFalse(res);
    }

    @Test(expected = Exception.class)
    public void testSynSend() throws NetconfException {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        IRpcReply res = session1.synSend(new ClientHello(100));
    }

    @Test
    public void testHandleDeviceResponse() throws Exception {
        DefaultSession session1 = new DefaultSession(100, null, null, "test");
        String text = "<test>This is some XML</test>";
        Reader reader = new StringReader(text);
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        XMLStreamReader streamreader = xmlif.createXMLStreamReader(reader);
        session1.handleDeviceResponse(streamreader);
        assertTrue(true);
    }

    @Test
    public void testafterPropertiesSet() throws Exception {
        DefaultSessionFactory session = new DefaultSessionFactory();
        session.afterPropertiesSet();
        assertTrue(true);
    }

    @Test
    public void test_handleTransportClosed() {
        NetconfAccessInfo accessinfo = new NetconfAccessInfo("1.1.1.1", "1.1.1.1");
        ISession isession = null;
        try {
            isession = new DefaultSessionFactory().createSession(accessinfo, "threadname");
            DefaultSession dSession = (DefaultSession)isession;
            dSession.handleTransportClosed(dSession.getTransport(), null);
        } catch(NetconfException e1) {
            assertTrue(true);
        }
    }
}
