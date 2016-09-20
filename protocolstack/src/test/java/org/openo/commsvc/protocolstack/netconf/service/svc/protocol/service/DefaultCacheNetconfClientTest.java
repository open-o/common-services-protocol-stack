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

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.TargetType;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import mockit.Mocked;

public class DefaultCacheNetconfClientTest {
    private String createThreadName = "createThreadName";
    private String appName = "appName";
    private INetconfAccessInfoLookup netconfAccessInfoLookup;
    private ICacheSessionPool cacheSessionPool;
    CommunicateArg commArg = new CommunicateArg("1.1.1.1", "password", 102, "username");
    private NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo(commArg);
    DefaultCacheNetconfClient defaultCacheNetconfClient = new DefaultCacheNetconfClient(netconfAccessInfo, cacheSessionPool, netconfAccessInfoLookup, appName, createThreadName);
    DefaultCacheNetconfClientFactory defaultCacheNetconfClientFactory = new DefaultCacheNetconfClientFactory();

    /**
     * Test method for {@link org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.DefaultCacheNetconfClient#lookupNetconfAccessInfo(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo)}.
     * @throws NetconfException 
     */
    @Test
    public void testLookupNetconfAccessInfo() throws NetconfException {
        netconfAccessInfo.setCommArg(commArg);
        String controllerId = "controllerid";
        netconfAccessInfo.setControllerId(controllerId );
        defaultCacheNetconfClient.lookupNetconfAccessInfo(netconfAccessInfo);
        assertTrue(true);
    }
}
