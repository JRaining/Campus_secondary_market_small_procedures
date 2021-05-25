package com.xiaojian.pick.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiaojian.pick.entity.Comment;
import com.xiaojian.pick.mapper.CommentMapper;
import com.xiaojian.pick.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/23 - 1:47
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findByTopicId(Integer topicId) {
        return commentMapper.findByTopicId(topicId);
    }

    @Override
    public List<Comment> findByPage(Integer topicId, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        return commentMapper.findByTopicId(topicId);
    }

    @Override
    public int addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Override
    public int updateCommentState(Comment comment) {
        return commentMapper.updateCommentState(comment);
    }

}
