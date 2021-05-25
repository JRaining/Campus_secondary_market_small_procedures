package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.Collect;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/5 - 17:47
 */
public interface CollectService {
    // 根据用户 id 查询
    List<Collect> findByUserId(Integer userId);
    // 商品id、用户id查询收藏信息
    Collect findCollect(Collect collect);
    // 删除收藏
    int deleteCollect(Integer id);
    // 商品详情页面，取消收藏
    int cancelCollect(Collect collect);
    // 添加收藏信息
    int addCollect(Collect collect);
}
