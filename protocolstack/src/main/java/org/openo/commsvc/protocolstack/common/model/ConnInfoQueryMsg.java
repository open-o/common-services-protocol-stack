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

package org.openo.commsvc.protocolstack.common.model;

/**
 * ConnInfoQueryMsg is model to maintain connectionInfo.
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version     GSO 0.5  Sep 7, 2016
 */
public class ConnInfoQueryMsg {

    private ConnInfo[] connInfo;

    /**
     * Connection information- Just a <br/>
     * 
     * @param connInfo - Controller connection information
     * @since GSO 0.5
     */
    public ConnInfoQueryMsg(ConnInfo[] connInfo) {
        this.connInfo = connInfo;
    }

    /**
     * Get list of Connections<br>
     * 
     * @return array of connection information
     * @since GSO 0.5
     */
    public ConnInfo[] getConnections() {
        return connInfo;
    }

}
