package com.xiaojian.pick.entity;

import lombok.Data;

@Data
public class TopicImg {
    private Integer id;     // 帖子图片 id
    private String imgSrc;  // 帖子图片路径
    private Integer topicId;    // 帖子
}