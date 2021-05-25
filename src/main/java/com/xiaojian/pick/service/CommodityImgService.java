package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.CommodityImg;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/22 - 19:14
 */
public interface CommodityImgService {
    // 根据商品 id 查询
    List<CommodityImg> findByCommodityId(Integer id);
    // 是否是轮播图
    List<CommodityImg> getSwiperList();

    // 添加商品图片
    int add(CommodityImg commodityImg);
    // 删除商品记录
    int deleteByCommodityId(List<Integer> commodityIds);
}
