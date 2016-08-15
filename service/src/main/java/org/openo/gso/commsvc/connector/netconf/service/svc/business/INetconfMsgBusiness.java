package org.openo.gso.commsvc.connector.netconf.service.svc.business;

import javax.servlet.http.HttpServletRequest;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.gso.commsvc.connector.netconf.service.svc.model.NetconfReqMessage;
import org.openo.gso.commsvc.connector.netconf.service.svc.model.NetconfResponse;


public interface INetconfMsgBusiness
{
    /**
     * <br>
     * @author
     * @see 
     * @since
     * @param controllerId
     * @param message 
     * @param httpContext 
     * @return 
     * @throws ServiceException 
     */
    public NetconfResponse dispatchMessage(String controllerId, NetconfReqMessage message, HttpServletRequest httpContext) throws ServiceException;
}
