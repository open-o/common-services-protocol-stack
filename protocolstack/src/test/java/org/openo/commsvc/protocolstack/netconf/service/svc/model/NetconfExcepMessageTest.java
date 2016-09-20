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

package org.openo.commsvc.protocolstack.netconf.service.svc.model;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class NetconfExcepMessageTest {

    @Test
    public void NetconfExcepMessageTestNull()
    {
        ServiceException e;
        
        NetconfExcepMessage obj = new NetconfExcepMessage(null);
    }
    
    @Test
    public void NetconfExcepMessageTestNullArgs()
    {
        ServiceException e = new ServiceException();
        
        NetconfExcepMessage obj = new NetconfExcepMessage(e);
    }
    
    @Test
    public void NetconfExcepMessageTest()
    {
        ServiceException e = new ServiceException();
        
        new MockUp<ServiceException>() {

            @Mock
            public ExceptionArgs getExceptionArgs() {
                ExceptionArgs args = new ExceptionArgs();
                return args;
            }
        };
        
        NetconfExcepMessage obj = new NetconfExcepMessage(e);
    }
}
