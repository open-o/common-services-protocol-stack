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

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.BadMessageFormatException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.util.VTDNavAdapter;

/**
 * Returns an error message description
 * <p>
 * detailed comment
 * 
 * @author
 * @see
 * @since 1.0
 */
public class RpcErrorInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcErrorInfo.class);

    /**
     * error code
     */
    private String errAppTag;

    /**
     * Error Message Details
     */
    private String errInfo;

    /**
     * Error Message
     */
    private String errMsg;

    /**
     * Dynamic parameters
     */
    private Map<Integer, String> errParas;

    /**
     * Error Severity Level
     */
    private String errSeverity;

    /**
     * error-tag
     */
    private String errTag;

    /**
     * Error type
     */
    private String errType;

    /**
     * 
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since  GSO 0.5
     */
    public RpcErrorInfo() {
        super();
    }

    /**
     * Constructor
     * 
     * @author
     * @param errAppTag
     * @param errInfo
     * @param errMsg
     * @param errSeverity
     * @param errTag
     * @param errType
     * @throws NetconfException
     */
    public RpcErrorInfo(String errAppTag, String errInfo, String errMsg, String errSeverity, String errTag,
            String errType) throws NetconfException {
        super();
        this.errAppTag = errAppTag;
        this.errInfo = errInfo;
        this.errMsg = errMsg;
        this.errSeverity = errSeverity;
        this.errTag = errTag;
        this.errType = errType;
    }

    /**
     * Constructor
     * 
     * @author
     * @param reader
     * @throws BadMessageFormatException
     */
    public RpcErrorInfo(XMLStreamReader reader) throws BadMessageFormatException {
        try {
            if(RpcMessageConstant.ELE_RPC_ERROR.equals(reader.getLocalName())) {
                while(reader.hasNext()) {
                    int event = reader.next();
                    // If this is the beginning of the element
                    if(event == XMLStreamConstants.START_ELEMENT) {
                        parseElement(reader);
                    } else if(event == XMLStreamConstants.END_ELEMENT) {
                        // </rpc-error>On behalf of the end.
                        StringBuffer tmp = new StringBuffer();
                        tmp.append("<error-info>");
                        tmp.append(this.errInfo);
                        tmp.append("</error-info>");

                        XMLStreamReader errReader = VTDNavAdapter.createStAXFactory(tmp.toString());
                        parseErrorParas(errReader);
                        return;
                    }
                }
            } else {
                throw new BadMessageFormatException("expected <rpc-error> but actual is " + reader.getLocalName(),
                        null);
            }
        } catch(XMLStreamException e) {
            throw new BadMessageFormatException("rpc-error is not illegal.", e);
        }
    }

    /**
     * Error code is returned
     * 
     * @author
     * @return error code
     */
    public String getErrAppTag() {
        return this.errAppTag;
    }

    /**
     * Returns Error Message Detail
     * 
     * @author
     * @returnError Message Details
     */
    public String getErrInfo() {
        return this.errInfo;
    }

    /**
     * Returns an error message
     * 
     * @author
     * @return Error Message
     */
    public String getErrMsg() {
        return this.errMsg;
    }

    /**
     * Returns dynamic parameters
     * 
     * @author
     * @return Dynamic parameter table
     */
    public Map<Integer, String> getErrParas() {
        return this.errParas;
    }

    /**
     * You did not find the corresponding attribute in the error message to be
     * clear.
     */
    @Deprecated
    public String getErrPath() {
        return "";
    }

    /**
     * Return an error level
     * 
     * @author
     * @return Error Level
     */
    public String getErrSeverity() {
        return this.errSeverity;
    }

    /**
     * error-tag
     * 
     * @author
     * @return error-tag
     */
    public String getErrTag() {
        return this.errTag;
    }

    /**
     * Returns the type of error
     * 
     * @author
     * @return Error type
     */
    public String getErrType() {
        return this.errType;
    }

    /**
     * Setting error code
     * 
     * @author
     * @param errAppTag
     *            error code
     */
    public void setErrAppTag(String errAppTag) {
        this.errAppTag = errAppTag;
    }

    /**
     * Setting Error Message Detail
     * 
     * @author
     * @param errInfo
     */
    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    /**
     * Setting error message
     * 
     * @author
     * @param errMsg
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    /**
     * Dynamic parameter setting error message
     * 
     * @author
     * @param errInfo
     */
    public void setErrParas(Map<Integer, String> errParas) {
        this.errParas = errParas;
    }

    /**
     * Setting the error level
     * 
     * @author
     * @param errSeverity
     */
    public void setErrSeverity(String errSeverity) {
        this.errSeverity = errSeverity;
    }

    /**
     * error-tag <br>
     * 
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     * @param errTag
     *            error-tag
     */
    public void setErrTag(String errTag) {
        this.errTag = errTag;
    }

    /**
     * Setting the Error type
     * 
     * @author
     * @param errTyp
     */
    public void setErrType(String errType) {
        this.errType = errType;
    }

    /**
     * XML Element <br>
     * 
     * @author
     * @param reader
     * @throws XMLStreamException
     */
    private void parseElement(XMLStreamReader reader) throws XMLStreamException {
        String name = reader.getLocalName();
        if(RpcMessageConstant.ELE_ERROR_TYPE.equals(name)) {
            this.errType = VTDNavAdapter.getElementText(reader, RpcMessageConstant.ELE_ERROR_TYPE);
        } else if(RpcMessageConstant.ELE_ERROR_TAG.equals(name)) {
            this.errTag = VTDNavAdapter.getElementText(reader, RpcMessageConstant.ELE_ERROR_TAG);
        } else if(RpcMessageConstant.ELE_ERROR_SEVERITY.equals(name)) {
            this.errSeverity = VTDNavAdapter.getElementText(reader, RpcMessageConstant.ELE_ERROR_SEVERITY);
        } else if(RpcMessageConstant.ELE_ERROR_APP_TAG.equals(name)) {
            this.errAppTag = VTDNavAdapter.getElementText(reader, RpcMessageConstant.ELE_ERROR_APP_TAG);
        } else if(RpcMessageConstant.ELE_ERROR_MESSAGE.equals(name)) {
            this.errMsg = VTDNavAdapter.getElementText(reader, RpcMessageConstant.ELE_ERROR_MESSAGE);
        } else if(RpcMessageConstant.ELE_ERROR_INFO.equals(name)) {
            this.errInfo = VTDNavAdapter.getElementText(reader, RpcMessageConstant.ELE_ERROR_INFO);
        } else {
            LOGGER.debug("do Nothing");
        }
    }

    /**
     * error-info
     * 
     * @author
     * @param reader
     * @throws XMLStreamException
     */
    private void parseErrorParas(XMLStreamReader reader) throws XMLStreamException {
        this.errParas = new HashMap<Integer, String>();

        Integer count = 0;
        int eventType = reader.next();

        while(reader.hasNext()) {
            if(eventType == XMLStreamConstants.START_ELEMENT) {
                String name = reader.getLocalName();
                if(RpcMessageConstant.ELE_ERROR_PARA.equals(name)) {
                    count++;
                    String tmp = VTDNavAdapter.getElementText(reader, RpcMessageConstant.ELE_ERROR_PARA);
                    this.errParas.put(count, tmp);
                }
            }

            eventType = reader.next();
        }
    }
}
