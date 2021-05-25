package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.User;
import com.xiaojian.pick.page.ReturnData;

import java.util.List;
import java.util.Map;

/**
 * @author 小贱
 * @date 2020/10/12 - 18:43
 */
public interface UserService {
    // 用户初次登录
    ReturnData loginByCode(String code);
    // 根据 id 查询信息
    User findById(Integer id);
    // 根据条件查询 用户列表
    List<User> findByParam(User user,Integer page,Integer pageSize);
    // 根据条件查询 用户列表数量
    Long getCount(User user);
    // 根据 openid 查询用户信息
    User findByOpenid(String openid);
    // 添加用户信息
    int addUser(User user);
    // 更新用户信息
    int updateUser(User user);
}
