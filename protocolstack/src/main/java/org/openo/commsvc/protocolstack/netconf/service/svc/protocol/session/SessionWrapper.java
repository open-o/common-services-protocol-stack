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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session;

import java.util.concurrent.atomic.AtomicBoolean;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.ISession;

/**
 * NETCONF session packaging . Use state for saving NETCONF session of
 * Note: The status and access critical data at every session of
 * org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.impl.DeviceSessionPool.monitor
 * protection
 * 
 * @author
 */
class SessionWrapper {

    /**
     * NETCONF session
     */
    private ISession session;

    /**
     * NETCONF session status of use
     */
    private AtomicBoolean freeStatus = new AtomicBoolean(true);

    /**
     * Constructor
     * 
     * @author
     */
    SessionWrapper() {
    }

    /**
     * The NETCONF session marked as unused
     * 
     * @author
     */
    void free() {
        this.freeStatus.set(true);
    }

    /**
     * @return Returns the session.
     */
    ISession getSession() {
        synchronized(this) {
            return this.session;
        }
    }

    /**
     * Current NETCONF session whether unused
     * 
     * @author
     * @return true: Unused , false: Used
     */
    boolean isFree() {
        return this.freeStatus.get();
    }

    /**
     * The NETCONF session marked for use
     * 
     * @author
     */
    void setUsing() {
        this.freeStatus.set(false);
    }

    /**
     * Cover method
     * 
     * @see java.lang.Object#toString()
     * @author
     */
    @Override
    public String toString() {

        return "SessionWrapper[" + getSession() + "," + this.freeStatus.get() + "]";
    }

    /**
     * Set a valid session to ensure that the caller is null <br>
     * this.session
     * 
     * @author
     * @param session NETCONF session
     */
    void setSession(ISession session) {
        synchronized(this) {
            this.session = session;
        }
    }

}
