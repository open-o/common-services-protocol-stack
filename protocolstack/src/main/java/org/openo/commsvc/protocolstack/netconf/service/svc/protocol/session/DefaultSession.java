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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.ITransport;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.ITransportFactory;

/**
 * NETCONF Session default implementation。 <br>
 * 
 * @author
 */
public final class DefaultSession extends AbstractSession {

    /**
     * Transport Layer factory class
     */
    @SuppressWarnings("rawtypes")
    private ITransportFactory transportFactory;

    /**
     * Constructor<br>
     * <br>
     * 
     * @author
     * @param id of the current Session ID
     * @param netconfAccessInfo Netconf access parameters
     * @param transportFactory Transport Layer factory class
     * @param createThreadName Creating the current thread 's name Netconf session
     */
    @SuppressWarnings("rawtypes")
    public DefaultSession(int id, NetconfAccessInfo netconfAccessInfo, ITransportFactory transportFactory,
            String createThreadName) {
        super(id, netconfAccessInfo, createThreadName);
        this.transportFactory = transportFactory;
    }

    /**
     * Implementation
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.session.AbstractSession#createTransport(org.openo.commsvc.connector.netconf.service.svc.protocol.service.NetconfAccessInfo)
     * @author
     * @throws NetconfException
     */
    @SuppressWarnings("unchecked")
    @Override
    protected ITransport createTransport(NetconfAccessInfo netconfAccessInfo) throws NetconfException {
        return this.transportFactory.createTransport(netconfAccessInfo);
    }
}
