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
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

public class UnLockArgTest {
	@Test
	public void testToCommand() throws NetconfException{
		
		TargetType rdb = TargetType.RDB;
		TargetType cdb = TargetType.CDB;
		TargetType startup = TargetType.STARTUP;
		UnLockArg unLockArg = new UnLockArg(rdb);
		assertTrue(unLockArg.toCommand() != null);
		unLockArg.setTargetCfgType(cdb);
		assertTrue(unLockArg.toCommand() != null);
		unLockArg.setTargetCfgType(startup);
		unLockArg.getTargetCfgType();
		assertTrue(unLockArg.toCommand() != null);
		unLockArg.setTargetCfgType(TargetType.URL);
		assertTrue(unLockArg.toCommand() != null);
	}
	
	@Test
	public void testCapable() throws NetconfException{
		TargetType rdb = TargetType.RDB;
		UnLockArg unLockArg = new UnLockArg(rdb);
		unLockArg.capable(1);
		unLockArg.capable(0);
		unLockArg.setTargetCfgType(TargetType.CDB);
		unLockArg.capable(0x3);
		unLockArg.setTargetCfgType(TargetType.CDB);
		unLockArg.capable(0x5);
		assertTrue(true);
	}
}
