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

import java.net.MalformedURLException;
import java.net.URL;

import org.openo.commsvc.protocolstack.common.constant.CommonConstant;

/**
 * Sdn controller information
 * 
 * @author
 */
public class ControllerCommInfo {

    String sdnControllerId;

    String name;

    String url;

    String userName;

    String password;

    String version;

    String vendor;

    String description;

    String protocol;

    String productName;

    String type;

    String createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * Get IP Address <br>
     * 
     * @param url - Url of the controller
     * @return IP address as string
     * @since GSO 0.5
     */
    public String getIp(String url) {

        try {
            return new URL(url).getHost();
        } catch(MalformedURLException e) {
            return CommonConstant.DEFAULT_HOST_ADDRESS;
        }
    }

    /**
     * Get port from URL<br>
     * 
     * @param url - Url of the controler
     * @return port
     * @since GSO 0.5
     */
    public int getPort(String url) {

        try {
            return new URL(url).getPort();
        } catch(MalformedURLException e) {
            return CommonConstant.DEFAULT_PORT;
        }
    }

    public String getSdnControllerId() {
        return sdnControllerId;
    }

    public void setSdnControllerId(String sdnControllerId) {
        this.sdnControllerId = sdnControllerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
