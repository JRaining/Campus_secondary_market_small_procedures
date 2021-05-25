package com.xiaojian.pick.entity;

import lombok.Data;

import java.util.Date;


@Data
public class WxAccessToken {
    private String accessToken;
    private Date expire;
    private Date createTime;
    private Date updateTime;

}