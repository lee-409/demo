package com.lee.demo.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class EncodeUtil {

    public static String encoderByMd5(String str) {
        String newstr = null;
        try {
            //确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            // 加密后的字符串
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
            //endregion
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newstr;
    }

}
