package org.openo.gso.commsvc.connector.netconf.service.svc.protocol.datadefination;

import org.openo.gso.commsvc.connector.netconf.service.svc.protocol.error.NetconfException;

/**
 * Netconf<br>
 * @author
 * @since  CloudOpera Orchesator V100R001C00, 2016-2-1
 */
public interface IOperation
{
    /**
     *<br>
     * @author
     * @since 
     * @return 
     */
    String getContent();

    /**
     * 转换为下发给设备的操作层的报文<br>
     * @author
     * @since 
     * @return 下发给设备的操作层的报文
     * @throws NetconfException 转换异常
     */
    String toCommand() throws NetconfException;
}
