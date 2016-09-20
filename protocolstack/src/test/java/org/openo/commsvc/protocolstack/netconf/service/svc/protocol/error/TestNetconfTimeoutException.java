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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfTimeoutException;

public class TestNetconfTimeoutException {

    String operation;

    long timeout;

    long actual;

    Throwable cause;

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
    public final void testNetconfTimeoutExceptionStringLongLong() {
        NetconfTimeoutException netconfTimeoutException = new NetconfTimeoutException(operation, timeout, actual);
    }

    @Test
    public final void testNetconfTimeoutExceptionStringLongLongThrowable() {
        NetconfTimeoutException netconfTimeoutException =
                new NetconfTimeoutException(operation, timeout, actual, cause);
    }

    @Test
    public final void testGetActual() {
        NetconfTimeoutException netconfTimeoutException = new NetconfTimeoutException(operation, timeout, actual);
        netconfTimeoutException.getActual();
    }

    @Test
    public final void testGetTimeout() {
        NetconfTimeoutException netconfTimeoutException = new NetconfTimeoutException(operation, timeout, actual);
        netconfTimeoutException.getTimeout();
    }

}
