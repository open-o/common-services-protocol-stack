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

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.BadMessageFormatException;

public class RspCmdTest {

	@Test
	public void testRspCmd() throws XMLStreamException, BadMessageFormatException {
		
		String text = "<rpc-reply><active>urn:ietf:params:netconf:base:1.0</active><rpc-error></rpc-error><rpc-error><error-type></error-type><error-tag></error-tag><error-severity></error-severity><error-app-tag></error-app-tag><error-message></error-message><error-info></error-info><error-para></error-para></rpc-error></rpc-reply>";
		String input = "<as>thisis</as>";
		
		RspCmd rspCmdEmpty = new RspCmd();
		RspCmd rspCmd = new RspCmd(text);
		RspCmd rspCmd1 = new RspCmd("<active>urn:ietf:params:netconf:base:1.0</active>");
		
		RspCmd rspCmd3 = new RspCmd("<rpc-reply>urn:ietf:params:netconf:base:1.0</rpc-reply>");
		rspCmd.addErrorInfo(null);
		rspCmd.isOK();
		RpcErrorInfo errorInfo = new RpcErrorInfo();
		rspCmd1.addErrorInfo(errorInfo );
		rspCmd1.isOK();
		rspCmd1.getMessageID();
		rspCmd1.isActive();
		assertTrue(rspCmd1.getErrorInfos() != null);
		
	}
	
	@Test
	public void testResponse() throws BadMessageFormatException{
		RspCmd rspCmd2 = new RspCmd("<as><actives>urn:ietf:params:netconf:base:1.0</actives></as>");
		String input = "response";
		rspCmd2.setResponse(input);
		assertTrue(input == rspCmd2.getResponse());
		
	}
}
