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

package org.openo.commsvc.protocolstack.common.constant;

/**
 * This class has constants for the to populate the rest response message.
 * <br>
 * 
 * @author
 * @version     GSO 0.5  Sep 7, 2016
 */
public class ErrorMessage {

    public static final String NETCONF = "NETCONF";

    public static final String CONTROLLER_ID = "controller_id";

    public static final String PARAM_CANNOT_EMPTY = "param_cannot_empty";

    public static final String REQUEST_BODY = "request_body";

    public static final String PROTOCOL = "protocol";

    public static final String INVALID_PARAMS = "invalid_params";

    public static final String PAYLOAD = "payload";

    public static final String CALLBACK_URL = "callback_url";

    public static final String INVOKEMODE = "invokemode";

    public static final String CONTROLLER_NOT_EXIST = "controller_not_exist";

    public static final String GET_CONN_FAILED = "get_conn_failed";

    private ErrorMessage() {
        throw new IllegalAccessError("utility class");
    }
}
