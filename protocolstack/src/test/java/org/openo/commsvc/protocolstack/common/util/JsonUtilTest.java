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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;


public class JsonUtilTest {

    @Test
    public void testToJson() {
        
       Map map = new HashMap<>();
       map.put("1", "one");
       String res = JsonUtil.toJson(map);
       assertEquals("{\"1\":\"one\"}", res);
    }
    
    
    @Test
    public void testFromJson() {
        
       String map = "{\"1\":\"one\"}";
       Map res = JsonUtil.fromJson(map, Map.class);
       assertEquals("one", res.get("1"));
    }
    
    @Test
    public void testFromJson1() {
        
       String map = "{\"1\":\"one\"}";
       Map res = JsonUtil.fromJson(map, new TypeReference<Map>() {});
       assertEquals("one", res.get("1"));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testFromJson2() {
        
       String map = "{\"1\":\"one\"}";
       Map res = JsonUtil.fromJson(new File("busconfig.json"), Map.class);
       assertEquals("one", res.get("1"));
    }

}
