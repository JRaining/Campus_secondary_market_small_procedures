package com.xiaojian.pick.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class Topic {
    private Integer id;     // 主键
    private String theme;   // 帖子主题
    private String description;     // 帖子说明
    private Integer commentCount;   // 帖子评论数
    private Integer clickCount;     // 帖子点击率
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;       // 帖子发布时间
    private Integer state;      // 0，删除状态；1，展示中
    private Integer hotDegree;  // 热门程度：1,2,3

    private User user;     // 帖子发布者
    private List<TopicImg> topicImgList;    // 帖子内图片
    private List<Comment> commentList;      // 帖子的评论列表
}