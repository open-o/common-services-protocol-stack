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

package org.openo.commonsvc.protocolstack.moco;

import org.openo.sdno.testframework.moco.MocoHttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolStack extends MocoHttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolStack.class);

    private static String MOCO_TEST_PREFIX = "src/integration-test/resources/testcase/moco/";

    public ProtocolStack() {
        super();
    }

    @Override
    public void addRequestResponsePairs() {

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "esr_interface.json");

    }

}
