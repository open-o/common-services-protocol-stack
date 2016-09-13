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

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpcReply;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfErrCode;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfTimeoutException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.TransportIOException;

/**
 * Use buffer pool NETCONF client default implementation class Because C60
 * version GA, the interface can not be modified prior to subsequent rounds need
 * to modify the version in the original interface.
 * 
 * @author
 */
public final class DefaultCacheNetconfClient extends AbstractNetconfClient implements INetconfClient {

    /**
     * NetconfAccess information to find the object
     */
    private INetconfAccessInfoLookup netconfAccessInfoLookup;

    /**
     * Constructor
     * 
     * @author
     * @param netconfAccessInfo
     *            NetconfAccess information
     * @param cacheSessionPool
     *            Netconf session Buffer pool
     * @param appName
     *            Create Netconf client application module name
     * @param createThreadName
     *            Create Netconf client thread name
     * @param netconfAccessInfoLookup
     *            INetconfAccessInfoLookup
     */
    @SuppressWarnings("rawtypes")
    public DefaultCacheNetconfClient(NetconfAccessInfo netconfAccessInfo, ICacheSessionPool cacheSessionPool,
            INetconfAccessInfoLookup netconfAccessInfoLookup, String appName, String createThreadName) {
        super(netconfAccessInfo, cacheSessionPool, appName, createThreadName);
        this.netconfAccessInfoLookup = netconfAccessInfoLookup;
    }

    /**
     * Implementation
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.AbstractNetconfClient#lookupNetconfAccessInfo(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo)
     * @author
     */
    @Override
    protected NetconfAccessInfo lookupNetconfAccessInfo(NetconfAccessInfo netconfAccessInfoKey)
            throws NetconfException {
        if(null != this.netconfAccessInfoLookup) {
            return this.netconfAccessInfoLookup.getNetconfAccessInfo(netconfAccessInfoKey);
        }

        CommunicateArg commArg = netconfAccessInfoKey.getCommArg();
        if(null == commArg) {
            String devIp = netconfAccessInfoKey.getDevIp();
            commArg = CommunicationArgumentMgr.getCommunicateArg(devIp);
            if(null == commArg) {
                throw new NetconfException(NetconfErrCode.INTERNAL_ERROR,
                        "communication argument doesn't exist of device=" + devIp);
            }
            netconfAccessInfoKey.setCommArg(commArg);
        }
        if(commArg instanceof CommunicateArgWrap) {
            ((CommunicateArgWrap)commArg).setDevId(netconfAccessInfoKey.getControllerId());
        }

        return netconfAccessInfoKey;
    }

    /**
     * Cover method
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.AbstractNetconfClient#send(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc)
     * @author
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected IRpcReply send(IRpc reqCmd) throws NetconfException {
        try {
            return super.send(reqCmd);
        } catch(TransportIOException e) {
            // Issued IO exception or a timeout you will need to close the
            // connection
            ISession session = getSession();
            getLogger().error("send IO error.{}", session);
            ((ICacheSessionPool)getSessionPool()).close(session);
            throw e;
        } catch(NetconfTimeoutException e) {
            // Issued IO exception or a timeout you will need to close the
            // connection
            ISession session = getSession();
            getLogger().error("se nd timeout error.{}", session);
            ((ICacheSessionPool)getSessionPool()).close(session);
            throw e;
        }
    }

}
