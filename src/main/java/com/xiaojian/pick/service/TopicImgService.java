package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.CommodityImg;
import com.xiaojian.pick.entity.TopicImg;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/22 - 23:00
 */
public interface TopicImgService {

    // 根据帖子 id 查询
    List<TopicImg> findByTopicId(Integer topicId);

    // 添加帖子图片
    int addTopicImg(TopicImg topicImg);
    // 删除帖子记录
    int deleteByTopicId(List<Integer> topicId);
    
}
