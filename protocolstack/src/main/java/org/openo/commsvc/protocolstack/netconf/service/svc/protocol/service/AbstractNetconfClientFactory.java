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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Netconf client factory<br>
 * <p>
 * </p>
 * 
 * @param <T> - Netconf access information
 * @author
 * @version GSO 0.5 Sep 7, 2016
 */
public abstract class AbstractNetconfClientFactory<T extends NetconfAccessInfo> implements INetconfClientFactory<T> {

    /**
     * Log object
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNetconfClientFactory.class);

    /**
     * Implementation <br>
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.INetconfClientFactory#close(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.INetconfClient)
     * @author
     */
    @Override
    public void close(INetconfClient netconfClient) {
        if(null == netconfClient) {
            return;
        }
        netconfClient.close();
    }

    /**
     * @return Returns the LOGGER.
     */
    protected Logger getLogger() {
        return this.LOGGER;
    }

}
