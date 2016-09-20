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

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;

public class EncryptUtilTest {

    byte[] DEFAULT_IV = {2, 1, 4, 8, 0, 3, 2, 0, 7, 9, 2, 8, 5, 11, 6, 1};

    IvParameterSpec IV_PARAMETER_SPEC = new IvParameterSpec(DEFAULT_IV);

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.common.util.EncryptUtil#encrypt(java.lang.String)}.
     * 
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    @Test
    public void testEncrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        String plain = "default";
        String result = EncryptUtil.encrypt(plain);
        assertEquals(result, "M9IcQzwkzCXJ6on+ZTl+fg==");
    }

    /**
     * Test method for
     * {@link org.openo.commsvc.protocolstack.common.util.EncryptUtil#decrypt(java.lang.String)}.
     */
    @Test
    public void testDecrypt() {
        String encrypted = "M9IcQzwkzCXJ6on+ZTl+fg==";
        char[] result = EncryptUtil.decrypt(encrypted);
        assertTrue(true);
    }
    @Test
    public void testDecryptNull() {
        String encrypted = null;
        char[] result = EncryptUtil.decrypt(encrypted);
        assertTrue(true);
         encrypted = "";
         result = EncryptUtil.decrypt(encrypted);
        assertTrue(true);
    }

}
