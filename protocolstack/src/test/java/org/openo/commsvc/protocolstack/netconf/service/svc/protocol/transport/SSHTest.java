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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.DefaultSessionFactory;

import mockit.Mocked;

public class SSHTest {

    @Mocked
    private ITransportHandler transportHandler;

    @Mocked
    private ITransportListener transportListener;

    @Test
    public void testIsConnected() {
        SSH ssh = new SSH();
        assertFalse(ssh.isConnected());
    }

    @Test
    public void testClose() {
        // connect the ssh session and try to close the session.

        SSH ssh = new SSH();
        try {
            CommunicateArg arg = new CommunicateArg("1.1.1.1", "username", "password", 17830, "1", 1, 1);
            NetconfAccessInfo serverInfo = new NetconfAccessInfo(arg);
            ssh.connect(serverInfo,
                    (ITransportHandler)new DefaultSessionFactory().createSession(serverInfo, "theadname"));
        } catch(NetconfException e) {
            ssh.close();
            assertTrue(true);
        }
    }

    @Test(expected = Exception.class)
    public <T> void testConnect() throws NetconfException {

        ITransportHandler transportHandler = new ITransportHandler() {

            @Override
            public void handlePacket(String devReplyStr) {
            }

            @Override
            public void handleError(String devReplyErr) {
            }

            @Override
            public String getPacketEndTag() {
                return null;
            }

            @Override
            public String getCharsetName() {
                return null;
            }
        };

        CommunicateArg ca = new CommunicateArg("1.1.1.1", "username", "password", 17830, "1", 1, 1);
        ca.setAuthType(CommunicateArg.PASSWORD_AUTH);
        NetconfAccessInfo info = new NetconfAccessInfo(ca);

        SSH ssh = new SSH();

        ssh.connect(info, transportHandler);
    }

    @Test(expected = IllegalArgumentException.class)
    public <T> void testConnect2() throws NetconfException {

        ITransportHandler transportHandler = new ITransportHandler() {

            @Override
            public void handlePacket(String devReplyStr) {
            }

            @Override
            public void handleError(String devReplyErr) {
            }

            @Override
            public String getPacketEndTag() {
                return null;
            }

            @Override
            public String getCharsetName() {
                return null;
            }
        };

        NetconfAccessInfo info = new NetconfAccessInfo(null);

        SSH ssh = new SSH();

        ssh.connect(info, transportHandler);
    }

    @Test(expected = IllegalArgumentException.class)
    public <T> void testConnect1() throws NetconfException {

        ITransportHandler transportHandler = new ITransportHandler() {

            @Override
            public void handlePacket(String devReplyStr) {
            }

            @Override
            public void handleError(String devReplyErr) {
            }

            @Override
            public String getPacketEndTag() {
                return null;
            }

            @Override
            public String getCharsetName() {
                return null;
            }
        };

        NetconfAccessInfo info = new NetconfAccessInfo(null);

        SSH ssh = new SSH();

        ssh.connect(info, null);
    }

    @Test
    public void testSend() {
        SSH ssh = new SSH();
        try {
            // connect before sending the message
            CommunicateArg arg = new CommunicateArg("1.1.1.1", "username", "password", 17830, "1", 1, 1);
            NetconfAccessInfo serverInfo = new NetconfAccessInfo(arg);
            ssh.connect(serverInfo,
                    (ITransportHandler)new DefaultSessionFactory().createSession(serverInfo, "theadname"));
            ssh.send("req");
        } catch(NetconfException e) {
            if(!ssh.isConnected()) {
                assertTrue(true);
            }
        }
    }

    @Test
    public void testSend1() throws NetconfException {
        SSH ssh = new SSH();
        ssh.send(null);
    }

    @Test
    public void testGetBytes_empty() {
        SSH ssh = new SSH();
        byte[] res = ssh.getBytes(null);

        assertEquals(0, res.length);
    }

    @Test
    public void testGetBytes_UTF8() {
        SSH ssh = new SSH();
        byte[] res = ssh.getBytes("123");

        assertEquals(3, res.length);
    }

    @Test
    public void testAppendSuffix() {
        assertEquals(null, SSH.appendSuffix(null));
    }

    @Test
    public void testAppendSuffix_1() {
        assertEquals("123]]>]]>", SSH.appendSuffix("123]]>]]>"));
    }

    @Test
    public void testAppendSuffix_2() {
        assertEquals("123]]>]]>", SSH.appendSuffix("123"));
    }

    @Test
    public void testAddConnectListener() {
        SSH ssh = new SSH();
        ssh.addConnectListener(transportListener);
    }

    @Test
    public void testAddConnectListener1() {
        transportListener = new ITransportListener() {

            @Override
            public void handleTransportClosed(ITransport transport, NetconfException e) {
            }
        };
        SSH ssh = new SSH();
        ssh.addConnectListener(transportListener);
    }

}
