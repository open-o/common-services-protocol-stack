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

import org.openo.commsvc.protocolstack.common.constant.CommonConstant;

/**
 * NETCONF communication timeout exception.
 * Usually when messaging timeout situation.
 * 
 * @author
 * @see
 */
public class NetconfTimeoutException extends NetconfException {

    private static final long serialVersionUID = -4417154651925608687L;

    /**
     * How much actual execution time
     */
    private final long actual;

    /**
     * Setting timeout is the number
     */
    private final long timeout;

    /**
     * Constructor
     * 
     * @author
     * @see
     * @param operation
     * @param timeout
     * @param actual
     */
    public NetconfTimeoutException(String operation, long timeout, long actual) {
        this(operation, timeout, actual, null);
    }

    /**
     * Constructor
     * 
     * @author
     * @see
     * @param operation
     * @param timeout Setting the default timeout
     * @param actual The actual length of waiting time
     * @param cause Due to an unrecoverable exception
     */
    public NetconfTimeoutException(String operation, long timeout, long actual, Throwable cause) {
        super(NetconfErrCode.TIMEOUT, getErrorInfo(operation, timeout, actual), cause);
        this.actual = actual;
        this.timeout = timeout;
    }

    /**
     * <br>
     * 
     * @author
     * @see
     * @param operation
     * @param timeout
     * @param actual
     * @return
     */
    private static String getErrorInfo(String operation, long timeout, long actual) {
        StringBuilder builder = new StringBuilder(CommonConstant.DEFAULT_STRING_LENGTH_128);
        builder.append("TIMEOUT(");
        builder.append(operation);
        builder.append("), expected=");
        builder.append(timeout);
        builder.append(", actual=");
        builder.append(actual);

        return builder.toString();
    }

    public long getActual() {
        return actual;
    }

    public long getTimeout() {
        return timeout;
    }
}
