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
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfErrCode;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

/**
 * Netconfå®¢æˆ·ç«¯å·¥åŽ‚é»˜è®¤å®žçŽ°ç±»ï¼Œä½¿ç”¨ç¼“å­˜çº¿ç¨‹æ± <br>
 * å› C60ç‰ˆæœ¬GAï¼Œä¸�èƒ½ä¿®æ”¹ä¹‹å‰�çš„æŽ¥å�£ï¼Œå�Žç»­ç‰ˆæœ¬å›žå�ˆéœ€è¦�åœ¨åŽŸæœ‰æŽ¥å�£ä¸Šä¿®æ”¹ã€‚
 * 
 * @author
 * @since CloudOpera Orchesator V100R001C00, 2016-2-1
 */
public class DefaultCacheNetconfClientFactory extends AbstractNetconfClientFactory<NetconfAccessInfo> {

    /**
     * NETCONF Sessionç¼“å­˜æ± 
     */
    @SuppressWarnings("rawtypes")
    private ICacheSessionPool cacheSessionPool;

    /**
     * Netconfè®¿é—®ä¿¡æ�¯æŸ¥æ‰¾å¯¹è±¡
     */
    private INetconfAccessInfoLookup netconfAccessInfoLookup;

    /**
     * å®žçŽ°æ–¹æ³•<br>
     * <br>
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.INetconfClientFactory#getNetconfClient(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo,
     *      java.lang.String)
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     */
    @Override
    public INetconfClient getNetconfClient(NetconfAccessInfo netconfAccessInfo, String appName) {
        if(null == netconfAccessInfo) {
            throw new IllegalArgumentException("netconfAccessInfo is null. appName=" + appName);
        }

        return new DefaultCacheNetconfClient(netconfAccessInfo, this.cacheSessionPool, this.netconfAccessInfoLookup,
                appName, Thread.currentThread().getName());
    }

    /**
     * @param cacheSessionPool The cacheSessionPool to set.
     */
    @SuppressWarnings("rawtypes")
    public void setCacheSessionPool(ICacheSessionPool cacheSessionPool) {
        this.cacheSessionPool = cacheSessionPool;
        CommunicationArgumentMgr.setCacheSessionPool(cacheSessionPool);
    }

    /**
     * @param netconfAccessInfoLookup The netconfAccessInfoLookup to set.
     */
    public void setNetconfAccessInfoLookup(INetconfAccessInfoLookup netconfAccessInfoLookup) {
        this.netconfAccessInfoLookup = netconfAccessInfoLookup;
    }

    /**
     * å®žçŽ°æ–¹æ³•<br>
     * <br>
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.INetconfClientFactory#testConnective(org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo)
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     */
    @SuppressWarnings("unchecked")
    @Override
    public int testConnective(NetconfAccessInfo netconfAccessInfo) {
        NetconfAccessInfo completeNetconfAccessInfo = netconfAccessInfo;
        if(null != this.netconfAccessInfoLookup) {
            try {
                completeNetconfAccessInfo = this.netconfAccessInfoLookup.getNetconfAccessInfo(netconfAccessInfo);
            } catch(NetconfException e) {
                getLogger().error("lookup netconf access info error. netconfAccessInfo=" + netconfAccessInfo, e);
                return NetconfErrCode.INTERNAL_ERROR;
            }
        } else {
            CommunicateArg commArg = completeNetconfAccessInfo.getCommArg();
            if(null == commArg) {
                String devIp = completeNetconfAccessInfo.getDevIp();
                commArg = CommunicationArgumentMgr.getCommunicateArg(devIp);
                if(null == commArg) {
                    getLogger().error("communicateArg is null. devIp={}", devIp);
                    return NetconfErrCode.COMMUNICATEARG_VERIFY_FAIL;
                }
                completeNetconfAccessInfo.setCommArg(commArg);
            }
            if(commArg instanceof CommunicateArgWrap) {
                ((CommunicateArgWrap)commArg).setDevId(completeNetconfAccessInfo.getControllerId());
            }
        }

        return this.cacheSessionPool.testConnective(completeNetconfAccessInfo);
    }
}
