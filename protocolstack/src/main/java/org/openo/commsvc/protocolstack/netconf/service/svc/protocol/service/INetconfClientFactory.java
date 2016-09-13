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

/**
 * Netconf client factory
 * 
 * @author
 * @param <T>
 */
public interface INetconfClientFactory<T extends NetconfAccessInfo> {

    /**
     * Close Netconf client
     * 
     * @author
     * @param netconfClient
     *            client
     */
    void close(INetconfClient netconfClient);

    /**
     * Get Netconf operation client
     * 
     * @author
     * @param netconfAccessInfo
     *            AccessInfo equipment Netconf access information
     * @param appName
     *            application module name used, can not be empty
     * @return Netconf operation client
     */
    INetconfClient getNetconfClient(T netconfAccessInfo, String appName);

    /**
     * Netconf test whether the specified device can be connected
     * 
     * @author
     * @param netconfAccessInfo
     *            AccessInfo equipment Netconf access information
     * @return 0 (Netconf ErrCode.SUCCESS): the test is successful, the other:
     *         the test is unsuccessful error code
     */
    int testConnective(T netconfAccessInfo);

}
