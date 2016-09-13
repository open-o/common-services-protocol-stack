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

package org.openo.commsvc.protocolstack.common.constant;

/**
 * Constants used in sending JSON requests<br>
 * 
 * @author
 * @version GSO 0.5 Sep 7, 2016
 */
public class RequestJsonConstants {

    public static final String SERVICE_JOB = "service_job";

    public static final String STATUS = "status";

    public static final String PROGRESS = "progress";

    public static final String RESULT = "result";

    public static final String RESULT_REASON = "result_reason";

    public static final String ACTION = "action";

    public static final String FINISHED = "finished";

    public static final String DATA = "data";

    /**
     * Constants used for service jobs and supervision<br>
     * 
     * @author
     * @version GSO 0.5 Sep 7, 2016
     */
    public static class ServiceJob {

        public static final String JOBID = "job_id";

        public static final String SERVICE = "service";

        public static final String SERVICELIST = "services";

        public static final String SRVVERSION = "srv_version";

        public static final String CREATEDTIME = "created_at";

        public static final String COMPLETEDTIME = "completed_at";

        private ServiceJob() {
            // hide the constructor
        }
    }

    /**
     * Constants used for service<br>
     *
     * @author
     * @version GSO 0.5 Sep 7, 2016
     */
    public static class Service {

        public static final String SERVICEID = "service_id";

        public static final String SERVICENAME = "name";

        public static final String ISDRYRUN = "is_dryrun";

        public static final String ONFAILURE = "onfailure";

        public static final String RESOURCES = "resources";

        public static final String SUBSERVICES = "subservices";

        public static final String RESSTATUSLIST = "res_status";

        private Service() {
            // hide the constructor
        }
    }

    /**
     * Constants used for Resource<br>
     * 
     * @author
     * @version GSO 0.5 Sep 7, 2016
     */
    public static class Resource {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String TYPE = "type";

        public static final String PROPERTIES = "properties";

        public static final String DEPENDON = "depends";

        private Resource() {
            // hide the constructor
        }
    }

    /**
     * Constants used for result<br>
     * 
     * @author
     * @version GSO 0.5 Sep 7, 2016
     */
    public static class Result {

        public static final String ERRORCODE = "code";

        public static final String REASON = "reason";

        private Result() {
            // hide the constructor
        }
    }

    /**
     * Constants for Exception types <br>
     *
     * @author
     * @version GSO 0.5 Sep 7, 2016
     */
    public static class ExceptionJson {

        public static final String EXCEPTIONID = "exceptionId";

        public static final String EXCEPTIONTYPE = "exceptionType";

        private ExceptionJson() {
            // hide the constructor
        }
    }

    private RequestJsonConstants() {
        // hide the constructor
    }
}
