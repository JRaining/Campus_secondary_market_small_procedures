package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.Attention;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/23 - 20:51
 */
public interface AttentionService {
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
}
