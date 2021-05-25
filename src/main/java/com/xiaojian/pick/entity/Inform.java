package com.xiaojian.pick.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Inform {
    private Integer id;     // 主键
    private String content; // 举报内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;    // 举报时间
    private Boolean state;  // 信息状态：0,未处理；1，已处理

    private User user;  // 举报者
    private Commodity commodity;    // 被举报的商品
    private Seek seek;      // 被举报的求购
    private Topic topic;    // 被举报的帖子
}