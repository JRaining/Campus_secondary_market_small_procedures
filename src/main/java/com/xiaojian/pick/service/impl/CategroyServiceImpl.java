package com.xiaojian.pick.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiaojian.pick.entity.Category;
import com.xiaojian.pick.mapper.CategoryMapper;
import com.xiaojian.pick.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/14 - 18:20
 */
@Service
public class CategroyServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public List<Category> findByPage(Integer page,Integer pageSize) {
        if(page != null && pageSize != null){
            PageHelper.startPage(page,pageSize);
        }

        return categoryMapper.findAll();
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    public Long getCount() {
        return categoryMapper.getCount();
    }

    @Override
    public int addCategory(Category category) {
        return categoryMapper.addCategory(category);
    }

    @Override
    public int updateCategory(Category category) {
        return categoryMapper.updateCategory(category);
    }

    @Override
    public int deleteCategory(Integer id) {
        return categoryMapper.deleteCategory(id);
    }
}
