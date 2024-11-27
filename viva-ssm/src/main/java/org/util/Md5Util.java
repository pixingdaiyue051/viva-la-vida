package org.util;

import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    public static String encode(String pwd) {
        // 确定计算方法
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 加密后的字符串
            return Base64Utils.encodeToString(md5.digest(pwd.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
