package com.xiaojian.pick.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 前端用户类
 */
@Data
public class User {
    // 用户信息完整度状态
    // 用户信息不完整
    public static final String NOT_USER_INFO = "0";
    // 用户信息已部分完整
    public static final String HAVE_USER_INFO = "1";
    // 用户信息完整
    public static final String ALL_USER_INFO = "2";

    private Integer id;  // id
    private String openid;  // 微信小程序用户 openid
    private String nickName;    // 用户昵称
    private String avatarUrl;   // 用户头像
    private Byte gender;    // 性别
    private String country; // 国家
    private String province;    // 省份
    private String city;    // 城市
    private String haveUserInfo;    // 是否已获取用户信息

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;    // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;    // 更新时间

    private String qqNum;   // 预留 QQ号
    private String wechatNum;      // 预留 微信号
}