package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.Attention;

import java.util.List;

public interface AttentionMapper {
    // 根据用户 id 查询关注列表
    List<Attention> findByUserId(Integer userId);
    // 商品id、用户id查询关注信息
    Attention findAttention(Attention attention);
    // 删除关注话题
    int deleteAttention(Integer id);
    // 帖子详情页面，取消关注话题
    int cancelAttention(Attention attention);
    // 添加关注话题信息
    int addAttention(Attention attention);


//  根据 topic_id 删除收藏记录
    int deleteByTopicId(List<Integer> topicIds);
}