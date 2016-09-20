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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.util;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.junit.Test;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.BadMessageFormatException;

public class VTDNavAdapterTest {
	
    @Test
    public void encodeXmlTextTest()
    {
        String text = null;
        String result;
        VTDNavAdapter.encodeXmlText(text);
        
        
        text = "&";
        result = VTDNavAdapter.encodeXmlText(text);
        assertTrue(result.equals("&amp;"));
        
        
        text = "<";
        result =  VTDNavAdapter.encodeXmlText(text);
        assertTrue(result.equals("&lt;"));
        
        
        text = ">";
        result =  VTDNavAdapter.encodeXmlText(text);
        assertTrue(result.equals("&gt;"));
        
        
        text = "\"";
        result =  VTDNavAdapter.encodeXmlText(text);
        assertTrue(result.equals("&quot;"));
        
        
        text = "\'";
        result =  VTDNavAdapter.encodeXmlText(text);
        assertTrue(result.equals("&apos;"));
        
        text = "h";
        result =  VTDNavAdapter.encodeXmlText(text);
        assertTrue(result.equals("h"));
    }
    
    
    @Test(expected = NullPointerException.class)
    public void createStAXFactoryTestNull() throws BadMessageFormatException
    {
        String response = null;
        XMLStreamReader result;
        result =  VTDNavAdapter.createStAXFactory(response);
        assertTrue(result.equals(null));
    }
    
    @Test
    public void createStAXFactoryTest() throws BadMessageFormatException
    {
        String response = "abc";
        VTDNavAdapter.createStAXFactory(response);
    }
    
    @Test(expected = Exception.class)
    public void getElementTextTest() throws Exception
    {
        String text = "<tag src='test.com' attribute1='er'>ddfdfefsef</tag><!-- df -->";
        Reader reader = new StringReader(text);
      
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        XMLStreamReader streamreader = xmlif.createXMLStreamReader(reader);
        
        
        String result;
        
        result = VTDNavAdapter.getElementText(streamreader, "tag");
        result = VTDNavAdapter.getElementText(streamreader, "ser");
    }
}
