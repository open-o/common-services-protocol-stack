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
 * Stack type definition capability
 * 
 * @author
 */
public class Ability {

    public static final int BASE = 1;

    public static final int WRITABLE_RUNNING = 0x2;

    public static final int CANDIDATE = 0x4;

    public static final int CONFIRMED_COMMIT = 0x8;

    public static final int ROLLBACK_ON_ERROR = 0x10;

    public static final int VALIDATE = 0x20;

    public static final int STARTUP = 0x40;

    public static final int URL = 0x80;

    public static final int XPATH = 0x100;

    public static final int ROLLBACK = 0x200;

    public static final int COMPARE = 0x400;

    public static final int EXECUTE_CLI = 0x800;

    public static final int EXCHANGE = 0x1000;

    public static final int SYNC = 0x2000;

    public static final int SYNC12 = 0x400000;

    public static final int ACTION = 0x4000;

    public static final int ACTIVE = 0x8000;

    public static final int DISCARD_COMMIT = 0x10000;

    public static final int PREVIEW = 0x20000;

    public static final String BASE_NAME = "urn:ietf:params:netconf:base:1.0";

    public static final String WRITABLE_RUNNING_NAME = "urn:ietf:params:netconf:capability:writable-running:1.0";

    public static final String CANDIDATE_NAME = "urn:ietf:params:netconf:capability:candidate:1.0";

    public static final String CONFIRMED_COMMIT_NAME = "urn:ietf:params:netconf:capability:confirmed-commit:1.0";

    public static final String ROLLBACK_ON_ERROR_NAME = "urn:ietf:params:netconf:capability:rollback-on-error:1.0";

    public static final String VALIDATE_NAME = "urn:ietf:params:netconf:capability:validate:1.0";

    public static final String STARTUP_NAME = "urn:ietf:params:netconf:capability:startup:1.0";

    public static final String URL_NAME = "urn:ietf:params:netconf:capability:url:1.0?protocol=ftp";

    public static final String XPATH_NAME = "urn:ietf:params:netconf:capability:xpath:1.0";

    private Ability() {
        throw new IllegalAccessError("trying to create instance of utility class.");
    }
}
