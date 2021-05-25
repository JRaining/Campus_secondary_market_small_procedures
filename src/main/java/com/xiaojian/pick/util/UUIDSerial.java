package com.xiaojian.pick.util;

import java.util.UUID;

/**
 * @author 小贱
 * @date 2020/10/14 - 16:45
 */
public class UUIDSerial {

    public static String getSerial(){
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.println(getSerial());
    }
}
