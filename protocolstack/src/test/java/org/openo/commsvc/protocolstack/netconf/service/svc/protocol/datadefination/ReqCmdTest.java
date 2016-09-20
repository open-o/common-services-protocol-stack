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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.model.NetconfOperationArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;

public class ReqCmdTest {

	@Test
	public void testToCommnad() throws NetconfException {
		List<RpcArg> rpcArgs = new ArrayList<>();
		RpcArg arg0 = new RpcArg();
		arg0.setName("hwcontext");
		rpcArgs.add(arg0);
		IOperation operationArg = new NetconfOperationArg("command");
		ReqCmd reqCmd = new ReqCmd(rpcArgs, operationArg, true);
		assertTrue(reqCmd.toCommand(1) != null);
	}

	@Test
	public void testToCommnadBranch() throws NetconfException {
		List<RpcArg> rpcArgs = new ArrayList<>();
		RpcArg arg0 = new RpcArg();
		rpcArgs.add(arg0);
		ReqCmd reqCmd = new ReqCmd(null, null, true);
		assertTrue(reqCmd.toCommand(1) != null);
	}

	@Test
	public void testToCommnadBranch1() throws NetconfException {
		List<RpcArg> rpcArgs = new ArrayList<>();
		RpcArg arg0 = new RpcArg();
		rpcArgs.add(arg0);
		ReqCmd reqCmd = new ReqCmd(rpcArgs, null, true);
		assertTrue(reqCmd.toCommand(1) != null);
	}

	@Test
	public void testToCommnadBranch2() throws NetconfException {
		List<RpcArg> rpcArgs = new ArrayList<>();
		RpcArg arg0 = new RpcArg();
		rpcArgs.add(null);
		ReqCmd reqCmd = new ReqCmd(rpcArgs, null, true);
		assertTrue(reqCmd.toCommand(1) != null);
	}

	@Test
	public void testGetSet() {
		List<RpcArg> rpcArgs = new ArrayList<>();
		RpcArg arg0 = new RpcArg();
		arg0.setName("hwcontext");
		rpcArgs.add(arg0);
		IOperation ioperation = new NetconfOperationArg("command");
		OperationArg operationArg = new NetconfOperationArg("command");
		ReqCmd reqCmd = new ReqCmd(rpcArgs, ioperation, true);
		reqCmd.getContent();
		reqCmd.getLrID();
		reqCmd.getVrID();
		reqCmd.setOperationArg(operationArg);
		assertTrue(reqCmd.getOperationArg() == operationArg);
		String charset = "charset";
		reqCmd.setCharset(charset);
		assertTrue(charset == reqCmd.getCharset());
		int timeout = 10;
		reqCmd.setTimeout(10);
		assertTrue(timeout == reqCmd.getTimeout());
		reqCmd.setEnableGetNext(true);
		assertTrue(reqCmd.isEnableGetNext());
		reqCmd.setRpcArgList(rpcArgs);
		assertTrue(reqCmd.getRpcArgList() == rpcArgs);
	}
}
