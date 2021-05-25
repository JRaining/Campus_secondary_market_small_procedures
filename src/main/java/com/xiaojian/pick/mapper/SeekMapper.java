package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.Commodity;
import com.xiaojian.pick.entity.Seek;
import com.xiaojian.pick.page.CommodityCustom;
import com.xiaojian.pick.page.SeekCustom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeekMapper {
// 根据 id 查询
    Seek findById(Integer id);
// 根据用户查询求购列表
    List<Seek> findByUserId(Integer userId);
// 查询所有求购列表
    List<Seek> findAll();
// 条件查询求购列表
    List<Seek> findByParam(SeekCustom seekCustom);
// 获取求购信息数量
    Long getCount(SeekCustom seekCustom);
// 添加 求购信息
    int addSeek(Seek seek);
// 修改求购信息（目前只用到，修改求购信息状态）
    int updateSeekState(Integer seekId);
// 删除求购信息
    int delSeek(Integer id);


//    // 分页查询的求购列表
//    List<Seek> findByPage();

    // 关键字搜索求购、分页
    List<Seek> search(String content);


//  查询求购发布时间超过 30 天的求购列表
    List<Seek> overtimeSeek();
// 根据 求购状态 查询
    List<Seek> findByState(Integer state);
// 删除求购信息（一般根据求购状态：为0的已删除状态）
    int delSeekByState(Integer state);
}