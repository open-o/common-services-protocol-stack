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

package org.openo.commsvc.protocolstack.common.decomposer;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultTest {

    String retCode ="success";

    int statusCode;

    String reason = "reason";

    Object data = "data";

    Result result = new Result();

    Result result1 = new Result<>(retCode);

    Result result2 = new Result<>(retCode, reason);

    Result result3 = new Result<>(retCode, reason, data);

    @Test
    public void testGetRetCode() {
        result.setRetCode(retCode);
        result.getRetCode();
        assertTrue(result.getRetCode()!=null);

    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.decomposer.Result#getReason()}.
     */
    @Test
    public void testGetReason() {
        result.setReason(reason);
        result.getReason();
        assertTrue(result.getReason()!=null);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.decomposer.Result#getData()}.
     */
    @Test
    public void testGetData() {
        result.setData(data);
        result.getData();
        assertTrue(result.getData()!=null);
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.common.decomposer.Result#getStatusCode()}.
     */
    @Test
    public void testGetStatusCode() {
        result.setStatusCode(1);
        result.getStatusCode();
        assertTrue(result.getStatusCode()!=0);
    }
}
