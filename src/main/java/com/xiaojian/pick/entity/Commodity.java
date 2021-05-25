package com.xiaojian.pick.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 商品类
 */
@Data
public class Commodity {
    private Integer id;  // id
    private String title;  //商品标题
    private Double oldPrice;    // 原价
    private Double price;   // 现价
    private String quality; // 品质（九成新，使用时长）
    private Integer repertory;  // 库存数量
    private Integer clickCount = 0; // 浏览量
    private Integer collectCount = 0;   // 收藏数
    private Integer state;      // 商品状态（1，出售中；2，售罄；3，下架）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;   // 发布时间
    private String coverImage;  // 商品封面
    private String serial;      // 商品编号
    private String description; // 商品描述
    private Boolean swiper;     // 是否为 轮播图

    private Category category;  // 商品分类
    private User user;      // 商品发布者
    private List<CommodityImg> commodityImgList;    // 商品图片列表
}