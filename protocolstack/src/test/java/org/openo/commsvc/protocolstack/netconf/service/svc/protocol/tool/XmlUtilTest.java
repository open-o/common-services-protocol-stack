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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.tool;

import org.junit.Test;

public class XmlUtilTest {

	@Test
	public void getEmptyNodeTest() {
		XmlUtil.getEmptyNode("node");
	}

	@Test
	public void getEndNodeStrTest() {
		XmlUtil.getEndNodeStr("node");
	}

	@Test(expected = IllegalArgumentException.class)
	public void getNodeFullInfoTestNull() {
		StringBuilder result = null;
		XmlUtil.getNodeFullInfo("node", 1, null, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getNodeFullInfoTestFalseIndx() {
		StringBuilder result = null;
		XmlUtil.getNodeFullInfo("node", 1, "world <node", result);

	}

	@Test(expected = IllegalArgumentException.class)
	public void getNodeFullInfoTestTrueIndx() {
		StringBuilder result = null;
		XmlUtil.getNodeFullInfo("node", 6, "world <node", result);
	}

	@Test
	public void getNodeFullInfoTestTrueIndx1() {
		StringBuilder result = null;
		XmlUtil.getNodeFullInfo("node", 6, "worldnode", result);
	}

	@Test
	public void getNodeFullInfoTestTrueIndx2() {
		StringBuilder result = new StringBuilder();
		XmlUtil.getNodeFullInfo("node", 6, "world <node/>er", result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getNodeFullInfoTestTrueIndx3() {
		StringBuilder result = new StringBuilder();
		XmlUtil.getNodeFullInfo("node", 6, "world <node>e/r", result);
	}

	@Test
	public void getNodeFullInfoTestTrueIndx4() {
		StringBuilder result = new StringBuilder();
		XmlUtil.getNodeFullInfo("node", 6, "world <node>e</node>r", result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getStartNodeInfoTestNull() {
		StringBuilder result = null;
		XmlUtil.getStartNodeInfo("node", null, result);
	}

	@Test
	public void getStartNodeInfoTestFalseIndx() {
		StringBuilder result = new StringBuilder();
		XmlUtil.getStartNodeInfo("node", "Hello <node>r", result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getStartNodeInfoTestFalseIndxException() {
		StringBuilder result = new StringBuilder();
		XmlUtil.getStartNodeInfo("xml", "Hello <node>r", result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getStartNodeInfoTestFalseIndxException1() {
		StringBuilder result = new StringBuilder();
		XmlUtil.getStartNodeInfo("node", "Hello <noder", result);
	}

	@Test
	public void getStartNodeStrTest() {
		XmlUtil.getStartNodeStr("node");
	}

	@Test
	public void newSAXReaderTest() {
		XmlUtil.newSAXReader();
	}

	@Test
	public void newXMLInputFactoryTest() {
		XmlUtil.newXMLInputFactory();
	}
}
