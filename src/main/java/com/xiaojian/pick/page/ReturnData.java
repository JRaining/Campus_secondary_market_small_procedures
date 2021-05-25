package com.xiaojian.pick.page;

import com.xiaojian.pick.entity.User;
import lombok.Data;

/**
 * @author 小贱
 * @date 2020/11/2 - 9:03
 *  登录时返回给小程序端的数据
 */
@Data
public class ReturnData {
    private User user;
    private boolean success;
    private String message;
    private String haveUserInfo;
    private String token;

    public ReturnData() {
    }

    public ReturnData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ReturnData(boolean success, String message, String haveUserInfo,User user) {
        this.success = success;
        this.message = message;
        this.haveUserInfo = haveUserInfo;
        this.user = user;
    }
}
