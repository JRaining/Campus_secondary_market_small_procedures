package com.xiaojian.pick.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiaojian.pick.entity.Topic;
import com.xiaojian.pick.mapper.TopicMapper;
import com.xiaojian.pick.page.TopicCustom;
import com.xiaojian.pick.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/22 - 22:02
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public Topic findById(Integer id) {
        return topicMapper.findById(id);
    }

    @Override
    public List<Topic> findByUserId(Integer userId) {
        return topicMapper.findByUserId(userId);
    }

    @Override
    public List<Topic> findAll() {
        return topicMapper.findAll();
    }

    @Override
    public List<Topic> findByParam(TopicCustom topicCustom,Integer page,Integer pageSize) {
        PageHelper.startPage(page,pageSize);

        return topicMapper.findByParam(topicCustom);
    }

    @Override
    public Long getCount(TopicCustom topicCustom) {
        return topicMapper.getCount(topicCustom);
    }

    @Override
    public int addTopic(Topic topic) {
        return topicMapper.addTopic(topic);
    }

    @Override
    public int updateTopicState(Topic topic) {
        return topicMapper.updateTopicState(topic);
    }

    @Override
    public int updateTopicHot(Topic topic) {
        return topicMapper.updateTopicHot(topic);
    }

    @Override
    public int addTopicClick(Integer id) {
        return topicMapper.addTopicClick(id);
    }

    @Override
    public int addTopicComment(Integer id) {
        return topicMapper.addTopicComment(id);
    }

    @Override
    public int subTopicComment(Integer id) {
        return topicMapper.subTopicComment(id);
    }

    @Override
    public List<Topic> getUserPublish(Topic topic) {
        return topicMapper.getUserPublish(topic);
    }

    @Override
    public List<Topic> findByPage(Topic topic, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);

        return topicMapper.findByPage(topic);
    }

    @Override
    public List<Topic> search(String content, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);

        return topicMapper.search(content);
    }

    @Override
    public List<Topic> overtimeTopic() {
        return topicMapper.overtimeTopic();
    }

    @Override
    public int realDelTopic(Integer id) {
        return topicMapper.realDelTopic(id);
    }
}
