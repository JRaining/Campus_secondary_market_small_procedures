package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.Seek;
import com.xiaojian.pick.page.SeekCustom;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/24 - 17:00
 */
public interface SeekService {
    // 根据 id 查询
    Seek findById(Integer id);
    // 根据用户查询求购列表
    List<Seek> findByUserId(Integer userId);
    // 查询所有求购列表
    List<Seek> findAll();
    // 条件查询求购列表
    List<Seek> findByParam(SeekCustom seekCustom,Integer page, Integer pageSize);
    // 获取求购信息数量
    Long getCount(SeekCustom seekCustom);
    // 添加 求购信息
    int addSeek(Seek seek);
    // 修改求购信息（目前只用到，修改求购信息状态）
    int updateSeekState(Integer seekId);
    // 删除求购信息
    int delSeek(Integer id);

        // 分页查询的求购列表
    List<Seek> findByPage(Integer page,Integer pageSize);
    // 关键字搜索求购、分页
    List<Seek> search(String content,Integer page,Integer pageSize);
}
