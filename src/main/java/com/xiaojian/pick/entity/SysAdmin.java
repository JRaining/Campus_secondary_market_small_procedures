package com.xiaojian.pick.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 后台管理员类
 */
@Data
public class SysAdmin {
    private Integer id;  // id
    private String username;    // 用户名
    private String password;    // 密码
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerDate;  // 注册时间
    private Boolean admin;  // 是否为超级管理员
}