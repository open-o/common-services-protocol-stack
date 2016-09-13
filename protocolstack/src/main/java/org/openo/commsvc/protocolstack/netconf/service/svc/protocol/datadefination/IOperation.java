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
 * Netconf Operational level information
 * 
 * @author
 */
public interface IOperation {

    /**
     * Get the next issue of the content of the information layer devices
     * 
     * @author
     * @return Information sent to the device content layer
     */
    String getContent();

    /**
     * Is converted to packets sent at the operation level of the device
     * 
     * @author
     * @return send packets device operating layer
     * @throws NetconfException
     */
    String toCommand() throws NetconfException;
}
