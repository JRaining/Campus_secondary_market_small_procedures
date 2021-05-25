package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.Inform;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InformMapper {

    // 根据 id 查询举报信息
    Inform findById(Integer id);
    // 查询所有举报信息
    List<Inform> findAll();
    // 条件查询举报信息
    List<Inform> findByParam(Inform inform);
    // 举报信息数量
    Long getCount(Inform inform);
    // 修改举报信息状态：已处理
    int updateInform(Integer id);
    // 添加举报信息
    int addInform(Inform inform);
    // 删除举报信息
    int delInform(Inform inform);

    // 根据 商品 id 删除
    int delInformByCommodity(List<Integer> commodityIds);
    // 根据 求购 id 删除
    int delInformBySeek(List<Integer> seekIds);
    // 根据 帖子 id 删除
    int delInformByTopic(List<Integer> topicIds);


    // 测试拼接语句
    List<Inform> testSeparator(List<Integer> ids);

}