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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error;

/**
 * Error code definition
 * 
 * @author
 * @see
 */
public class NetconfErrCode {

    /**
     * Asynchronous exception handling
     */
    public static final int ASYNC_HANDLE_EXCEPTION = 1107495893;

    /**
     * hello packet session-id information returned by the device error
     */
    public static final int SERVER_HELLO_SESSSION_ID_ERROR = 1107495878;

    /**
     * Capability set does not support
     */
    public static final int CAPABILITY_VERIFY_FAIL = 1107495874;

    /**
     * Communication parameters checksum error
     */
    public static final int COMMUNICATEARG_VERIFY_FAIL = 1107495868;

    /**
     * Illegal IP
     */
    public static final int ILLEGAL_IP = 1107495882;

    /**
     * Stack Internal Error
     */
    public static final int INTERNAL_ERROR = -1;

    /**
     * A communication error with the device, may be due to network
     * interruption; equipment linked to death; exceeds the upper limit number
     * of connections and other equipment
     */
    public static final int SESSION_IOEXCEPTION = 1107495873;

    /**
     * LCT error exceptions allowed access allowed access protocol stack
     */
    public static final int SESSION_LCT_CONTROL_EXCEPTION = 1107495895;

    /**
     * Stack reached the maximum number of sessions
     */
    public static final int SESSION_UNAVAILABLE = 1107495869;

    /**
     * success
     */
    public static final int SUCCESS = 0;

    /**
     * Send command timeout
     */
    public static final int TIMEOUT = 1107495875;
    
    private NetconfErrCode() {
        // hiding public constructor.
    }
}
