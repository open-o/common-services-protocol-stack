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

package org.openo.commsvc.protocolstack.netconf.service.svc.business.impl;

import java.text.MessageFormat;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.commsvc.protocolstack.common.business.ConnStatusMgr;
import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.common.constant.ErrorMessage;
import org.openo.commsvc.protocolstack.common.model.ConnInfo;
import org.openo.commsvc.protocolstack.common.model.ConnInfoQueryMsg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.Netconf;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connection manager to update the controller connection informations
 * 
 * @author
 */
public class ConnParamsMgr {

    /**
     * <p>
     * logger
     * </p>
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnParamsMgr.class);

    /**
     * <p>
     * cache session pool  for maintaining the all the device session pools
     * </p>
     */
    private CacheSessionPool<NetconfAccessInfo> cacheSessionPool;

    /**
     * update controller connection parameters<br>
     * 
     * @author
     * @param connInfoMsg controller connection info
     */
    public void updateConnParams(ConnInfoQueryMsg connInfoMsg) {
        // controller connection information coming null
        if(null == connInfoMsg) {
            LOGGER.error("the connInfo is null!");
            return;
        }

        // fetch all controller connection info
        ConnInfo[] connInfos = connInfoMsg.getConnections();
        if(ArrayUtils.isEmpty(connInfos)) {
            LOGGER.warn("connection infos array is empty! release unused sessions!");

            // clear the communication info from the CommunicationArgumentMgr
            CommunicationArgumentMgr.clearAllCommArgs();
            ConnStatusMgr.getInstance().clearAllConnStatus();
            cacheSessionPool.releaseUnUsedSession();
            return;
        }

        long updateTime = System.currentTimeMillis();
        updateConnParams(connInfos, updateTime);

        // clear unused sessions
        cleanUnnecessarySessions(updateTime);

    }

    /**
     * update controller connection parameters<br>
     * 
     * @author
     * @see
     * @param connInfos controller connection info
     * @updateTime update time
     */
    private void updateConnParams(ConnInfo[] connInfos, long updateTime) {
        // iterating connection array
        for(ConnInfo connInfo : connInfos) {
            String controllerId = connInfo.getControllerId();
            String hostName = connInfo.getHostName();
            String protocol = connInfo.getProtocol();

            // checking controller id is empty
            if(StringUtils.isEmpty(controllerId) || StringUtils.isEmpty(hostName)) {
                LOGGER.error(
                        MessageFormat.format("controllerId or hostName is empty, controllerId : {0}, hostName : {1}",
                                controllerId, hostName));
                continue;
            }

            // Check netconf protocol
            if(!ErrorMessage.NETCONF.equalsIgnoreCase(connInfo.getProtocol())) {
                LOGGER.error(
                        MessageFormat.format("protocol is invalid, controller ID : {0}, hostName : {1}, protocol : {2}",
                                controllerId, hostName, protocol));
                continue;
            }

            // fetch max connections supported per controller
            int maxConnNum = connInfo.getMaxConnectionsPerClient();
            if(maxConnNum < 1) {
                LOGGER.error(MessageFormat.format(
                        "maxConnNum is invalid, controller ID : {0}, hostName : {1}, maxConnNum : {2}", controllerId,
                        hostName, maxConnNum));
                continue;
            }

            // fetch the port of the controller
            int port = connInfo.getPort();
            if(port < 1 || port > 65535) {
                LOGGER.error(MessageFormat.format("port is invalid, controller ID : {0}, hostName : {1}, port : {2}",
                        controllerId, hostName, port));
                continue;
            }

            updateConnParams(connInfo, controllerId, updateTime);
        }
    }

    /**
     * update controller connection parameters<br>
     * 
     * @author
     * @param connInfo connection information
     * @param controllerId controller ID
     * @param updateTime update time
     */
    private void updateConnParams(ConnInfo connInfo, String controllerId, long updateTime) {
        CommunicateArg commArg;

        // Check if controllerId is already present in the CommunicationArgumentMgr
        if(CommunicationArgumentMgr.isContainsArg(controllerId)) {
            commArg = CommunicationArgumentMgr.getCommunicateArg(controllerId);
            commArg.setIp(connInfo.getHostName());
            commArg.setPort(connInfo.getPort());
            commArg.setUserName(connInfo.getCommParam(CommonConstant.UNAME_KEY));
            commArg.setPassword(connInfo.getCommParam(CommonConstant.PWD_KEY));
        } else {
            commArg = new CommunicateArg(connInfo.getHostName(), connInfo.getCommParam(CommonConstant.UNAME_KEY),
                    connInfo.getCommParam(CommonConstant.PWD_KEY), connInfo.getPort(), null, 0, 0);
            commArg.setControllerId(controllerId);
            commArg.setLoginTimeout(Netconf.getDefaultLoginTimeout());
            commArg.setResponseTimeout(Netconf.getDefaultResponseTimeout());

            // add to CommunicationArgumentMgr
            try {
                CommunicationArgumentMgr.addCommunicateArg(commArg);
            } catch(NetconfException e) {
                LOGGER.error(e.getMessage(), e);
            }

            // add controller to the ConnStatusMgr manager
            ConnStatusMgr.getInstance().addController(commArg.getControllerId(), CommonConstant.NETCONF);
        }
        commArg.setMaxConnNum(connInfo.getMaxConnectionsPerClient());
    }

    /**
     * Clear unused sessions based on the last updated time
     * 
     * @author
     * @param updateTime update time of the controller connection information
     */
    private void cleanUnnecessarySessions(long updateTime) {
        // get all connection informations
        CommunicateArg[] commArgs = CommunicationArgumentMgr.getAllCommunicateArg();
        for(CommunicateArg commArg : commArgs) {
            // check if the last updated time is grater or equal to updateTime
            // Creating NetconfAccessInfo from CommunicateArg
            NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo(commArg);
            cacheSessionPool.close(netconfAccessInfo);
            String controllerId = commArg.getControllerId();
            CommunicationArgumentMgr.delCommunicateArg(controllerId);
            ConnStatusMgr.getInstance().removeController(controllerId);
        }
    }

    public void setCacheSessionPool(CacheSessionPool<NetconfAccessInfo> cacheSessionPool) {
        this.cacheSessionPool = cacheSessionPool;
    }
}
