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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RPC versus RPC-REPLY in hwcontext Property analysis tools.
 * 
 * @author
 * @see
 */
public class HWContextUtil {

    /**
     * * Default VRID (VS), the main VR ID
     */
    public static final int DEFAULT_VR_ID = 0;

    /**
     * The default LRID (LS)
     */
    public static final int DEFAULT_LR_ID = 0;

    public static final String HWCONTEXT_ATTR = "hwcontext";

    public static final String LR = "ls";

    public static final String VR = "vs";

    private static final Pattern PATTERN = Pattern.compile("(?:ls=(\\d+))?(?:vs=(\\d+))?");
    
    private HWContextUtil() {
        throw new IllegalAccessError("trying to create object of utility class");
    }

    /**
     * Converted into hwcontext property values ​​according to lr and vr values.
     * 
     * @author
     * @see
     * @param lrID
     * @param vrID
     * @return
     */
    public static String contextValue(Integer lrID, Integer vrID) {
        StringBuffer buf = new StringBuffer();
        if(lrID != null) {
            buf.append(LR);
            buf.append('=');
            buf.append(lrID.intValue());
        }

        if(vrID != null) {
            buf.append(VR);
            buf.append('=');
            buf.append(vrID.intValue());
        }

        return buf.toString();
    }

    /**
     * Lr and extract value from vr hwcontext property. <br>
     * 
     * @author
     * @see
     * @param context
     * @return {LR=\d | NULL, VR=\d | NULL}
     */
    public static Map<String, Integer> lrvrValue(String context) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        if(context == null) {
            return map;
        }

        final int lrGroup = 1;
        final int vrGroup = 2;
        Matcher matcher = PATTERN.matcher(context);
        if(matcher.matches()) {
            String lr = matcher.group(lrGroup);
            if(lr != null) {
                map.put(LR, Integer.parseInt(lr));
            }

            String vr = matcher.group(vrGroup);
            if(vr != null) {
                map.put(VR, Integer.parseInt(vr));
            }
        }
        return map;
    }
}
