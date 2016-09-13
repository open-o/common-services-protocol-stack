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
 * The constant message header label s
 * 
 * @author
 */
public final class RpcMessageConstant {

    /**
     * set-id
     */
    public static final String ATTR_SETID = "set-id";

    /**
     * flow-id
     */
    public static final String ATTR_FLOWID = "flow-id";

    /**
     * message-id
     */
    public static final String ATTR_MESSAGE_ID = "message-id";

    public static final String ATTR_FULLSYNC_ID = "sync-full-id";

    /**
     * Return packet identification device
     */
    public static final String ELE_RPCREPLY = "rpc-reply";

    /**
     * ELE_ACTIVE Events
     */
    public static final String ELE_ACTIVE = "active";

    /**
     * data
     */
    public static final String ELE_DATA = "data";

    /**
     * Error message tag
     */
    public static final String ELE_RPC_ERROR = "rpc-error";

    /**
     * Error Details
     */
    public static final String ELE_ERROR_INFO = "error-info";

    /**
     * Application error message
     */
    public static final String ELE_ERROR_APPLICATION = "application";

    /**
     * Error message dynamic parameters
     */
    public static final String ELE_ERROR_PARAS = "error-paras";

    /**
     * Error message dynamic parameters
     */
    public static final String ELE_ERROR_PARA = "error-para";

    /**
     * Error type
     */
    public static final String ELE_ERROR_TYPE = "error-type";

    /**
     * Error-tag
     */
    public static final String ELE_ERROR_TAG = "error-tag";

    /**
     * error code
     */
    public static final String ELE_ERROR_APP_TAG = "error-app-tag";

    /**
     * Serious error identification
     */
    public static final String ELE_ERROR_SEVERITY = "error-severity";

    /**
     * ELE Error Message
     */
    public static final String ELE_ERROR_MESSAGE = "error-message";

    /**
     * ELE OK Mark
     */
    public static final String ELE_OK = "ok";

    /**
     * access denied
     */
    public static final int ACCESS_DENIED = 1;

    /**
     * APPLICATION
     */
    public static final int APPLICATION = 2;

    /**
     * Illegal property
     */
    public static final int BAD_ATTRIBUTE = 3;

    /**
     * Illegal elements
     */
    public static final int BAD_ELEMENT = 4;

    /**
     * existed
     */
    public static final int DATA_EXISTS = 5;

    /**
     * Missing Data
     */
    public static final int DATA_MISSING = 6;

    /**
     * error
     */
    public static final int ERROR = 7;

    /**
     * In Use
     */
    public static final int IN_USE = 8;

    /**
     * Invalid value
     */
    public static final int INVALID_VALUE = 9;

    /**
     * Lock DENIED
     */
    public static final int LOCK_DENIED = 10;

    /**
     * Missing ATTRIBUTE
     */
    public static final int MISSING_ATTRIBUTE = 11;

    public static final int MISSING_ELEMENT = 12;

    /**
     * operation failed
     */
    public static final int OPERATION_FAILED = 13;

    public static final int OPERATION_NOT_SUPPORT = 14;

    public static final int PARTIAL_OPERATION = 15;

    public static final int PROTOCOL = 16;

    public static final int RESOURCE_DENIED = 17;

    public static final int ROLLBACK_FAILED = 18;

    public static final int RPC = 19;

    public static final int TOO_BIG = 20;

    public static final int TRANSPORT = 21;

    /**
     * Unknown attribute
     */
    public static final int UNKNOWN_ATTRIBUTE = 22;

    public static final int UNKNOWN_ELEMENT = 23;

    public static final int UNKNOWN_NAMESPACE = 24;

    public static final int WARNING = 25;

    /**
     * Invalid device serial number
     */
    public static final int FLOW_ID_INVALID = -1;

    private RpcMessageConstant() {
        throw new IllegalAccessError("Trying to create instance of utility class.");
    }

}
