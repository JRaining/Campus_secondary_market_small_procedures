package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.Category;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    // 根据 id 查询
    Category findById(Integer id);

    // 查询所有 商品分类
    List<Category> findAll();
    // 查询所有商品分类 数量
    Long getCount();
    // 增加 商品分类
    int addCategory(Category category);
    // 修改 商品分类
    int updateCategory(Category category);
    // 删除 商品分类
    int deleteCategory(Integer id);
}