package com.xiaojian.pick.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Comment {
    private Integer id;     // 评论表主键id
    private String content;     // 评论内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;   // 评论时间
    private Integer state;  // 0，删除状态；1，显示中

    private Integer topicId;    // 帖子 id
    private User user;      // 评论者

}