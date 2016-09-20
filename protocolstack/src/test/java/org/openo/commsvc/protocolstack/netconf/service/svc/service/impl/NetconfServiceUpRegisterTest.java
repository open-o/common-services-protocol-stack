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

package org.openo.commsvc.protocolstack.netconf.service.svc.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.service.NetconfServiceUpRegister;


public class NetconfServiceUpRegisterTest {
    NetconfServiceUpRegister netconfServiceUpRegister = new NetconfServiceUpRegister();
    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.service.NetconfServiceUpRegister#start()}.
     */
    @Test
    public void testStart() {
        netconfServiceUpRegister.start();
        assert(true);
    }

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.service.NetconfServiceUpRegister#stop()}.
     */
    @Test
    public void testStop() {
        netconfServiceUpRegister.stop();
        assert(true);
    }

}
