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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.common.business.ConnStatusMgr;
import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.common.constant.ErrorMessage;
import org.openo.commsvc.protocolstack.common.util.AppUtil;
import org.openo.commsvc.protocolstack.common.util.AsyncNetconfTask;
import org.openo.commsvc.protocolstack.common.util.ThreadPool;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.INetconfMsgBusiness;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfOperationArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfReqMessage;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfResponse;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpcReply;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.AbstractNetconfClientFactory;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.INetconfClient;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NetconfMsgBusinessImpl implements the INetconfMsgBusiness
 * Sends the request to netconf cleint based on request type (SYNC | ASYNC)
 * 
 * @author
 */
public class NetconfMsgBusinessImpl implements INetconfMsgBusiness {

    /**
     * log object
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(NetconfMsgBusinessImpl.class);

    /**
     * NETCONF Client Factory Object
     */
    private AbstractNetconfClientFactory<NetconfAccessInfo> netconfClientFactory;

    /**
     * ConnParamsMgr Object
     */
    private ConnParamsMgr connParamsMgr;

    @Override
    public NetconfResponse dispatchMessage(String controllerId, NetconfReqMessage message) throws ServiceException {

        if(StringUtils.isEmpty(controllerId)) {
            ExceptionArgs args = new ExceptionArgs();
            args.setDescArgs(new String[] {ErrorMessage.CONTROLLER_ID});
            throw new ServiceException(ErrorMessage.PARAM_CANNOT_EMPTY, HttpStatus.INTERNAL_SERVER_ERROR_500, args);
        }

        if(null == message) {
            ExceptionArgs args = new ExceptionArgs();
            args.setDescArgs(new String[] {ErrorMessage.REQUEST_BODY});
            throw new ServiceException(ErrorMessage.PARAM_CANNOT_EMPTY, HttpStatus.INTERNAL_SERVER_ERROR_500, args);
        }

        if(!ErrorMessage.NETCONF.equalsIgnoreCase(message.getProtocol())) {
            ExceptionArgs args = new ExceptionArgs();
            args.setDescArgs(new String[] {ErrorMessage.PROTOCOL});
            throw new ServiceException(ErrorMessage.INVALID_PARAMS, HttpStatus.INTERNAL_SERVER_ERROR_500, args);
        }

        if(StringUtils.isEmpty(message.getPayload())) {
            ExceptionArgs args = new ExceptionArgs();
            args.setDescArgs(new String[] {ErrorMessage.PAYLOAD});
            throw new ServiceException(ErrorMessage.PARAM_CANNOT_EMPTY, HttpStatus.INTERNAL_SERVER_ERROR_500, args);
        }

        return sendReqByInvokeMode(controllerId, message);
    }

    /**
     * This method sends the netconf message as per the invoked mode
     * to the specified controller using controllerId.
     * Invoked mode are of two type: SYNC and ASYNC.
     * <br>
     * 
     * @param controllerId
     * @param message
     * @return
     * @throws ServiceException
     * @since GSO 0.5
     */
    private NetconfResponse sendReqByInvokeMode(String controllerId, NetconfReqMessage message)
            throws ServiceException {
        String invokeMode = message.getInvokeMode();
        if(CommonConstant.ASYNC.equalsIgnoreCase(invokeMode)) {

            if(StringUtils.isEmpty(message.getCallbackPostUrl())) {
                ExceptionArgs args = new ExceptionArgs();
                args.setDescArgs(new String[] {ErrorMessage.CALLBACK_URL});
                throw new ServiceException(ErrorMessage.PARAM_CANNOT_EMPTY, HttpStatus.INTERNAL_SERVER_ERROR_500, args);
            }

            AsyncNetconfTask task = new AsyncNetconfTask(controllerId, message, this);
            ThreadPool.getInstance().execute(task);
            return null;
        } else if(CommonConstant.SYNC.equalsIgnoreCase(invokeMode)) {

            ConnStatusMgr.getInstance().addConnNum(controllerId);
            try {
                return sendNetconfRequest(controllerId, message);
            } finally {

                ConnStatusMgr.getInstance().subConnNum(controllerId);
            }
        } else {
            ExceptionArgs args = new ExceptionArgs();
            args.setDescArgs(new String[] {ErrorMessage.INVOKEMODE});
            throw new ServiceException(ErrorMessage.INVALID_PARAMS, HttpStatus.INTERNAL_SERVER_ERROR_500, args);
        }
    }

    /**
     * Send netconf request and get response <br>
     * 
     * @param controllerId - Controller ID
     * @param message - NetConf request message
     * @return - Netconf response
     * @throws ServiceException - when input is invalid or null
     * @since GSO 0.5
     */
    public NetconfResponse sendNetconfRequest(String controllerId, NetconfReqMessage message) throws ServiceException {

        CommunicateArg commArg = CommunicationArgumentMgr.getCommunicateArg(controllerId);

        if(null == commArg) {
            ExceptionArgs args = new ExceptionArgs();
            throw new ServiceException(ErrorMessage.CONTROLLER_NOT_EXIST, HttpStatus.INTERNAL_SERVER_ERROR_500, args);
        }
        commArg.setControllerId(controllerId);

        NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo(commArg);
        INetconfClient netconfClient =
                netconfClientFactory.getNetconfClient(netconfAccessInfo, AppUtil.getProcessName());

        if(null == netconfClient) {
            LOGGER.error("failed to get netconf client, controllerID : " + controllerId);
            throw new ServiceException(ErrorMessage.GET_CONN_FAILED, HttpStatus.INTERNAL_SERVER_ERROR_500);
        }

        try {

            NetconfOperationArg operArg = new NetconfOperationArg(message.getPayload());
            IRpcReply reply = netconfClient.send(false, message.getRpcArgList(), operArg);
            return new NetconfResponse(reply.getResponse());
        } catch(NetconfException e) {
            LOGGER.error("failed to send netconf request, controllerID : " + controllerId, e);
            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR_500);
        } finally {
            netconfClient.close();
        }
    }

    public AbstractNetconfClientFactory<NetconfAccessInfo> getNetconfClientFactory() {
        return netconfClientFactory;
    }

    public void setNetconfClientFactory(AbstractNetconfClientFactory<NetconfAccessInfo> netconfClientFactory) {
        this.netconfClientFactory = netconfClientFactory;
    }

    public ConnParamsMgr getConnParamsMgr() {
        return connParamsMgr;
    }

    public void setConnParamsMgr(ConnParamsMgr connParamsMgr) {
        this.connParamsMgr = connParamsMgr;
    }

}
