package com.xiaojian.pick.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiaojian.pick.entity.User;
import com.xiaojian.pick.mapper.UserMapper;
import com.xiaojian.pick.page.ReturnData;
import com.xiaojian.pick.service.UserService;
import com.xiaojian.pick.util.JwtUtil;
import com.xiaojian.pick.util.wechat.WxLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小贱
 * @date 2020/10/12 - 18:43
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public ReturnData loginByCode(String code) {
        ReturnData result = null;

        ReturnData returnData = WxLoginUtil.getOpenid(code);
        // 获取登录用户
        User user = returnData.getUser();
System.out.println("=======user=====" + user);
        // 判断数据库是否有该用户，没有则添加该用户（注册）
        User dbUser = userMapper.findByOpenid(user.getOpenid());
System.out.println("======dbUser=====" + dbUser);
        if(dbUser == null){
            // 添加该用户
            user.setHaveUserInfo("0");
            user.setCreateTime(new Date());
            int count = userMapper.addUser(user);
            if(count < 0){
                result = new ReturnData(false,"用户登录失败！");
                return result;
            }
            Map<String,Object> map = new HashMap<>();
            map.put("userId",user.getId());
            String token = JwtUtil.createToken(map);

            result = new ReturnData(true,"添加了该用户",User.NOT_USER_INFO,user);
            result.setToken(token);

        } else if(User.NOT_USER_INFO.equals(dbUser.getHaveUserInfo())){
            Map<String,Object> map = new HashMap<>();
            map.put("userId",user.getId());
            String token = JwtUtil.createToken(map);
            result = new ReturnData(true,"已存在该用户,但用户信息不完整！",User.NOT_USER_INFO,dbUser);
            result.setToken(token);
        } else{
            Map<String,Object> map = new HashMap<>();
            map.put("userId",user.getId());
            String token = JwtUtil.createToken(map);
            result = new ReturnData(true,"已存在该用户,且用户信息部分完整！",User.HAVE_USER_INFO,dbUser);
            result.setToken(token);
        }
//        if(dbUser != null){
//            if(dbUser.getNickName() != null && dbUser.getAvatarUrl() != null){
//                if(!user.getNickName().equals(dbUser.getNickName()) || !user.getAvatarUrl().equals(dbUser.getAvatarUrl())){
//                    // 更新用户信息
//                    userMapper.updateUser(user);
//                }
//            }
//        }
        return result;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> findByParam(User user,Integer page,Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        return userMapper.findByParam(user);
    }

    @Override
    public Long getCount(User user) {
        return userMapper.getCount(user);
    }

    @Override
    public User findByOpenid(String openid) {
        return userMapper.findByOpenid(openid);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }
}
