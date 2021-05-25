package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.Message;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/6 - 18:44
 */
public interface MessageService {
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


}
