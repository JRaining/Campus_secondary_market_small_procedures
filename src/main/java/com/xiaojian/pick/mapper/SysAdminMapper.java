package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.SysAdmin;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysAdminMapper {

    // 根据用户名查询
    SysAdmin findByUsername(String username);
}