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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.common.util.JsonUtil;
import org.openo.commsvc.protocolstack.common.util.RestfulUtil;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.ControllerCommInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfErrCode;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Communication argument manager fetches params from NSR and stores in cache<br>
 * <p>
 * </p>
 * 
 * @author
 * @version GSO 0.5 Sep 7, 2016
 */
public class CommunicationArgumentMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunicationArgumentMgr.class);

    private static final Map<String, CommunicateArg> devCommunicateArgs =
            Collections.synchronizedMap(new HashMap<String, CommunicateArg>());

    @SuppressWarnings("rawtypes")
    private static transient ICacheSessionPool cacheSessionPool;

    private static CommunicateArgChangeListener listener = new CommunicateArgChangeListener();

    // URL to communicate with ESR and fetch controller communication params
    public static final String ESR_GET_CONTROLLER_COMM_INFO = "/openoapi/extsys/v1/sdncontrollers/%s";

    private CommunicationArgumentMgr() {
        // private constructor
    }

    /**
     * Add communication argumentsto cache<br>
     * 
     * @param arg - communication arguments
     * @throws NetconfException when input is invalid or null
     * @since GSO 0.5
     */
    public static void addCommunicateArg(CommunicateArg arg) throws NetconfException {
        if((arg == null) || (null == arg.getIp())) {
            throw new NetconfException(NetconfErrCode.INTERNAL_ERROR, "getSession failed ,because of parameter error");
        }

        LOGGER.debug("addCommunicateArg, devIP is {} , charset is {}", new Object[] {arg.getIp(), arg.getCharset()});
        CommunicateArg oldComArg = null;
        if(arg instanceof CommunicateArgWrap) {
            oldComArg = devCommunicateArgs.put(arg.getControllerId(), arg);
        } else {
            oldComArg = devCommunicateArgs.put(arg.getControllerId(), new CommunicateArgWrap(arg, listener));
        }

        if(null != oldComArg) {
            ((CommunicateArgWrap)oldComArg).closeDevNetconfCache();
        }

    }

    /**
     * Delete communicate arguments<br>
     * 
     * @param ip - controllerId
     * @since GSO 0.5
     */
    public static void delCommunicateArg(String ip) {
        CommunicateArg oldComArg = devCommunicateArgs.remove(ip);
        if(null != oldComArg) {
            ((CommunicateArgWrap)oldComArg).closeDevNetconfCache();
        }
    }

    /**
     * Get communication parameter from ESR<br>
     * 
     * @param controllerId - Controller Id
     * @return communication arguments
     * @since GSO 0.5
     */
    public static CommunicateArg getCommunicateArg(String controllerId) {
        CommunicateArg oArg = null;
        if(null == devCommunicateArgs.get(controllerId)) {
            oArg = getControllerParamFromEsr(controllerId);
            if(null != oArg) {
                try {
                    addCommunicateArg(oArg);
                } catch(NetconfException e) {

                    LOGGER.error("Failed to add communication arguments to cache", e);
                }
            }

        }
        return oArg;

    }

    private static CommunicateArg getControllerParamFromEsr(String controllerId) {
        Map<String, String> paramsMap = new HashMap<String, String>();

        String url = String.format(ESR_GET_CONTROLLER_COMM_INFO, controllerId);

        // Step 1: Prepare url and method type
        paramsMap.put(CommonConstant.HttpContext.URL, url);
        paramsMap.put(CommonConstant.HttpContext.METHOD_TYPE, CommonConstant.MethodType.GET);

        RestfulResponse result = RestfulUtil.getRemoteResponse(paramsMap, null, null);

        if(result != null) {
            ControllerCommInfo oInfo = JsonUtil.fromJson(result.getResponseContent(), ControllerCommInfo.class);
            if(null != oInfo) {
                return converToCommunicateArg(oInfo);
            }
        }

        return null;
    }

    private static CommunicateArg converToCommunicateArg(ControllerCommInfo oInfo) {
        return new CommunicateArg(oInfo.getIp(oInfo.getUrl()), oInfo.getUserName(), oInfo.getPassword(),
                oInfo.getPort(oInfo.getUrl()), oInfo.getType(), 0, 0);

    }

    /**
     * Clears cache<br>
     * 
     * @since GSO 0.5
     */
    public static void clearAllCommArgs() {
        devCommunicateArgs.clear();
    }

    /**
     * Check if communication parameters exist for controller Id<br>
     * 
     * @param controllerId - Controller unique Id
     * @return true or false
     * @since GSO 0.5
     */
    public static boolean isContainsArg(String controllerId) {
        return devCommunicateArgs.containsKey(controllerId);
    }

    /**
     * Returns array of communication info<br>
     * 
     * @return Returns array of communication info
     * @since GSO 0.5
     */
    public static CommunicateArg[] getAllCommunicateArg() {
        Collection<CommunicateArg> args = devCommunicateArgs.values();
        return args.toArray(new CommunicateArg[args.size()]);
    }

    /**
     * @param cacheSessionPool The cacheSessionPool to set.
     */
    @SuppressWarnings("rawtypes")
    static void setCacheSessionPool(ICacheSessionPool cacheSessionPool) {
        LOGGER.info("setCacheSessionPool={}", cacheSessionPool);
        CommunicationArgumentMgr.cacheSessionPool = cacheSessionPool;
    }

    static class CommunicateArgChangeListener {

        @SuppressWarnings("unchecked")
        void handleChange(NetconfAccessInfo netconfAccessInfo) {
            if(null != cacheSessionPool) {
                cacheSessionPool.close(netconfAccessInfo);
            }
        }
    }

}
