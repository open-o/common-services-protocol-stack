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
 * Netconf initialization Information Management
 * 
 * @author
 * @see
 * @since 1.0
 */
public class Netconf {

    /**
     * Number of seconds to milliseconds of difference
     */
    private static final int BASE = 1000;

    /**
     * The default device-level thread pool survival times
     */
    private static final int DEFAULT_DEV_SESSION_POOL_KEEP_ALIVE_TIMES = 30;

    /**
     * According to this recent N access time to cache
     */
    private static int cachePolicyLastAccessCounter = 10;

    /**
     * Get the default session timeout (s)
     */
    private static int defaultLoginTimeout = 30 * BASE;

    /**
     * The default time-out (s)
     */
    private static int defaultResponseTimeout = 60 * BASE;

    /**
     * Whether to use the connection pool
     */
    private static int enableSessionPool = 0;

    /**
     * Maximum number of connections per device
     */
    private static int maxConnectionPerDevice = 5;

    /**
     * The maximum number of connections the cache
     */
    private static int maxHoldConnections = 600;

    /**
     * The maximum connection idle time
     */
    private static int maxIdelTimeout = 1200;

    /**
     * Thread pool thread maximum idle time, more than this time a thread will
     * be closed.
     */
    private static int maxThreadIdleTimeout = 60 * BASE;

    /**
     * Minimum number of connections per device
     */
    private static int minConnectionPerDevice = 1;

    /**
     * Survival number of device-level thread pool, clean up the fastest time,
     * unit: minutes
     */
    private static int devSessionPoolKeepAliveTimes = DEFAULT_DEV_SESSION_POOL_KEEP_ALIVE_TIMES;
    
    private Netconf() {
        throw new IllegalAccessError("trying to create the instance of utility class.");
    }

    /**
     * @return Returns the devSessionPoolKeepAliveTimes.
     */
    public static int getDevSessionPoolKeepAliveTimes() {
        return devSessionPoolKeepAliveTimes;
    }

    public static int getCachePolicyLastAccessCounter() {
        return cachePolicyLastAccessCounter;
    }

    /**
     * @return Returns the defaultLoginTimeout.
     */
    public static int getDefaultLoginTimeout() {
        return defaultLoginTimeout;
    }

    /**
     * @return Returns the defaultResponseTimeout.
     */
    public static int getDefaultResponseTimeout() {
        return defaultResponseTimeout;
    }

    /**
     * @return Returns the maxConnectionPerDevice.
     */
    public static int getMaxConnectionPerDevice() {
        return maxConnectionPerDevice;
    }

    /**
     * @return Returns the maxHoldConnections.
     */
    public static int getMaxHoldConnections() {
        return maxHoldConnections;
    }

    /**
     * @return Returns the maxIdelTimeout.
     */
    public static int getMaxIdelTimeout() {
        return maxIdelTimeout;
    }

    /**
     * @return Returns the maxThreadIdleTimeout.
     */
    public static int getMaxThreadIdleTimeout() {
        return maxThreadIdleTimeout;
    }

    /**
     * @return Returns the minConnectionPerDevice.
     */
    public static int getMinConnectionPerDevice() {
        return minConnectionPerDevice;
    }

    public static boolean isEnableSessionPool() {
        return enableSessionPool != 0;
    }

    /**
     * Get the session priority
     * 
     * @author
     */
    public static enum SESSION_PRIORITY {
        HIGH, LOW
    }
}
