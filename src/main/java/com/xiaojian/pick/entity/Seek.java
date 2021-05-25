package com.xiaojian.pick.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Seek {

    private Integer id;     // 主键
    private String title;   // 求购标题
    private String remark;  // 备注
    private Integer state;  // 求购信息状态（0，已删除；1，存在中）
    private Double minPrice;    // 最低价格
    private Double maxPrice;    // 最高价格
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;   // 发布时间

    private User user;     // 求购发布者
}