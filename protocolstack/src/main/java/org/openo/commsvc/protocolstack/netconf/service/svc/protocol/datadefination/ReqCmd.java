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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.util.HWContextUtil;

/**
 * ReqCmd to netconf server
 * <p>
 * 
 * @author
 */
public class ReqCmd implements IRpc {

    /**
     * Whether the fight by the protocol stack package: true fight by the
     * protocol stack packet; false packet protocol stack is not responsible for
     * the fight, send get-next operation under application
     */
    private boolean enableGetNext;

    /**
     * LR ID
     */
    private Integer lrID;

    /**
     * Operating layer parameters
     */
    private IOperation operationArg;

    /**
     * RPC Parameter List
     */
    private List<RpcArg> rpcArgs;

    /**
     * command timeout
     */
    private long timeout;

    /**
     * Globalization requirements, equipment charset field
     */
    private String charset = CommunicateArg.ENCODING_UTF8;

    /**
     * VR ID
     */
    private Integer vrID;

    /**
     * Constructor
     * 
     * @author
     * @param rpcArgs
     *            rpc parameter
     * @param operationArg
     *            Operating parameters
     * @param enableGetNext
     */
    public ReqCmd(List<RpcArg> rpcArgs, IOperation operationArg, boolean enableGetNext) {
        this.enableGetNext = enableGetNext;
        this.rpcArgs = rpcArgs;
        this.operationArg = operationArg;
        initLrVr();
    }

    /**
     * Implementation
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc#getContent()
     * @author
     */
    @Override
    public String getContent() {
        return this.operationArg.getContent();
    }

    /**
     * Implementation
     * 
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc#getLrID()
     * @author
     */
    @Override
    public Integer getLrID() {
        return this.lrID;
    }

    public IOperation getOperationArg() {
        return this.operationArg;
    }

    public List<RpcArg> getRpcArgList() {
        return this.rpcArgs;
    }

    @Override
    public long getTimeout() {
        return this.timeout;
    }

    /**
     * @see org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc#getVrID()
     * @author
     */
    @Override
    public Integer getVrID() {
        return this.vrID;
    }

    public boolean isEnableGetNext() {
        return this.enableGetNext;
    }

    public void setEnableGetNext(boolean enableGetNext) {
        this.enableGetNext = enableGetNext;
    }

    public void setOperationArg(OperationArg operationArg) {
        this.operationArg = operationArg;
    }

    public void setRpcArgList(List<RpcArg> rpcArgList) {
        this.rpcArgs = rpcArgList;
        initLrVr();
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toCommand(int messageID) throws NetconfException {

        StringBuffer buf = buildCommandHead(messageID);

        if(this.operationArg != null) {
            buf.append(this.operationArg.toCommand());
        }

        buf.append("</rpc>");

        return buf.toString();
    }

    /**
     * Command assembly head
     * 
     * @author
     * @see
     * @param messageID
     * @return Command header
     */
    private StringBuffer buildCommandHead(int messageID) {
        StringBuffer buf = new StringBuffer(CommonConstant.DEFAULT_STRING_LENGTH_128);
        buf.append("<?xml version=\"1.0\" encoding=\"");
        buf.append(this.charset);
        buf.append("\"?>  <rpc message-id=\"");
        buf.append(messageID);
        buf.append('"');

        // Adding RPC Properties
        if(null != this.rpcArgs) {
            Iterator<RpcArg> iter = this.rpcArgs.iterator();
            RpcArg arg = null;
            while(iter.hasNext()) {
                arg = iter.next();
                if(null == arg) {
                    continue;
                }
                String key = arg.getName();
                String value = arg.getValue();
                buf.append(' ');
                buf.append(key);
                buf.append("=\"");
                buf.append(value);
                buf.append('"');
            }
        }
        buf.append(" xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">");
        return buf;
    }

    /**
     * Initialization GrID, VrID <br>
     * 
     * @author
     */
    private void initLrVr() {
        if(null == this.rpcArgs) {
            return;
        }

        for(RpcArg rpcArg : this.rpcArgs) {
            if(null == rpcArg) {
                continue;
            }

            if(HWContextUtil.HWCONTEXT_ATTR.equals(rpcArg.getName())) {
                Map<String, Integer> lrvrValue = HWContextUtil.lrvrValue(rpcArg.getValue());
                this.lrID = lrvrValue.get(HWContextUtil.LR);
                this.vrID = lrvrValue.get(HWContextUtil.VR);
            }
        }
    }

    @Override
    public String getCharset() {
        return this.charset;
    }

    @Override
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
