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

/**
 * Netconf constants class
 * 
 * @author
 * @since CloudOpera Orchesator V100R001C00, 2016-2-1
 */
public class NetconfContants {

    /**
     * The default password value is displayed
     */
    public static final String DEFAULT_PWD = "........";

    /**
     * difference incremental synchronization attribute name returned
     */
    public static final String DIFFERENCE_ATTR = "difference";

    /**
     * operation attribute name issued configured
     */
    public static final String OPERATION_ATTR = "operation";

    /**
     * method attribute name issued configured
     */
    public static final String METHOD_ATTR = "method";

    /**
     * 1 second
     */
    public static final int ONE_SECOND = 1000;

    /**
     * The next line, for logging print wrap
     */
    public static final char NEXT_LINE = '\n';

    /**
     * Incremental synchronization difference property value
     * 
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     */
    public static final class DifferenceEnum {

        /**
         * create
         */
        public static final String CREATE = "create";

        /**
         * delete
         */
        public static final String DELETE = "delete";

        /**
         * modify
         */
        public static final String MODIFY = "modify";
        
        private DifferenceEnum() {
            throw new IllegalAccessError("trying to create instance of utility class.");
        }
    }

    /**
     * Issued configuration operation enumeration value
     * 
     * @author
     */
    public static final class OperationEnum {

        /**
         * create
         */
        public static final String CREATE = "create";

        /**
         * create
         */
        public static final String DELETE = "delete";

        /**
         * Merger, there is a modification; does not exist, new
         */
        public static final String MERGE = "merge";

        /**
         * replace
         */
        public static final String REPLACE = "replace";
        
        private OperationEnum() {
            // hiding public constructor
        }
    }
    
    private NetconfContants() {
        // hiding public constructor
    }
}
