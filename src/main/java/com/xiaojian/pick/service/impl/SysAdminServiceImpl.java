package com.xiaojian.pick.service.impl;

import com.xiaojian.pick.entity.SysAdmin;
import com.xiaojian.pick.mapper.SysAdminMapper;
import com.xiaojian.pick.service.SysAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小贱
 * @date 2020/10/12 - 18:53
 */
@Service
public class SysAdminServiceImpl implements SysAdminService {

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Override
    public SysAdmin findByUsername(String username) {
        return sysAdminMapper.findByUsername(username);
    }
}
