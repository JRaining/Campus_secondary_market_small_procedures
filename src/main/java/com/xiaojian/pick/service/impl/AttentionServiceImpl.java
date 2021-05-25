package com.xiaojian.pick.service.impl;

import com.xiaojian.pick.entity.Attention;
import com.xiaojian.pick.mapper.AttentionMapper;
import com.xiaojian.pick.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/23 - 20:51
 */
@Service
public class AttentionServiceImpl implements AttentionService {

    @Autowired
    private AttentionMapper attentionMapper;

    @Override
    public List<Attention> findByUserId(Integer userId) {
        return attentionMapper.findByUserId(userId);
    }

    @Override
    public Attention findAttention(Attention attention) {
        return attentionMapper.findAttention(attention);
    }

    @Override
    public int deleteAttention(Integer id) {
        return attentionMapper.deleteAttention(id);
    }

    @Override
    public int cancelAttention(Attention attention) {
        return attentionMapper.cancelAttention(attention);
    }

    @Override
    public int addAttention(Attention attention) {
        return attentionMapper.addAttention(attention);
    }
}
