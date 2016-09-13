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
 * NETCONFCached thread pool Interface Because C60 version GA, the interface can
 * not be modified prior to subsequent rounds need to modify the version in the
 * original interface.
 * 
 * @author
 * @param <T>
 */
public interface ICacheSessionPool<T extends NetconfAccessInfo> extends ISessionPool<T> {

    /**
     * Directly off the device NETCONF connection
     * 
     * @author
     * @param session
     *            NETCONF connection device
     */
    void close(ISession session);
}
