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

package org.openo.commsvc.protocolstack.netconf.service.svc.business.impl;

import java.util.concurrent.Executors;

import org.apache.commons.lang.ArrayUtils;
import org.openo.commsvc.protocolstack.common.business.ConnStatusMgr;
import org.openo.commsvc.protocolstack.common.model.ControllerStatus;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.datadefination.CommunicateArg;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.error.NetconfErrCode;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.CommunicationArgumentMgr;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.service.NetconfAccessInfo;
import org.openo.commsvc.protocolstack.netconf.service.svc.protocol.session.CacheSessionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConnStatusDetector responsible for detecting the connection
 * status between controller and netconf client.
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version     GSO 0.5  Sep 7, 2016
 */
public class ConnStatusDetector {

    /**
     * Log object
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnParamsMgr.class);

    /**
     * State detection interval: 1 minute
     */
    private static final int DETECT_SPACE = 60 * 1000;

    /**
     * Caching session address pool
     */
    private CacheSessionPool<NetconfAccessInfo> cacheSessionPool;

    /**
     * State detection thread
     */
    private DetectThread detectThread;

    /**
     * Detect connection state
     * 
     * @author
     * @see
     */
    public void detectConnStatus() {
        detectThread = new DetectThread(this);
        Executors.newSingleThreadExecutor().submit(detectThread);
    }

    /**
     * Stop Detection
     * 
     * @author
     * @see
     */
    public void stopDetecting() {
        detectThread.stopThread();
    }

    public void setCacheSessionPool(CacheSessionPool<NetconfAccessInfo> cacheSessionPool) {
        this.cacheSessionPool = cacheSessionPool;
    }

    /**
     * Description: [State detection thread] Copyright: Copyright (c) 1988-2015
     * Company: Huawei Tech. Co., Ltd
     * 
     * @author
     */
    private static class DetectThread implements Runnable {

        /**
         * State detecting objects
         */
        private ConnStatusDetector detector;

        /**
         * Run flag
         */
        private boolean runningFlag = true;

        /**
         * Thread-locking object
         */
        private final Object lockObject = new Object();

        /**
         * Constructor <br>
         * 
         * @author
         * @see
         * @param detector
         *            State detecting objects
         */
        public DetectThread(ConnStatusDetector detector) {
            this.detector = detector;
        }

        /**
         * Stop Thread
         * 
         * @author
         * @see
         */
        public void stopThread() {
            runningFlag = false;
        }

        /**
         * Covering methods / implementations (choose one)
         *
         * @see java.lang.Runnable#run()
         * @author
         * @see
         */
        @Override
        public void run() {
            LOGGER.warn("start to detect conn status");

            // Monitoring controller status, where you need to capture
            // Exception, ensure that threads do not abort
            while(runningFlag) {
                try {
                    detectStatus();
                } catch(Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }

                try {
                    synchronized(lockObject) {
                        lockObject.wait(DETECT_SPACE);
                    }
                } catch(InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }

        /**
         * Detect connection state
         * 
         * @author
         * @see
         */
        private void detectStatus() {
            LOGGER.warn("detect status!");
            // First to see if the connection parameter is empty
            CommunicateArg[] commArgs = CommunicationArgumentMgr.getAllCommunicateArg();
            if(ArrayUtils.isEmpty(commArgs)) {
                LOGGER.warn("communication args is empty");
                return;
            }

            // State traversal connection parameters, monitoring each controller
            for(CommunicateArg arg : commArgs) {
                NetconfAccessInfo netconfAccessInfo = new NetconfAccessInfo(arg);
                int status = detector.cacheSessionPool.testConnective(netconfAccessInfo);
                if(NetconfErrCode.SUCCESS == status) {
                    ConnStatusMgr.getInstance().updateConnStatus(arg.getControllerId(), ControllerStatus.NORMAL);
                } else {
                    ConnStatusMgr.getInstance().updateConnStatus(arg.getControllerId(), ControllerStatus.ABNORMAL);
                }
            }
        }
    }
}
