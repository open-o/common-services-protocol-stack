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

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

/**
 * 操作参数基类
 * <p>
 * 
 * @author
 * @since 1.0
 */
public abstract class OperationArg implements IOperation {

    /**
     * Content layer
     */
    protected String content;

    /**
     * Whether to active
     */
    protected boolean keepAlive;

    /**
     * This calibration set for a device capability is valid.
     * 
     * @author
     * @param deviceCapability
     *            flag
     * @return true: false:
     */
    public boolean capable(int deviceCapability) {
        return (NetconfAbility.BASE & deviceCapability) != 0;
    }

    /**
     * @return Returns the content.
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * @param content
     *            The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Implementation
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.datadefination.IOperation#toCommand()
     * @author
     */
    @Override
    public abstract String toCommand() throws NetconfException;

}
