package com.xiaojian.pick.mapper;


import com.xiaojian.pick.entity.User;

import java.util.List;

public interface UserMapper {

    // 根据 id 查询信息
    User findById(Integer id);
    // 根据条件查询 用户列表
    List<User> findByParam(User user);
    // 根据条件查询 用户列表数量
    Long getCount(User user);
    // 根据 openid 查询用户信息
    User findByOpenid(String openid);
    // 添加用户信息
    int addUser(User user);
    // 更新用户信息
    int updateUser(User user);

}