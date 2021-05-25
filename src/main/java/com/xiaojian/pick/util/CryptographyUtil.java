package com.xiaojian.pick.util;

import org.apache.shiro.crypto.hash.Md2Hash;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 加密工具类
 */
public class CryptographyUtil {

    public final static String SALT = "pick";

    /**
     * MD 5 加密
     */
    public static String md5(String str,String salt){
        return new Md5Hash(str,salt).toString();
    }

    public static void main(String[] args) {
        String password = "qweqwe";
        System.out.println(md5(password,SALT));
    }

}
