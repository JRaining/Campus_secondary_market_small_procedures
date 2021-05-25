package com.xiaojian.pick.shiro;

import com.xiaojian.pick.entity.SysAdmin;
import com.xiaojian.pick.service.SysAdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义 Realm
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private SysAdminService sysAdminService;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 1、获取包含用户名 和 密码的 Token
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        // 2、查询用户名是否存在
        SysAdmin user = sysAdminService.findByUsername(token.getUsername());
        // 3、如果用户存在
        if(user != null){
            // 2. 判断密码，返回 AuthenticationInfo 子类
            // 第一个参数为 用户名
            // 第二个，password 为正确密码
            // 第三个，当前realm的名字,可通过父类getName()获得
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getName());
        } else{
            return null;
        }
    }
}
