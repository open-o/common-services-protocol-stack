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

package org.openo.commsvc.protocolstack.netconf.service.svc.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commsvc.protocolstack.common.util.JsonUtil;
import org.openo.commsvc.protocolstack.netconf.service.svc.business.INetconfMsgBusiness;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfReqMessage;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author
 */
@Path("/protocolstack-netconf/v1/extSysID")
public class NetconfROAService {

    /**
     * <p>
     * Logger for logging
     * </p>
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NetconfROAService.class);

    /**
     * <p>
     * Netconf business implementation
     * </p>
     */
    @Autowired
    private INetconfMsgBusiness netconfMsgBusiness;

    /**
     * Dispatches the json rest messages to <br>
     * 
     * @author
     * @param message NETCONF request
     * @param controllerId controller Id
     * @return NetconfResponse response to rest client
     * @throws ServiceException exception from netconf cleint
     */
    @POST
    @Path("/{extSys-id}/messages")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public NetconfResponse dispatchMessage(String message, @PathParam("extSys-id") String controllerId)
            throws ServiceException {
        try {
            NetconfReqMessage req = JsonUtil.fromJson(message, NetconfReqMessage.class);
            NetconfResponse resp = netconfMsgBusiness.dispatchMessage(controllerId, req);
            LOGGER.warn("NETCONF_DISPATCH : " + controllerId + " , SUCCESS ");
            return resp;
        } catch(ServiceException e) {
            LOGGER.error("NETCONF_DISPATCH : " + controllerId + " , FAILURE ");
            throw e;
        }
    }

    public INetconfMsgBusiness getNetconfMsgBusiness() {
        return netconfMsgBusiness;
    }

    public void setNetconfMsgBusiness(INetconfMsgBusiness netconfMsgBusiness) {
        this.netconfMsgBusiness = netconfMsgBusiness;
    }
}
