
package org.openo.gso.commsvc.connector.netconf.service.svc.protocol.datadefination;

import org.openo.gso.commsvc.connector.netconf.service.svc.protocol.error.NetconfException;

/**
 *  <br>
 * @author
 * @since  
 */
public interface IRpc
{
    /**
     * <br>
     * @author
     * @since  
     * @return
     */
    String getContent();

    /**
     * <br>
     * @author
     * @since  
     * @return lrID
     */
    Integer getLrID();

    /**
     *  <br>
     * @author
     * @since  
     * @return
     */
    long getTimeout();

    /**
     *  <br>
     * @author
     * @since 
     * @return
     */
    String getCharset();

    /**
     *  <br>
     * @author
     * @since  
     * @param charset
     */
    void setCharset(String charset);

    /**
     * <br>
     * @author
     * @since  
     * @return vrID
     */
    Integer getVrID();   
}
