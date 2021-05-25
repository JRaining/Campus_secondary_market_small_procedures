package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.Inform;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/25 - 17:10
 */
public interface InformService {

    // 根据 id 查询举报信息
    Inform findById(Integer id);
    // 查询所有举报信息
    List<Inform> findAll();
    // 条件查询举报信息
    List<Inform> findByParam(Inform inform,Integer page,Integer pageSize);
    // 举报信息数量
    Long getCount(Inform inform);
    // 修改举报信息状态：已处理
    int updateInform(Integer id);
    // 添加举报信息
    int addInform(Inform inform);
    // 删除举报信息
    int delInform(Inform inform);
}
