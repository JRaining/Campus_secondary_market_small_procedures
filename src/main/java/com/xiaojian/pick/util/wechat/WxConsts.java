package com.xiaojian.pick.util.wechat;

/**
 * @author 小贱
 * @date 2020/11/1 - 22:02
 */
public class WxConsts {

    // 修改成自己小程序的 appid
    public static final String WX_APPID = "";
    // 修改成自己小程序的 appsecret
    public static final String WX_APPSECRET = "";

    public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";

    public static final String GRANT_TYPE_ACCESS_TOKEN = "access_token";

    public static final String GET_SESSION_BY_CODE = "https://api.weixin.qq.com/sns/jscode2session";

    public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
}
