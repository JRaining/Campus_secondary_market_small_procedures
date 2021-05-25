package com.xiaojian.pick.util.wechat;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaojian.pick.entity.User;
import com.xiaojian.pick.page.ReturnData;
import com.xiaojian.pick.util.HttpRequest;

/**
 * @author 小贱
 * @date 2020/11/1 - 21:53
 */
public class WxLoginUtil {

    // 获取微信登录用户的openid
    public static ReturnData getOpenid(String code){
        ReturnData returnData = null;

        // 设置访问微信服务器的参数
        String params = "appid=" + WxConsts.WX_APPID
                + "&secret=" + WxConsts.WX_APPSECRET
                + "&js_code=" + code
                + "&grant_type=" + WxConsts.GRANT_TYPE_AUTHORIZATION_CODE;
        // 返回的数据是 json 格式的字符串
        String result = HttpRequest.sendGet(WxConsts.GET_SESSION_BY_CODE,params);
        // 解析返回数据
        JSONObject jsonObject = JSON.parseObject(result);
        if(jsonObject == null){
            returnData = new ReturnData(false,"微信 api 调用失败，请重试！");
        }
        // 如果解析结果为错误信息
        Integer errcode = (Integer) jsonObject.get("errcode");
        String errmsg = (String) jsonObject.get("errmsg");
        if(errcode != null && errmsg != null){
            returnData = new ReturnData(false,errmsg);
        }
        // openid
        String openid = (String) jsonObject.get("openid");
        User user = new User();
        user.setOpenid(openid);
        returnData = new ReturnData();
        returnData.setUser(user);

    System.out.println("========openid=====" + openid);
    System.out.println("========这里返回了微信服务器的响应信息=======" + result);
        return returnData;
    }

    public static void main(String[] args) {
        getOpenid("073OnVkl2iNLT54iEMml2TMXKm0OnVkH");
    }
}




