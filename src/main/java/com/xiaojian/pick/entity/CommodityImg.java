package com.xiaojian.pick.entity;

import lombok.Data;

/**
 * 商品图片
 */
@Data
public class CommodityImg {
    private Integer id;   // id
    private String imgSrc;  // 图片地址
    private Integer commodityId; // 商品类
}