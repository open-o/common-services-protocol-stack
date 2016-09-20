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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TargetTypeTest {

	@Test
	public void testTargetCommand() {
		TargetType rdb = TargetType.RDB;
		TargetType cdb = TargetType.CDB;
		TargetType startup = TargetType.STARTUP;
		TargetType url = TargetType.URL;
		TargetType checkpoint = TargetType.CHECKPOINT;

		assertTrue(rdb.targetCommand("content", "tag") != null);
		assertTrue(cdb.targetCommand("content", "tag") != null);
		assertTrue(startup.targetCommand("content", "tag") != null);
		assertTrue(url.targetCommand("content", "tag") != null);
		assertTrue(checkpoint.targetCommand("content", "tag") != null);
	}
}
