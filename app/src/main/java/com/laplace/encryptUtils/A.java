package com.laplace.encryptUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class A {
    /*
算法/模式/填充                 16字节加密后数据长度       不满16字节加密后长度
AES/CBC/NoPadding                   16                          不支持
AES/CBC/PKCS5Padding                32                          16
AES/CBC/ISO10126Padding             32                          16
AES/CFB/NoPadding                   16                          原始数据长度
AES/CFB/PKCS5Padding                32                          16
AES/CFB/ISO10126Padding             32                          16
AES/ECB/NoPadding                   16                          不支持
AES/ECB/PKCS5Padding                32                          16
AES/ECB/ISO10126Padding             32                          16
AES/OFB/NoPadding                   16                          原始数据长度
AES/OFB/PKCS5Padding                32                          16
AES/OFB/ISO10126Padding             32                          16
AES/PCBC/NoPadding                  16                          不支持
AES/PCBC/PKCS5Padding               32                          16
AES/PCBC/ISO10126Padding            32                          16

需要注意，AES 密钥长度只能是 16、25 或 32 字节，如果不符合要求则会异常：
                       128
java.security.InvalidKeyException: Invalid AES key length
     */
    private static final String SECRET = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

//    private static final String CIPHER_ = "AES/CBC/NoPadding";
//    private static final String CIPHER_ = "AES/CBC/PKCS5Padding";
//    private static final String CIPHER_ = "AES/CBC/ISO10126Padding";
//    private static final String CIPHER_ = "AES/CFB/NoPadding";
//    private static final String CIPHER_ = "AES/CFB/PKCS5Padding";
//    private static final String CIPHER_ = "AES/CFB/ISO10126Padding";
//    private static final String CIPHER_ = "AES/ECB/NoPadding";
//    private static final String CIPHER_ = "AES/ECB/PKCS5Padding";
//    private static final String CIPHER_ = "AES/ECB/ISO10126Padding";
//    private static final String CIPHER_ = "AES/OFB/NoPadding";
//    private static final String CIPHER_ = "AES/OFB/PKCS5Padding";
//    private static final String CIPHER_ = "AES/OFB/ISO10126Padding";
//    private static final String CIPHER_ = "AES/PCBC/NoPadding";
//    private static final String CIPHER_ = "AES/PCBC/PKCS5Padding";
//    private static final String CIPHER_ = "AES/PCBC/ISO10126Padding";



    public static byte[] decode(byte[] key,byte[] content) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, SECRET));
        return  cipher.doFinal(content);
    }


    public static byte[] encode(byte[] key ,byte[] content) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance((CIPHER_ALGORITHM));
        cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key,SECRET));
        return cipher.doFinal(content);
    }

}
