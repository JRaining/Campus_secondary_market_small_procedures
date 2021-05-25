package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.Topic;
import com.xiaojian.pick.page.TopicCustom;

import java.util.List;

public interface TopicMapper {
// 根据 id 查询帖子（简单内容）
    Topic findByTopicId(Integer id);
// 根据 id 查询帖子
    Topic findById(Integer id);
// 根据 用户id 查询其发布的
    List<Topic> findByUserId(Integer userId);
// 查询所有帖子
    List<Topic> findAll();
// 根据条件（帖子标题 、帖子内容..）查询 帖子列表
    List<Topic> findByParam(TopicCustom topicCustom);
// 获取所有 帖子数量
    Long getCount(TopicCustom topicCustom);
// 添加帖子信息
    int addTopic(Topic topic);


// 修改帖子状态（0，已删除；1，展示中）
    int updateTopicState(Topic topic);
// 修改帖子热门程度（热门程度：1,2,3）
    int updateTopicHot(Topic topic);
// 帖子点击量 +1
    int addTopicClick(Integer id);
// 帖子评论数 +1
    int addTopicComment(Integer id);
// 帖子评论数 -1
    int subTopicComment(Integer id);
// 小程序中查看一个用户发布的帖子
    List<Topic> getUserPublish(Topic topic);
// 分页 查询 未删除 的帖子列表
    List<Topic> findByPage(Topic topic);
// 关键字搜索帖子、分页
    List<Topic> search(String content);


//  查询帖子发布时间超过 30 天的帖子列表
    List<Topic> overtimeTopic();
// 真实删除帖子记录
    int realDelTopic(Integer id);
// 根据 状态 查询帖子列表
    List<Topic> findByState(Integer state);
// 根据 状态 删除帖子列表
    int delTopicByState(Integer state);
}