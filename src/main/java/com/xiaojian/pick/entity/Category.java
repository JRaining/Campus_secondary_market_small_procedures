package com.xiaojian.pick.entity;

import lombok.Data;

/**
 * 商品分类
 */
@Data
public class Category {
    private Integer id;      // id
    private String cateName;    // 分类名称
    private String cateIcon;    // 分类图标
    private Integer sort;   // 排序
}