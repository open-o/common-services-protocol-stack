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

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.BadMessageFormatException;

/**
 * NetconfAbility netconf ability <br>
 * 
 * @author
 */
public class NetconfAbility {

    public static final int ACTION = 0x4000;

    public static final int ACTIVE = 0x8000;

    public static final int BASE = 1;

    public static final int CANDIDATE = 0x4;

    /**
     * Two-phase commit protocol description tag capability
     */
    public static final int COMMIT_DESCRIPTION = 0x40000;

    public static final int COMPARE = 0x200;

    public static final int CONFIRMED_COMMIT = 0x8;

    public static final int DISCARD_COMMIT = 0x10000;

    public static final int EXCHANGE = 0x1000;

    public static final int EXECUTE_CLI = 0x800;

    /**
     * Discover large amount of data , the ability to get the next packet label
     */
    public static final int GET_NEXT = 0x80000;

    public static final int PREVIEW = 0x20000;

    public static final int ROLLBACK = 0x400;

    public static final int ROLLBACK_ON_ERROR = 0x10;

    public static final int STARTUP = 0x40;

    public static final int SYNC = 0x2000;

    /**
     * Device sync return the whole amount of new compressed file format
     * handling capacity
     */
    public static final int SYNC11 = 0x200000;

    /**
     * Device sync return the whole amount of support queries, FTP control
     */
    public static final int SYNC12 = 0x400000;

    /**
     * When CDB submitted RDB, data inconsistencies, the ability to
     * automatically update the CDB
     */
    public static final int UPDATE = 0x100000;

    public static final int URL = 0x80;

    public static final int VALIDATE = 0x20;

    public static final int WRITABLE_RUNNING = BASE << 1;

    public static final int XPATH = 0x100;

    private static final List<String> abilityItem = new ArrayList<String>();

    private static final String CLIENT_ABILITY_HELLO;

    private int deviceAbilityVolumn;

    static {
        abilityItem.add("urn:ietf:params:netconf:base:1.0");
        abilityItem.add("urn:ietf:params:netconf:capability:writable-running:1.0");
        abilityItem.add("urn:ietf:params:netconf:capability:candidate:1.0");
        abilityItem.add("urn:ietf:params:netconf:capability:confirmed-commit:1.0");
        abilityItem.add("urn:ietf:params:netconf:capability:rollback-on-error:1.0");
        abilityItem.add("urn:ietf:params:netconf:capability:validate:1.0");
        abilityItem.add("urn:ietf:params:netconf:capability:startup:1.0");
        abilityItem.add("urn:ietf:params:netconf:capability:url:1.0?protocol=ftp");
        abilityItem.add("urn:ietf:params:netconf:capability:xpath:1.0");

        String beginTag = "<capability>";
        String endTag = "</capability>";
        StringBuffer buf = new StringBuffer(CommonConstant.DEFAULT_STRING_LENGTH_128);
        buf.append("<capabilities>");
        for(String item : abilityItem) {
            buf.append(beginTag);
            buf.append(item);
            buf.append(endTag);
        }
        buf.append("</capabilities>");
        CLIENT_ABILITY_HELLO = buf.toString();
    }

    /**
     * 
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @param reader
     * @throws BadMessageFormatException
     * @since  GSO 0.5
     */
    public NetconfAbility(XMLStreamReader reader) throws BadMessageFormatException {
        try {
            while(reader.hasNext()) {
                int event = reader.next();
                if(event == XMLStreamConstants.START_ELEMENT) {
                    if("capability".equals(reader.getLocalName())) {
                        String ability = reader.getElementText();
                        int index = abilityItem.indexOf(ability.trim());
                        if(index >= 0) {
                            this.deviceAbilityVolumn |= (1 << index);
                        }
                    }
                } else if(event == XMLStreamConstants.END_ELEMENT && "capabilities".equals(reader.getLocalName())) {
                    // </capabilities>End
                    return;
                }
            }
        } catch(XMLStreamException e) {
            throw new BadMessageFormatException("capabilities is not illegal.", e);
        }
    }

    public static String getClientabilityhello() {
        return CLIENT_ABILITY_HELLO;
    }

    public int getDeviceAbilityVolumn() {
        return this.deviceAbilityVolumn;
    }

}
