package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.SysAdmin;

/**
 * @author 小贱
 * @date 2020/10/12 - 18:53
 */

public interface SysAdminService {

    // 根据用户名查询
    SysAdmin findByUsername(String username);
}
