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

package org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.HWContext;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IOperation;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpc;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.IRpcReply;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.OperationArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.ReqCmd;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.RpcArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.TargetType;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.UnLockArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netconf client <br>
 * 
 * @author
 */
public abstract class AbstractNetconfClient implements INetconfClient {

    /**
     * Netconf Session pool
     */
    @SuppressWarnings("rawtypes")
    private ISessionPool sessionPool;

    /**
     * app name
     */
    private String appName;

    /**
     * thread name
     */
    private String createThreadName;

    /**
     * Async handler
     */
    private IAsyncRpcHandler handler;

    /**
     * lock target
     */
    private Set<TargetType> lockTarget;

    /**
     * logger class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNetconfClient.class);

    /**
     * LRID
     */
    private Integer lrID;

    /**
     * controller info
     */
    private NetconfAccessInfo netconfAccessInfo;

    /**
     * Operation arguments for commit and roll back
     */
    private List<OperationArg> preOperArgs = new ArrayList<OperationArg>();

    /**
     * NETCONF session
     */
    private ISession session;

    /**
     * netconf request timeout
     */
    private long timeout;

    /**
     * VRID
     */
    private int vrID;

    /**
     * AbstractNetconfClient<br>
     * <br>
     * 
     * @author
     * @param netconfAccessInfo
     *            Netconf information
     * @param sessionPool
     *            Netconf session pool
     * @param appName
     *            app name
     * @param createThreadName
     *            thread name
     */
    @SuppressWarnings("rawtypes")
    protected AbstractNetconfClient(NetconfAccessInfo netconfAccessInfo, ISessionPool sessionPool, String appName,
            String createThreadName) {
        this.netconfAccessInfo = netconfAccessInfo;
        this.sessionPool = sessionPool;
        this.appName = appName;
        this.createThreadName = createThreadName;
    }

    /**
     * close the netconf client<br>
     * <br>
     * 
     * @see org.openo.commsvc.connector.netconf.service.svc.protocol.service.INetconfClient#close()
     * @author
     */
    @Override
    public void close() {
        if(null == this.session) {
            LOGGER.info("close netconf client, session has been cleaned.");
            return;
        }

        closeBefore();
        LOGGER.info("release netconf session created by ({})", this.createThreadName);
        releaseSession(this.session);

        // remove session
        this.session = null;
        clearPreOperArgs();
    }

    /**
     * clear operational args<br>
     * 
     * @author
     */
    protected void clearPreOperArgs() {
        this.preOperArgs.clear();
    }

    public IAsyncRpcHandler getHandler() {
        return this.handler;
    }

    public Integer getLrID() {
        return this.lrID;
    }

    /**
     * @return Returns the session.
     */
    protected ISession getSession() {
        return this.session;
    }

    /**
     * @return Returns the sessionPool.
     */
    @SuppressWarnings("rawtypes")
    public ISessionPool getSessionPool() {
        return this.sessionPool;
    }

    public int getVrID() {
        return this.vrID;
    }

    public void setHandler(IAsyncRpcHandler handler) {
        this.handler = handler;
    }

    public void setLrID(Integer lrID) {
        this.lrID = lrID;
    }

    /**
     * Convert to string<br>
     * <br>
     * 
     * @see java.lang.Object#toString()
     * @author
     */
    @Override
    public String toString() {
        return "AbstractNetconfClient [netconfAccessInfo=" + this.netconfAccessInfo + "]";
    }

    /**
     * unlock the target tyep<br>
     * 
     * @param type - target type
     * @return IPC replay
     * @throws NetconfException - when input is invalid or null
     * @since GSO 0.5
     */
    public IRpcReply unlock(TargetType type) throws NetconfException {
        if(type == null) {
            throw new IllegalArgumentException("unlock not allow null TargetType");
        }

        UnLockArg arg = new UnLockArg(type);
        IRpcReply reply = send(false, null, arg);

        if(reply == null) {
            throw new IllegalArgumentException("failed to send unlock request");
        }

        if(reply.isOK()) {
            this.lockTarget.remove(type);
            if(this.lockTarget.isEmpty()) {
                this.lockTarget = null;
            }
        }

        return reply;
    }

    /**
     * clear the lock <br>
     * 
     * @author
     * @see DefaultNetconfClient#closeBefore
     */
    protected void cleanUpLock() {
        if(this.lockTarget == null) {
            return;
        }

        LOGGER.info("client doesn't unlock before close, try to fix it.");
        try {
            for(TargetType target : this.lockTarget) {
                IRpcReply reply = unlock(target);
                if(!reply.isOK()) {
                    LOGGER.error("can't unlock:{}", reply.getErrorInfos());
                } else {
                    LOGGER.info("unlock success.");
                }
            }
        } catch(NetconfException e) {
            LOGGER.info("try to clean up before close exception.", e);
        }
    }

    public void setLockTarget(Set<TargetType> lockTarget) {
        this.lockTarget = lockTarget;
    }

    /**
     * close before <br>
     * 
     * @author
     */
    protected void closeBefore() {
        cleanUpLock();
    }

    /**
     * @return Returns the appName.
     */
    protected String getAppName() {
        return this.appName;
    }

    /**
     * @return Returns the createThreadName.
     */
    protected String getCreateThreadName() {
        return this.createThreadName;
    }

    /**
     * @return Returns the LOGGER.
     */
    protected Logger getLogger() {
        return LOGGER;
    }

    /**
     * return Netconf Session based on controller info <br>
     * 
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     * @para netconfAccessInfo netconf information
     * @return Netconf session
     * @throws NetconfException
     *             exception
     */
    @SuppressWarnings("unchecked")
    protected ISession getSession(NetconfAccessInfo netconfAccessInfo) throws NetconfException {
        return getSessionPool().getSession(netconfAccessInfo, getCreateThreadName());
    }

    /**
     * look up the netconf access information <br>
     * 
     * @author
     * @param netconfAccessInfoKey
     *            Netconf info key
     * @return NetconfAccessInfo
     * @throws NetconfException
     */
    protected abstract NetconfAccessInfo lookupNetconfAccessInfo(NetconfAccessInfo netconfAccessInfoKey)
            throws NetconfException;

    /**
     * release session <br>
     * 
     * @author
     * @param session
     *            Netconf session
     */
    protected void releaseSession(ISession session) {
        getSessionPool().releaseSession(session);
    }

    /**
     * test netconf connectivity <br>
     * 
     * @author
     * @since CloudOpera Orchesator V100R001C00, 2016-2-1
     * @param netconfAccessInfo
     *            netconf info
     * @return 0:success
     */
    @SuppressWarnings("unchecked")
    protected int testConnective(NetconfAccessInfo netconfAccessInfo) {
        return getSessionPool().testConnective(netconfAccessInfo);
    }

    /**
     * Get NETCONF session<br>
     * 
     * @author
     * @return NETCONF session
     * @throws NetconfException
     */
    private ISession getAvailableSession() throws NetconfException {
        if(null == this.session) {
            this.netconfAccessInfo = lookupNetconfAccessInfo(this.netconfAccessInfo);
            this.session = getSession(this.netconfAccessInfo);
        } else if(!this.session.isConnected()) {
            // disconnect session
            LOGGER.info("session has been disconnected. session={}", this.session);
            getSessionPool().releaseSession(this.session);
            this.netconfAccessInfo = lookupNetconfAccessInfo(this.netconfAccessInfo);
            this.session = getSession(this.netconfAccessInfo);
        }

        return this.session;
    }

    /**
     * Send netconf request <br>
     * 
     * @author
     * @param enableNext
     * @param rpcArgList
     * @param operArg
     * @return
     * @throws NetconfException
     */
    @Override
    public IRpcReply send(boolean enableNext, List<RpcArg> rpcArgList, IOperation operArg) throws NetconfException {
        List<RpcArg> rpcArgumentList = null;
        if((this.lrID != null) || (this.vrID != 0)) {
            if(rpcArgList == null) {
                rpcArgumentList = new ArrayList<RpcArg>();
            } else {
                rpcArgumentList = rpcArgList;
            }
            rpcArgumentList.add(new HWContext(this.lrID, this.vrID));
        }

        ReqCmd cmd = new ReqCmd(rpcArgumentList, operArg, enableNext);
        cmd.setTimeout(this.timeout);
        return send(cmd);
    }

    /**
     * Send <br>
     * 
     * @author
     * @param reqCmd
     * @return
     * @throws NetconfException
     */
    protected IRpcReply send(IRpc reqCmd) throws NetconfException {
        ISession sess = getAvailableSession();

        return sess.synSend(reqCmd);
    }

    /**
     * Set the session to null <br>
     * 
     * @author
     */
    protected final void setSessionNull() {
        this.session = null;
    }

}
