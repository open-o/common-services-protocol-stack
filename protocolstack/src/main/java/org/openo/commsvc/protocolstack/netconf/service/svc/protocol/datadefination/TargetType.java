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

import org.openo.commsvc.protocolstack.common.constant.CommonConstant;

/**
 * Host Configuration Set, edit-config command to specify the target
 * configuration set.
 * 
 * @author
 * @see
 */
public enum TargetType {
    RDB(1), CDB(2), STARTUP(3), URL(4), CHECKPOINT(5);

    /**
     * Identification numbers, in order to be consistent with the Cfg DbType.
     */
    private int type;

    private TargetType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    /**
     * 
     * <br>
     * 
     * @param content
     * @param tag
     * @return
     * @since  GSO 0.5
     */
    public String targetCommand(String content, String tag) {
        StringBuilder buf = new StringBuilder(CommonConstant.DEFAULT_STRING_LENGTH_128);
        buf.append('<');
        buf.append(tag);
        buf.append('>');

        if(this == TargetType.RDB) {
            buf.append("<running/>");
        } else if(this == TargetType.CDB) {
            buf.append("<candidate/>");
        } else if(this == TargetType.STARTUP) {
            buf.append("<startup/>");
        } else if(this == TargetType.URL) {
            buf.append("<url>");
            buf.append(content);
            buf.append("</url>");
        } else if(this == TargetType.CHECKPOINT) {
            buf.append("<checkpoint>");
            buf.append(content);
            buf.append("</checkpoint>");
        }

        buf.append("</");
        buf.append(tag);
        buf.append('>');
        return buf.toString();
    }
}
