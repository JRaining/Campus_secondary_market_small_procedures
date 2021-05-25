package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.Category;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/14 - 18:20
 */
public interface CategoryService {

    // 根据 id 查询
    Category findById(Integer id);

    // 分页查询 商品分类
    List<Category> findByPage(Integer page,Integer pageSize);
    // 查询所有商品分类
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
