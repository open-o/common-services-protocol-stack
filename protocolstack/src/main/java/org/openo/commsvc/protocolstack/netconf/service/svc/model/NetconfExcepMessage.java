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

package org.openo.commsvc.protocolstack.netconf.service.svc.model;

import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;

/**
 * NetconfExcepMessage netconf exception
 * 
 * @author
 */
public class NetconfExcepMessage {

    /**
     * exceptionId
     */
    private String exceptionId;

    /**
     * Exception Type
     */
    private String exceptionType;

    /**
     * descArgs
     */
    private String[] descArgs;

    /**
     * reasonArgs
     */
    private String[] reasonArgs;

    /**
     * detailArgs
     */
    private String[] detailArgs;

    /**
     * adviceArgs
     */
    private String[] adviceArgs;

    /**
     * Constructor
     * 
     * @author
     * @see
     * @param e Service Exception
     */
    public NetconfExcepMessage(ServiceException e) {
        // Null check
        if(null == e) {
            return;
        }

        exceptionId = e.getId();
        exceptionType = "ROA_EXFRAME_EXCEPTION";

        ExceptionArgs args = e.getExceptionArgs();
        if(null == args) {
            return;
        }
        descArgs = args.getDescArgs();
        reasonArgs = args.getReasonArgs();
        detailArgs = args.getDetailArgs();
        adviceArgs = args.getAdviceArgs();
    }

    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String[] getDescArgs() {
        return descArgs;
    }

    public void setDescArgs(String[] descArgs) {
        this.descArgs = descArgs;
    }

    public String[] getReasonArgs() {
        return reasonArgs;
    }

    public void setReasonArgs(String[] reasonArgs) {
        this.reasonArgs = reasonArgs;
    }

    public String[] getDetailArgs() {
        return detailArgs;
    }

    public void setDetailArgs(String[] detailArgs) {
        this.detailArgs = detailArgs;
    }

    public String[] getAdviceArgs() {
        return adviceArgs;
    }

    public void setAdviceArgs(String[] adviceArgs) {
        this.adviceArgs = adviceArgs;
    }
}
