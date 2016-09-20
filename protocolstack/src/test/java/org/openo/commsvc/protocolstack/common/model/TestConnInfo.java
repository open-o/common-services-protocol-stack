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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.commsvc.protocolstack.common.model.ConnInfo;

public class TestConnInfo {

    ConnInfo connInfo = new ConnInfo(null);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetCommParam() {
        final ConnInfo connInfo = new ConnInfo(null);
        final String commparam = "key";
    }

    @Test
    public final void testGetControllerId() {
        final ConnInfo connInfo = new ConnInfo(null);
        assertNull(connInfo.getControllerId());
        final String controllerId = "111";
        connInfo.setControllerId(controllerId);
        assertEquals(controllerId, connInfo.getControllerId());

    }

    @Test
    public final void testSetControllerId() {
        final ConnInfo connInfo = new ConnInfo(null);
        final String controllerId = "111";
        connInfo.setControllerId(controllerId);
        assertEquals(controllerId, connInfo.getControllerId());
    }

    @Test
    public final void testGetHostName() {
        final ConnInfo connInfo = new ConnInfo(null);
        assertNull(connInfo.getHostName());
        final String hostname = "10.201.11.48";
        connInfo.setHostName(hostname);
        assertEquals(hostname, connInfo.getHostName());
    }

    @Test
    public final void testSetHostName() {
        final ConnInfo connInfo = new ConnInfo(null);
        final String hostname = "10.201.11.48";
        connInfo.setHostName(hostname);
        assertEquals(hostname, connInfo.getHostName());
    }

    @Test
    public final void testGetProtocol() {
        final ConnInfo connInfo = new ConnInfo(null);
        assertNull(connInfo.getProtocol());
        final String protocol = "netconf";
        connInfo.setProtocol(protocol);
        assertEquals(protocol, connInfo.getProtocol());
    }

    @Test
    public final void testSetProtocol() {
        final ConnInfo connInfo = new ConnInfo(null);
        final String protocol = "netconf";
        connInfo.setProtocol(protocol);
        assertEquals(protocol, connInfo.getProtocol());
    }

    @Test
    public final void testGetMaxConnectionsPerClient() {
        final ConnInfo connInfo = new ConnInfo(null);
        assertNotNull(connInfo.getMaxConnectionsPerClient());
        @SuppressWarnings("unused")
        final int maxconnectionsperclient = 5;
        connInfo.setMaxConnectionsPerClient(maxconnectionsperclient);
        ;
        assertEquals(maxconnectionsperclient, connInfo.getMaxConnectionsPerClient());
    }

    @Test
    public final void testSetMaxConnectionsPerClient() {
        final ConnInfo connInfo = new ConnInfo(null);
        final int maxconnectionsperclient = 5;
        connInfo.setMaxConnectionsPerClient(maxconnectionsperclient);
        ;
        assertEquals(maxconnectionsperclient, connInfo.getMaxConnectionsPerClient());
    }

    @Test
    public final void testGetPort() {
        final ConnInfo connInfo = new ConnInfo(null);
        assertNotNull(connInfo.getPort());
        @SuppressWarnings("unused")
        final int port = 17830;
        connInfo.setPort(port);
        assertEquals(port, connInfo.getPort());
    }

    @Test
    public final void testSetPort() {
        final ConnInfo connInfo = new ConnInfo(null);
        final int port = 17830;
        connInfo.setPort(port);
        assertEquals(port, connInfo.getPort());
    }

}
