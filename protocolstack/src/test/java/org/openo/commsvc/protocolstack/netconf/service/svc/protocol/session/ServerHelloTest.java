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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session;

import static org.junit.Assert.*;

import java.io.Reader;
import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.junit.Test;

public class ServerHelloTest {

    @Test(expected = Exception.class)
    public void testServerHello() throws Exception{
        String text = "<hello>This is some XML</hello>";
        Reader reader = new StringReader(text);
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        XMLStreamReader streamreader = xmlif.createXMLStreamReader(reader);
        streamreader.close();
        reader.close();
        ServerHello hello = new ServerHello(streamreader);
    }
}
