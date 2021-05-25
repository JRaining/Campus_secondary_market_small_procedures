package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.CommodityImg;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommodityImgMapper {
    // 根据商品 id 查询
    List<CommodityImg> findByCommodityId(Integer id);
    // 是否是轮播图
    List<CommodityImg> getSwiperList();

    // 添加商品图片
    int add(CommodityImg commodityImg);

    // 删除商品图片记录
    int deleteByCommodityId(List<Integer> commodityId);
}