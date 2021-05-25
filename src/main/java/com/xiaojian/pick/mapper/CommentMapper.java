package com.xiaojian.pick.mapper;

import com.xiaojian.pick.entity.Comment;

import java.util.List;

public interface CommentMapper {

// 根据帖子 id 查询 评论列表
    List<Comment> findByTopicId(Integer topicId);

// 添加帖子的评论
    int addComment(Comment comment);
// 修改评论状态
    int updateCommentState(Comment comment);

// 删除帖子的评论记录
    int deleteByTopicId(List<Integer> topicIds);

}