package com.laplace.encryptUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Slat {

    public static final String slat = "%#$^*@";

    public static String slatKey(String key) {
        key = slat + key + slat;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(key.getBytes(StandardCharsets.UTF_8));
            byte[] digest = m.digest();
            StringBuilder md5 = new StringBuilder();

            for (byte b : digest) {
                md5.append(Integer.toHexString(b & 0xff));
            }
            return md5.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
