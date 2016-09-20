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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;

public class ServiceExceptionTest {

    public static final String DEFAULT_ID = "framwork.remote.SystemError";

    Throwable cause = null;

    private static final long serialVersionUID = 5703294364555144738L;

    private String id = DEFAULT_ID;

    private Object[] args = null;

    private int httpCode = 500;

    private ExceptionArgs exceptionArgs = null;

    String message = "";

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
    public final void testServiceException() {
        ServiceException serviceexp = new ServiceException();

    }

    @Test
    public final void testServiceExceptionStringThrowable() {
        ServiceException serviceexp = new ServiceException(id, cause);

    }

    @Test
    public final void testServiceExceptionString() {
        ServiceException serviceexp = new ServiceException(message);
    }

    @Test
    public final void testServiceExceptionStringString() {
        ServiceException serviceexp = new ServiceException(id, message);
    }

    @Test
    public final void testServiceExceptionStringInt() {
        ServiceException serviceexp = new ServiceException(id, httpCode);
    }

    @Test
    public final void testServiceExceptionStringIntExceptionArgs() {
        ServiceException serviceexp = new ServiceException(id, httpCode, exceptionArgs);
    }

    @Test
    public final void testServiceExceptionStringStringObjectArray() {
        ServiceException serviceexp = new ServiceException(id, message, cause, args);
    }

    @Test
    public final void testServiceExceptionStringStringThrowableObjectArray() {
        ServiceException serviceexp = new ServiceException(id, message, cause, args);
    }

    @Test
    public final void testServiceExceptionStringStringThrowable() {
        ServiceException serviceexp = new ServiceException(id, message, cause);
    }

    @Test
    public final void testServiceExceptionThrowable() {
        ServiceException serviceexp = new ServiceException(cause);
    }

    @Test
    public final void testGetId() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.getId();
        serviceexp.setId(null);

        assertEquals(serviceexp.getId(), id);

    }

    @Test
    public final void testGetIdempty() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.getId();

        assertEquals(serviceexp.getId(), id);

    }

    @Test
    public final void testGetIdvalue() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.getId();

        assertEquals(serviceexp.getId(), DEFAULT_ID);

    }

    @Test
    public final void testSetIdnull() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.setId(null);

    }

    @Test
    public final void testSetIdnempty() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.setId("");
    }

    @Test
    public final void testSetIdvalue() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.setId(DEFAULT_ID);
    }

    @Test
    public final void testGetHttpCode() {
        ServiceException serviceexp = new ServiceException();
        assertEquals(serviceexp.getHttpCode(), 500);
    }

    @Test
    public final void testSetHttpCode() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.setHttpCode(540);
    }

    @Test
    public final void testGetExceptionArgs() {
        ServiceException serviceexp = new ServiceException();
        assertEquals(serviceexp.getExceptionArgs(), exceptionArgs);
    }

    @Test
    public final void testSetExceptionArgs() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.setExceptionArgs(exceptionArgs);
    }

    @Test
    public final void testGetArgs() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.setId("123");

    }

    @Test
    public final void testGetArgs1() {
        ServiceException serviceexp = new ServiceException();
        serviceexp.setId("DEFAULT_ID");
    }

    @Test
    public final void testToString() {
        ServiceException serviceexp = new ServiceException();
        String returnValue = serviceexp.toString();
        assertEquals(returnValue,
                "exception.id: framwork.remote.SystemError; org.openo.baseservice.remoteservice.exception.ServiceException: ");
    }

}
