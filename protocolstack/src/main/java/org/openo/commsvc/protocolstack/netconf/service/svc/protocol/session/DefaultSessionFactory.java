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

import java.util.concurrent.atomic.AtomicInteger;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISessionFatory;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.ITransportFactory;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.transport.SSHTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Netconf session factory<br>
 * 
 * @author
 */
public final class DefaultSessionFactory implements ISessionFatory<NetconfAccessInfo>, InitializingBean {

    /**
     * logger object
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSessionFactory.class);

    /**
     * Netconf sessionId
     */
    private static AtomicInteger sessionIdCreator = new AtomicInteger(0);

    /**
     * transportFactory
     */
    @SuppressWarnings("rawtypes")
    private ITransportFactory transportFactory;

    /**
     * afterPropertiesSet<br>
     * <br>
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     * @author
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if(null != this.transportFactory) {
            this.LOGGER.info("use extend transportFactory={}", this.transportFactory.getClass().getName());
        } else {
            this.transportFactory = new SSHTransportFactory();
        }
    }

    /**
     * create default Session<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.ISessionFatory#createSession(org.openo.commsvc.connector.netconf.service.svc.protocol.service.NetconfAccessInfo,
     *      java.lang.String)
     * @author
     */
    @Override
    public ISession createSession(NetconfAccessInfo netconfAccessInfo, String createThreadName)
            throws NetconfException {
        return new DefaultSession(sessionIdCreator.getAndIncrement(), netconfAccessInfo, this.transportFactory,
                createThreadName);
    }

    /**
     * @param transportFactory The transportFactory to set.
     */
    @SuppressWarnings("rawtypes")
    public void setTransportFactory(ITransportFactory transportFactory) {
        this.transportFactory = transportFactory;
    }

}
