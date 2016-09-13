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

package org.openo.commsvc.protocolstack.netconf.service.svc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NetConf Service Registration
 * 
 * @author
 * @see
 * @since 1.0
 */
public class NetconfServiceUpRegister {

    /**
     * LOG
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NetconfServiceUpRegister.class);

    /**
     * Start
     */
    public void start() {
        LOGGER.warn("netconf started.");
    }

    /**
     * Stop
     */
    public void stop() {
        LOGGER.info("netconf stoped");
    }
}
