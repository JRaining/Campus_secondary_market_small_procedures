package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.Message;

import java.util.List;

public interface MessageMapper {
    // 查询所有消息
    List<Message> findAll();
    // 条件查询消息
    List<Message> findByParam(Message message);
    // 根据用户查询消息列表
    List<Message> findByUserId(Integer userId);
    // 根据用户查询消息数量
    Long getCountByUserId(Integer userId);
    // 发布消息
    int publishMsg(Message message);
    // 更改消息状态（0，未读；1，已读；2，删除状态）
    int updateMsgState(Message message);

    // 删除消息（一般根据消息状态为：2，已删除）
    int delMessageByState(Integer state);
}