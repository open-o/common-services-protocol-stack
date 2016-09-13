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

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.BadMessageFormatException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.tool.XmlUtil;

/**
 * @author
 */
public class VTDNavAdapter {

    private VTDNavAdapter() {
        throw new IllegalAccessError("trying to create object of utility class");
    }
    
    
    /**
     * 
     * <br>
     * 
     * @param response
     * @return
     * @throws BadMessageFormatException
     * @since  GSO 0.5
     */
    public static XMLStreamReader createStAXFactory(String response) throws BadMessageFormatException {
        if(response == null) {
            return null;
        }

        XMLInputFactory factory = XmlUtil.newXMLInputFactory();
        try {
            return factory.createXMLStreamReader(new StringReader(response.trim()));
        } catch(XMLStreamException e) {
            throw new BadMessageFormatException("rpc-reply is not illegal.", e);
        }
    }

    /**
     * 
     * <br>
     * 
     * @param reader
     * @param attributeName
     * @param defaultValue
     * @return
     * @since  GSO 0.5
     */
    public static String getStringAttribute(XMLStreamReader reader, String attributeName, String defaultValue) {
        String attr = reader.getAttributeValue(null, attributeName);
        if((attr == null) || "".equals(attr)) {
            return defaultValue;
        }
        return attr;
    }

    /**
     * 
     * <br>
     * 
     * @param reader
     * @param attributeName
     * @param defaultValue
     * @return
     * @since  GSO 0.5
     */
    public static int getIntAttribute(XMLStreamReader reader, String attributeName, int defaultValue) {
        String attr = reader.getAttributeValue(null, attributeName);
        try {
            if(attr != null) {
                return Integer.parseInt(attr.trim());
            } else {
                return defaultValue;
            }
        } catch(NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 
     * <br>
     * 
     * @param reader
     * @param tag
     * @return
     * @throws XMLStreamException
     * @since  GSO 0.5
     */
    public static String getElementText(XMLStreamReader reader, String tag) throws XMLStreamException {
        StringWriter out = new StringWriter();
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(out);
        int eventType = reader.next();
        while(true) {
            if((eventType == XMLStreamConstants.CHARACTERS) || (eventType == XMLStreamConstants.CDATA)
                    || (eventType == XMLStreamConstants.SPACE) || (eventType == XMLStreamConstants.ENTITY_REFERENCE)) {
                writer.writeCharacters(reader.getText());
            } else if(eventType == XMLStreamConstants.START_ELEMENT) {
                writeStartElement(reader, writer);
            } else if(eventType == XMLStreamConstants.END_ELEMENT) {
                if(tag.equals(reader.getLocalName())) {
                    break;
                }
                writer.writeEndElement();
            }
            eventType = reader.next();
        }
        return out.toString().trim();
    }

    /**
     * 
     * <br>
     * 
     * @param reader
     * @param writer
     * @throws XMLStreamException
     * @since  GSO 0.5
     */
    private static void writeStartElement(XMLStreamReader reader, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(reader.getLocalName());

        int nsCnt = reader.getNamespaceCount();
        for(int i = 0; i < nsCnt; i++) {
            writer.writeNamespace(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
        }

        int cnt = reader.getAttributeCount();
        for(int i = 0; i < cnt; i++) {
            writer.writeAttribute(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
        }
    }

    /**
     * In a string of special characters escaped
     * 
     * @author
     * @see
     * @param text
     * @return
     */
    public static String encodeXmlText(String text) {
        if(text == null) {
            return null;
        }

        char[] chars = new char[text.length()];
        text.getChars(0, text.length(), chars, 0);
        StringBuilder buf = new StringBuilder();
        for(int i = 0; i < chars.length; i++) {
            switch(chars[i]) {
                case '&': {
                    buf.append("&amp;");
                    break;
                }
                case '<': {
                    buf.append("&lt;");
                    break;
                }
                case '>': {
                    buf.append("&gt;");
                    break;
                }
                case '"': {
                    buf.append("&quot;");
                    break;
                }
                case '\'': {
                    buf.append("&apos;");
                    break;
                }
                default: {
                    buf.append(chars[i]);
                }
            }
        }
        return buf.toString();
    }
}
