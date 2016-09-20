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

import java.io.Reader;
import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.BadMessageFormatException;

public class NetconfAbilityTest {

	@Test
	public void testClientAbility() throws BadMessageFormatException, XMLStreamException {
		String text = "<capabilities><capability>urn:ietf:params:netconf:base:1.0</capability></capabilities>";
		Reader reader = new StringReader(text);
		XMLInputFactory factory = XMLInputFactory.newInstance(); 
		XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);

		NetconfAbility netconfAbility = new NetconfAbility(xmlReader);
		netconfAbility.getDeviceAbilityVolumn();
		NetconfAbility.getClientabilityhello();

	}

	@Test
	public void testClientAbilityBranch() throws BadMessageFormatException, XMLStreamException {
		String text = "<capability>This is branch</capability>";
		Reader reader = new StringReader(text);
		XMLInputFactory factory = XMLInputFactory.newInstance(); // Or
																	// newFactory()
		XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);

		NetconfAbility netconfAbility = new NetconfAbility(xmlReader);
		netconfAbility.getDeviceAbilityVolumn();
		NetconfAbility.getClientabilityhello();

	}
}
