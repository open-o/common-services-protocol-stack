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

import java.io.IOException;

import javax.xml.stream.XMLInputFactory;

import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML helper class
 * 
 * @author
 */
@Deprecated
public class XmlUtil {

    /**
     * End character node
     */
    public static final char NODE_END_FLAG = '>';

    /**
     * End nodes slash
     */
    public static final char NODE_END_SLASH = '/';

    /**
     * Node start character
     */
    public static final char NODE_START_FLAG = '<';
    
    private XmlUtil() {
        throw new IllegalAccessError("trying to create instance for utility class.");
    }

    /**
     * Assembled empty node , such as data
     * 
     * @author
     * @param nodeName nodeName
     * @return Empty node
     */
    public static String getEmptyNode(String nodeName) {
        StringBuilder builder = new StringBuilder();
        builder.append(NODE_START_FLAG).append(nodeName).append(NODE_END_SLASH).append(NODE_END_FLAG);
        return builder.toString();
    }

    /**
     * Assembled by node name ending XML node
     * rpc-reply
     * 
     * @author
     * @param nodeName nodeName
     * @return Complete End XML node
     */
    public static String getEndNodeStr(String nodeName) {
        StringBuilder builder = new StringBuilder();
        builder.append(NODE_START_FLAG).append(NODE_END_SLASH).append(nodeName).append(NODE_END_FLAG);
        return builder.toString();
    }

    /**
     * Gets an XML node of complete information , including all child nodes
     * If the XML node syntax is incorrect , it will throw an exception IllegalArgumentException
     * 
     * @author
     * @param nodeName nodeName
     * @param fromIndex Find the starting position
     * @param fullStr The total amount being sought XML string
     * @param result search result
     * @return End position query to the XML node
     */
    public static int getNodeFullInfo(String nodeName, int fromIndex, String fullStr, StringBuilder result) {
        if(null == fullStr) {
            throw new IllegalArgumentException("fullStr is null .");
        }

        String nodeStartStr = getNodeStartStr(nodeName);
        int startIndex = fullStr.indexOf(nodeStartStr, fromIndex);
        if(startIndex < 0) {
            return fromIndex;
        }

        int endIndex = fullStr.indexOf(NODE_END_FLAG, startIndex);
        if(endIndex < 0) {
            throw new IllegalArgumentException("There is no end flag, nodeName=" + nodeName);
        }

        // If the node is empty , such as<active message-id="3"
        // xmlns="urn:ietf:params:xml:ns:netconf:base:1.0"/>
        if(NODE_END_SLASH == fullStr.charAt(endIndex - 1)) {
            return subString(startIndex, NODE_END_FLAG, fullStr, result);
        }

        return subString(startIndex, getEndNodeStr(nodeName), fullStr, result);
    }

    /**
     * Depending on the starting string node name , node assembly , such as the <rpc-reply>
     * 
     * @author
     * @param nodeName nodeName
     * @return String starting node
     */
    public static String getNodeStartStr(String nodeName) {
        return NODE_START_FLAG + nodeName;
    }

    /**
     * SSHTransportFactory.java
     * If you do not meet the XML structure , thrown IllegalArgumentException
     * 
     * @author
     * @param nodeName nodeName
     * @param fullStr The total amount being queried XML string
     * @param result Storing information originating node
     * @return End position originating node
     */
    public static int getStartNodeInfo(String nodeName, String fullStr, StringBuilder result) {
        if(null == fullStr) {
            throw new IllegalArgumentException("fullStr is null.");
        }

        int startIndex = fullStr.indexOf(getNodeStartStr(nodeName));
        if(startIndex < 0) {
            throw new IllegalArgumentException("can't find start node:" + nodeName);
        }

        return subString(startIndex, NODE_END_FLAG, fullStr, result);
    }

    /**
     * According to the name of the node , generating the starting node , e.g.<rpc-reply>.<br>
     * 
     * @author
     * @param nodeName NodeName
     * @return Start node
     */
    public static String getStartNodeStr(String nodeName) {
        return NODE_START_FLAG + nodeName + NODE_END_FLAG;
    }

    /**
     * Get the XML SAXReader
     * 
     * @author
     * @return SAXReader XML is SAXReader
     */
    public static SAXReader newSAXReader() {
        SAXReader reader = new SAXReader();
        reader.setEntityResolver(new EntityResolver() {

            /**
             * Implementation
             * <br>
             * 
             * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
             * @author
             */
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                throw new IOException("Not accept external or external dtd.");
            }
        });

        return reader;
    }

    /**
     * Create a new instance of the factory.<br>
     * 
     * @author
     * @return new instance of the factory
     */
    public static XMLInputFactory newXMLInputFactory() {
        XMLInputFactory instance = XMLInputFactory.newInstance();
        instance.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        instance.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        return instance;
    }

    /**
     * According to the end of the character , the interception of the string ( containing the end
     * character )
     * If you can not find the end of the characters, Throws IllegalArgumentException
     * 
     * @author
     * @param startIndex Starting position taken
     * @param endCh End character
     * @param fullStr The total amount intercepted string
     * @param result Results interception storage containers
     * @return End end position character
     */
    private static int subString(int startIndex, char endCh, String fullStr, StringBuilder result) {
        int endIndex = fullStr.indexOf(endCh, startIndex);
        if(endIndex < 0) {
            throw new IllegalArgumentException("can't find endCh:" + endCh);
        }

        result.append(fullStr.substring(startIndex, endIndex + 1));
        return endIndex + 1;
    }

    /**
     * According to the end of the string , the interception of the string ( containing the end of
     * the string )
     * If you can not find the end of the string, Throws IllegalArgumentException
     * 
     * @author
     * @param startIndex Interception starting position
     * @param endStr End of the string
     * @param fullStr String intercepted full amount
     * @param result Results interception storage containers
     * @return End end of the string
     */
    private static int subString(int startIndex, String endStr, String fullStr, StringBuilder result) {
        int endIndex = fullStr.indexOf(endStr, startIndex);
        if(endIndex < 0) {
            throw new IllegalArgumentException("can't find endStr:" + endStr);
        }

        result.append(fullStr.substring(startIndex, endIndex + endStr.length()));
        return endIndex + endStr.length();
    }

}
