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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;

public class ExceptionArgsTest {

    ExceptionArgs expargs = new ExceptionArgs();

    private String[] descArgs = null;

    /**
     * Exception reasons.
     */
    private String[] reasonArgs = null;

    /**
     * Exception details.
     */
    private String[] detailArgs = null;

    /**
     * Exception advices.
     */
    private String[] adviceArgs = null;

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
    public final void testExceptionArgs() {
        ExceptionArgs expargs = new ExceptionArgs();
    }

    @Test
    public final void testExceptionArgsStringArrayStringArrayStringArrayStringArray() {
        ExceptionArgs expargs = new ExceptionArgs();

        expargs.setAdviceArgs(this.adviceArgs);
        expargs.setDescArgs(this.descArgs);
        expargs.setDetailArgs(this.detailArgs);
        expargs.setReasonArgs(this.reasonArgs);

        assertNull(expargs.getAdviceArgs());
        assertNull(expargs.getDescArgs());
        assertNull(expargs.getDetailArgs());
        assertNull(expargs.getReasonArgs());

        assertEquals(expargs.getAdviceArgs(), adviceArgs);
        assertEquals(expargs.getDescArgs(), descArgs);
        assertEquals(expargs.getDetailArgs(), detailArgs);
        assertEquals(expargs.getReasonArgs(), reasonArgs);
    }

}
