package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.TopicImg;

import java.util.List;

public interface TopicImgMapper {

    // 根据帖子 id 查询
    List<TopicImg> findByTopicId(Integer topicId);

    // 添加帖子图片
    int addTopicImg(TopicImg topicImg);
    // 删除帖子记录
    int deleteByTopicId(List<Integer> topicId);

}