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

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

/**
 * Netconf Session thread pool interface for Netconf session lifecycle
 * management The class that implements the interface must be released as a
 * single object or service Example
 * 
 * @author
 * @param <T>
 */
public interface ISessionPool<T extends NetconfAccessInfo> {

    /**
     * Remove the specified device all cached session Because the user may
     * modify the access level Netconf users like this new connection requires
     * new Netconf to access the device, but can not always use the old For the
     * session user is using, but if it is cached, then only the first mark,
     * when the user releases, and then delete it.
     * 
     * @author
     * @param netconfAccessInfo
     *            Device information, the main use of which deviD or devID
     */
    void close(T netconfAccessInfo);

    /**
     * Get session <br>
     * 
     * @author
     * @param netconfAccessInfo Netconf access information
     * @param createThreadName Creating the current thread's name Netconf session
     * @return ISession Netconf session
     * @throws NetconfException
     *             Get Netconf session exception
     */
    ISession getSession(T netconfAccessInfo, String createThreadName) throws NetconfException;

    /**
     * Release session, if the buffer pool, then back into the buffer pool, not
     * directly close
     * 
     * @author
     * @param session
     *            Netconf Session
     */
    void releaseSession(ISession session);

    /**
     * Connectivity test equipment
     * 
     * @author
     * @param netconfAccessInfo
     *            Equipment Netconf access information
     * @return 0:The test is successful, the other: the test is unsuccessful
     *         error code
     */
    int testConnective(T netconfAccessInfo);

}
