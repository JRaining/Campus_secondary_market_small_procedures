package com.xiaojian.pick.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Message {
    private Integer id;     // 消息 id
    private String title;   // 消息主题
    private String content; // 消息内容
    private Integer state;  // 消息阅读状态（0:未读；1:已读；2，删除状态）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;    // 消息发送的时间

    private User user;     // 消息接收者
}