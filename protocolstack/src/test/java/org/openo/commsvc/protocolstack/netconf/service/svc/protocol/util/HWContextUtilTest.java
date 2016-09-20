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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.util;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HWContextUtilTest {

    @Test
    public void contextValueTest() {
        Integer lrID = 0, vrID = 0;
        String result;
        result = HWContextUtil.contextValue(lrID, vrID);

        assertTrue(result.equals("ls=0vs=0"));
    }

    @Test
    public void contextVlueTestNull() {
        Integer lrID = 0, vrID = 0;
        String result;
        result = HWContextUtil.contextValue(null, vrID);

        assertTrue(result.equals("vs=0"));
    }

    @Test
    public void contextValueTestNull1() {
        Integer lrID = 0, vrID = 0;
        String result;
        result = HWContextUtil.contextValue(lrID, null);

        assertTrue(result.equals("ls=0"));
    }

    @Test
    public void lrvrValueTest() {
        String context = null;
        Map<String, Integer> map = new HashMap<String, Integer>();

        map = HWContextUtil.lrvrValue(context);
        assertTrue(map.isEmpty());

        context = "huawei";
        map = HWContextUtil.lrvrValue(context);
        assertTrue(map.isEmpty());
        // System.out.println(map);

        context = "ls=0vs=0";
        map = HWContextUtil.lrvrValue(context);
        assertTrue(!map.isEmpty());
    }
}
