package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.Collect;

import java.util.List;

public interface CollectMapper {
    // 根据用户 id 查询收藏列表
    List<Collect> findByUserId(Integer userId);
    // 商品id、用户id查询收藏信息
    Collect findCollect(Collect collect);
    // 删除收藏
    int deleteCollect(Integer id);
    // 商品详情页面，取消收藏
    int cancelCollect(Collect collect);
    // 添加收藏信息
    int addCollect(Collect collect);

//  根据 commodity_id 删除收藏记录
    int deleteByCommodityId(List<Integer> commodityIds);
}