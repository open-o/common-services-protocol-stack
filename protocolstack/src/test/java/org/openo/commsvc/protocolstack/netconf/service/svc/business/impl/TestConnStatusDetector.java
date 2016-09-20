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

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.impl.ConnStatusDetector;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr;

public class TestConnStatusDetector {

    ConnStatusDetector connStatusDetector = new ConnStatusDetector();

    private ConnStatusDetector detector;

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
    public final void testDetectConnStatus() {
        connStatusDetector.detectConnStatus();

        String ip;
        CommunicateArg[] commArgs = CommunicationArgumentMgr.getAllCommunicateArg();
        assertNotNull(commArgs);
    }

    @Test
    public final void testDetectConnStatusTraversal() {
        connStatusDetector.detectConnStatus();

    }

    @Test
    public final void testStopDetecting() {
        connStatusDetector.detectConnStatus();
        connStatusDetector.stopDetecting();

    }

    @Test
    public final void testSetCacheSessionPool() {
        connStatusDetector.setCacheSessionPool(null);
    }

}
