package com.xiaojian.pick.service.impl;

import com.xiaojian.pick.entity.Message;
import com.xiaojian.pick.mapper.MessageMapper;
import com.xiaojian.pick.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/6 - 18:44
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> findAll() {
        return messageMapper.findAll();
    }

    @Override
    public List<Message> findByParam(Message message) {
        return messageMapper.findByParam(message);
    }

    @Override
    public List<Message> findByUserId(Integer userId) {
        return messageMapper.findByUserId(userId);
    }

    @Override
    public Long getCountByUserId(Integer userId) {
        return messageMapper.getCountByUserId(userId);
    }

    @Override
    public int publishMsg(Message message) {
        return messageMapper.publishMsg(message);
    }

    @Override
    public int updateMsgState(Message message) {
        return messageMapper.updateMsgState(message);
    }
}
