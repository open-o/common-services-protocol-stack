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

/**
 * simple introduction
 * 
 * @author
 * @see
 * @since 1.0
 */

public class HWContext extends RpcArg {

    /**
     * Corresponding argument constructor
     * 
     * @param lrID
     * @param vrID
     */
    public HWContext(Integer lrID, Integer vrID) {
        super("hwcontext", contextValue(lrID, vrID));
    }

    /**
     * contextValue
     * 
     * @param lrID
     * @param vrID
     * @return buf Buffer
     */
    private static String contextValue(Integer lrID, Integer vrID) {
        StringBuffer buf = new StringBuffer();
        if(lrID != null) {
            buf.append("ls=");
            buf.append(lrID.intValue());
        }
        if(vrID != null) {
            buf.append("vs=");
            buf.append(vrID.intValue());
        }

        return buf.toString();
    }

}
