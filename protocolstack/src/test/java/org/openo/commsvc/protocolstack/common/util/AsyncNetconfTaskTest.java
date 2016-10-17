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

package org.openo.commsvc.protocolstack.common.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.impl.NetconfMsgBusinessImpl;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfReqMessage;


public class AsyncNetconfTaskTest {
    private String controllerId = "controllerId";
    private NetconfReqMessage message = new NetconfReqMessage();
    private NetconfMsgBusinessImpl netconfMsgBusiness = new NetconfMsgBusinessImpl();
    AsyncNetconfTask asyncNetconfTask = new AsyncNetconfTask(controllerId, message, netconfMsgBusiness);
    
    /**
     * Test method for {@link org.openo.commsvc.protocolstack.common.util.AsyncNetconf#run()}.
     *  
     */

    @Test
    public void testRun() throws ServiceException {
        asyncNetconfTask.run();
    }

}
