package com.xiaojian.pick.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author 小贱
 * @date 2020/9/16 - 19:18
 * 字符串修饰工具
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str){
        if(str == null || str.trim().equals("")){
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否不为空
     */
    public static boolean isNotEmpty(String str){
        if(str != null && !str.trim().equals("")){
            return true;
        }
        return false;
    }
    /**
     * 生成 6 位随机数
     */
    public static String getSixRandom(){
        Random random = new Random();
        String result = "";
        for(int i = 0; i < 6; i++){
            result += random.nextInt(10);
        }
        return result;
    }

/**
 * 返回当前日期，无格式
 */
    public static String getDateForm(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }
    /**
     * 返回
     */

}
