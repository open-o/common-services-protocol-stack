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
 * @author
 */
public class NetconfException extends Exception {

    /**
     * Eclipse automatically generated serial number
     */
    private static final long serialVersionUID = 543890907406483040L;

    /**
     * error code
     */
    private int errorCode;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @param errorCode
     * @since  GSO 0.5
     */
    public NetconfException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode
     * @param details
     */
    public NetconfException(int errorCode, String details) {
        super(details);
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode
     * @param details
     * @param cause
     */
    public NetconfException(int errorCode, String details, Throwable cause) {
        super(details, cause);
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode
     * @param cause
     */
    public NetconfException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
