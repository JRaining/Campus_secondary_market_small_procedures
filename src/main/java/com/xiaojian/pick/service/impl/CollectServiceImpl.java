package com.xiaojian.pick.service.impl;

import com.xiaojian.pick.entity.Collect;
import com.xiaojian.pick.mapper.CollectMapper;
import com.xiaojian.pick.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/5 - 17:47
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    public List<Collect> findByUserId(Integer userId) {
        return collectMapper.findByUserId(userId);
    }

    @Override
    public Collect findCollect(Collect collect) {
        return collectMapper.findCollect(collect);
    }

    @Override
    public int deleteCollect(Integer id) {
        return collectMapper.deleteCollect(id);
    }

    @Override
    public int cancelCollect(Collect collect) {
        return collectMapper.cancelCollect(collect);
    }

    @Override
    public int addCollect(Collect collect) {
        return collectMapper.addCollect(collect);
    }
}
