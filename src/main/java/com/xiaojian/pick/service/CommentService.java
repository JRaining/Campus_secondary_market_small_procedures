package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.Comment;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/23 - 1:47
 */
public interface CommentService {

    // 根据帖子 id 查询 评论列表
    List<Comment> findByTopicId(Integer topicId);
    // 根据 帖子id 查询 ，并分页
    List<Comment> findByPage(Integer topicId,Integer page,Integer pageSize);

    // 添加帖子的评论
    int addComment(Comment comment);
    // 修改评论状态
    int updateCommentState(Comment comment);


}
