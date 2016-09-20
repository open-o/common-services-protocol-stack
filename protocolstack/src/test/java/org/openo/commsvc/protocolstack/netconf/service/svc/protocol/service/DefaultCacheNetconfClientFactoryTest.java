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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;

public class DefaultCacheNetconfClientFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void testSetCacheSessionPool() {
        DefaultCacheNetconfClientFactory defaultCacheNetconfClientFactory = new DefaultCacheNetconfClientFactory();
        defaultCacheNetconfClientFactory.getNetconfClient(null, null);
    }

    @Test
    public void testSetCacheSessionPoolBrach() {
        DefaultCacheNetconfClientFactory defaultCacheNetconfClientFactory = new DefaultCacheNetconfClientFactory();
        defaultCacheNetconfClientFactory.getNetconfClient(new NetconfAccessInfo("12", "12"), "appname");
    }

    @Test(expected = Exception.class)
    public void testTestConnective() {
        DefaultCacheNetconfClientFactory defaultCacheNetconfClientFactory = new DefaultCacheNetconfClientFactory();
        defaultCacheNetconfClientFactory.setNetconfAccessInfoLookup(null);
        NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo("12", "12");

        CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 12345, "username");
        CommunicateArgWrap obj = new CommunicateArgWrap(commArg, null);
        netconfAccessInfo.setCommArg(obj);
        defaultCacheNetconfClientFactory.testConnective(netconfAccessInfo);
    }

    @Test(expected = Exception.class)
    public void testTestConnectiveBranch() {
        DefaultCacheNetconfClientFactory defaultCacheNetconfClientFactory = new DefaultCacheNetconfClientFactory();
        defaultCacheNetconfClientFactory.setNetconfAccessInfoLookup(null);
        NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo("12", "12");

        CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 12345, "username");
        CommunicateArgWrap obj = new CommunicateArgWrap(commArg, null);
        netconfAccessInfo.setCommArg(commArg);
        defaultCacheNetconfClientFactory.testConnective(netconfAccessInfo);
    }

    public void testTestConnectiveBranch1() {
        DefaultCacheNetconfClientFactory defaultCacheNetconfClientFactory = new DefaultCacheNetconfClientFactory();
        defaultCacheNetconfClientFactory.setNetconfAccessInfoLookup(null);
        NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo("12", "12");

        CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 12345, "username");
        CommunicateArgWrap obj = new CommunicateArgWrap(commArg, null);
        netconfAccessInfo.setCommArg(null);
        defaultCacheNetconfClientFactory.testConnective(netconfAccessInfo);
    }
}
