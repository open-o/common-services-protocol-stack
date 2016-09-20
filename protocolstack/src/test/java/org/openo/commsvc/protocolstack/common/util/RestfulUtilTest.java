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

package org.openo.commsvc.protocolstack.common.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.commsvc.protocolstack.common.constant.CommonConstant;
import org.openo.commsvc.protocolstack.common.constant.CommonConstant.HttpContext;


public class RestfulUtilTest {

    @Test
    public void test() {
        
        Map paramMap = new HashMap();
        paramMap.put(CommonConstant.HttpContext.URL, "/openoapi/protocolstack-netconf/v1/123");
        paramMap.put(HttpContext.METHOD_TYPE, "POST");
        
        String rawdata = "{\"id\":\"1\"}";
        
        Map queryParam = new HashMap<>();
        queryParam.put("value", "123");
        
        RestfulUtil.getRemoteResponse(paramMap, rawdata, queryParam);
    }
    
    @Test
    public void test1() {
        
        Map paramMap = new HashMap();
        paramMap.put(CommonConstant.HttpContext.URL, "/openoapi");
        paramMap.put(HttpContext.METHOD_TYPE, "POST");
        
        String rawdata = "{\"id\":\"1\"}";
        
        Map queryParam = new HashMap<>();
        queryParam.put("value", "123");
        
        assertNull(RestfulUtil.getRemoteResponse(null, rawdata, queryParam));
    }
    
    @Test
    public void test2() {
        
        Map paramMap = new HashMap();
        paramMap.put(CommonConstant.HttpContext.URL, "/openoapi");
        paramMap.put(HttpContext.METHOD_TYPE, "POST");
        
        String rawdata = "{\"id\":\"1\"}";
        
        Map queryParam = new HashMap<>();
        queryParam.put("value", "123");
        
        RestfulUtil.getRemoteResponse(paramMap, null, null);
    }





    
    @Test
    public void test_getRemoteResponse_null_arguemnts() {
        RestfulResponse response = RestfulUtil.getRemoteResponse(null, null, null);
        assertEquals(null, response);
    }
    
    @Test
    public void test_getRemoteResponse() {
        
        Map<String, String> paramsMap = new HashMap<>();
        Map<String, String> queryParam = new HashMap<>();
        String params = "params";
        
        paramsMap.put(CommonConstant.HttpContext.URL, "http://URL.com");
        paramsMap.put(HttpContext.METHOD_TYPE, "method_type");
        
        RestfulResponse response = RestfulUtil.getRemoteResponse(paramsMap, params, queryParam);        
    }
    
    @Test
    public void test_getRemoteResponse_method_get() {
        
        Map<String, String> paramsMap = new HashMap<>();
        Map<String, String> queryParam = new HashMap<>();
        String params = "params";
        
        paramsMap.put(CommonConstant.HttpContext.URL, "http://URL.com");
        paramsMap.put(HttpContext.METHOD_TYPE, "GET");
        
        RestfulResponse response = RestfulUtil.getRemoteResponse(paramsMap, params, queryParam);        
    }
    
    @Test
    public void test_getRemoteResponse_method_post() {
        
        Map<String, String> paramsMap = new HashMap<>();
        Map<String, String> queryParam = new HashMap<>();
        String params = "params";
        
        paramsMap.put(CommonConstant.HttpContext.URL, "http://URL.com");
        paramsMap.put(HttpContext.METHOD_TYPE, "POST");
        
        RestfulResponse response = RestfulUtil.getRemoteResponse(paramsMap, params, queryParam);        
    }
    
    @Test
    public void test_getRemoteResponse_method_put() {
        
        Map<String, String> paramsMap = new HashMap<>();
        Map<String, String> queryParam = new HashMap<>();
        String params = "params";
        
        paramsMap.put(CommonConstant.HttpContext.URL, "http://URL.com");
        paramsMap.put(HttpContext.METHOD_TYPE, "PUT");
        
        RestfulResponse response = RestfulUtil.getRemoteResponse(paramsMap, params, queryParam);        
    }
    
    @Test
    public void test_getRemoteResponse_method_delete() {
        
        Map<String, String> paramsMap = new HashMap<>();
        Map<String, String> queryParam = new HashMap<>();
        String params = "params";
        
        paramsMap.put(CommonConstant.HttpContext.URL, "http://URL.com");
        paramsMap.put(HttpContext.METHOD_TYPE, "DELETE");
        
        RestfulResponse response = RestfulUtil.getRemoteResponse(paramsMap, params, queryParam);        
    }
}
