package com.xiaojian.pick.service.impl;

import com.xiaojian.pick.entity.TopicImg;
import com.xiaojian.pick.mapper.TopicImgMapper;
import com.xiaojian.pick.service.TopicImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/22 - 23:09
 */
@Service
public class TopicImgServiceImpl implements TopicImgService {

    @Autowired
    private TopicImgMapper topicImgMapper;


    @Override
    public List<TopicImg> findByTopicId(Integer topicId) {
        return topicImgMapper.findByTopicId(topicId);
    }

    @Override
    public int addTopicImg(TopicImg topicImg) {
        return topicImgMapper.addTopicImg(topicImg);
    }

    @Override
    public int deleteByTopicId(List<Integer> topicIds) {
        return topicImgMapper.deleteByTopicId(topicIds);
    }
}
