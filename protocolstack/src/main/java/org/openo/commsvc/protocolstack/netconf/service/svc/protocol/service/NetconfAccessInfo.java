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

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;

/**
 * Netconf access information<br>
 * <p>
 * If other domains have additional information to add extensions from the
 * current class
 * 
 * @author
 */
public class NetconfAccessInfo {

    /**
     * Particular communication parameters
     */
    private CommunicateArg commArg;

    /**
     * Device IP
     */
    private String devIp;

    /**
     * Controller ID
     */
    private String controllerId;

    /**
     * Constructor
     * 
     * @author
     * @param commArg
     *            Netconf communication parameters
     */
    public NetconfAccessInfo(CommunicateArg commArg) {
        if(null == commArg) {
            throw new IllegalArgumentException("commArg is null.");
        }

        String ip = commArg.getIp();
        if(null == ip) {
            throw new IllegalArgumentException("commArg'ip is null.");
        }

        this.controllerId = commArg.getControllerId();
        this.devIp = ip;
        this.commArg = commArg;
    }

    /**
     * Constructor
     * 
     * @author
     * @param controllerId
     *            Device ID
     * @param devIp
     *            Device IP
     */
    public NetconfAccessInfo(String controllerId, String devIp) {
        if((null == controllerId) || (null == devIp)) {
            throw new IllegalArgumentException("devId or devIp is null. devId=" + controllerId + ", devIp=" + devIp);
        }

        this.controllerId = controllerId;
        this.devIp = devIp;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    /**
     * Cover method
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * @author
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        NetconfAccessInfo other = (NetconfAccessInfo)obj;
        return ObjectUtils.equals(this.controllerId, other.controllerId) && ObjectUtils.equals(this.devIp, other.devIp);
    }

    /**
     * @return Returns the commArg.
     */
    public CommunicateArg getCommArg() {
        return this.commArg;
    }

    /**
     * @return Returns the devIp.
     */
    public String getDevIp() {
        return this.devIp;
    }

    /**
     * @see java.lang.Object#hashCode()
     * @author
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.controllerId == null) ? 0 : this.controllerId.hashCode());
        result = prime * result + ((this.devIp == null) ? 0 : this.devIp.hashCode());
        return result;
    }

    /**
     * @param commArg
     *            The commArg to set.
     */
    public void setCommArg(CommunicateArg commArg) {
        this.commArg = commArg;
    }

    /**
     * @see java.lang.Object#toString()
     * @author
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NetconfInfo[");
        builder.append(this.controllerId);
        builder.append(',');
        if(StringUtils.isEmpty(this.devIp)) {
            if(null != this.commArg) {
                builder.append(this.commArg.getIp()).append('.');
            }
        } else {
            builder.append(this.devIp);
        }
        builder.append(']');
        return builder.toString();
    }

}
