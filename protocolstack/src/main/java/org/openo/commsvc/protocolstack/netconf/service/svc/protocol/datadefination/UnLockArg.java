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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination;

import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

/**
 * ununlock Operating parameters are defined
 * 
 * @author
 * @see
 */
public class UnLockArg extends OperationArg {

    /**
     * Target configuration set type ：running，candidate，startup
     */
    private TargetType targetCfgType;

    /**
     * 
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @param targetCfgType
     * @throws NetconfException
     * @since  GSO 0.5
     */
    public UnLockArg(TargetType targetCfgType) throws NetconfException {
        super();
        this.targetCfgType = targetCfgType;
    }

    public TargetType getTargetCfgType() {
        return targetCfgType;
    }

    public void setTargetCfgType(TargetType targetCfgType) {
        this.targetCfgType = targetCfgType;
    }

    @Override
    public boolean capable(int deviceCapability) {
        if(!super.capable(deviceCapability)) {
            return false;
        }

        if(targetCfgType == TargetType.CDB && (NetconfAbility.CANDIDATE & deviceCapability) == 0) {
            return false;
        }
        return true;
    }

    @Override
    public String toCommand() throws NetconfException {
        StringBuilder buf = new StringBuilder(CommonConstant.DEFAULT_STRING_LENGTH_128);
        buf.append("<unlock><target>");

        if(TargetType.RDB == targetCfgType) {
            buf.append("<running/>");
        } else if(TargetType.CDB == targetCfgType) {
            buf.append("<candidate/>");
        } else if(TargetType.STARTUP == targetCfgType) {
            buf.append("<startup/>");
        }
        buf.append("</target></unlock>");
        return buf.toString();
    }
}
