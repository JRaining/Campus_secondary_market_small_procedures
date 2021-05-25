package com.xiaojian.pick.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiaojian.pick.entity.Seek;
import com.xiaojian.pick.mapper.SeekMapper;
import com.xiaojian.pick.page.SeekCustom;
import com.xiaojian.pick.service.SeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/24 - 17:01
 */
@Service
public class SeekServiceImpl implements SeekService {

    @Autowired
    private SeekMapper seekMapper;

    @Override
    public Seek findById(Integer id) {
        return seekMapper.findById(id);
    }

    @Override
    public List<Seek> findByUserId(Integer userId) {
        return seekMapper.findByUserId(userId);
    }

    @Override
    public List<Seek> findAll() {
        return seekMapper.findAll();
    }

    @Override
    public List<Seek> findByParam(SeekCustom seekCustom, Integer page, Integer pageSize) {
        if(page != null && pageSize != null){
            PageHelper.startPage(page,pageSize);
        }
        List<Seek> seekList = seekMapper.findByParam(seekCustom);
        return seekList;
    }

    @Override
    public Long getCount(SeekCustom seekCustom) {

        return seekMapper.getCount(seekCustom);
    }

    @Override
    public int addSeek(Seek seek) {
        return seekMapper.addSeek(seek);
    }

    @Override
    public int updateSeekState(Integer seekId) {
        return seekMapper.updateSeekState(seekId);
    }

    @Override
    public int delSeek(Integer id) {
        return seekMapper.delSeek(id);
    }

    @Override
    public List<Seek> findByPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);

        return seekMapper.findAll();
    }

    @Override
    public List<Seek> search(String content,Integer page,Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        return seekMapper.search(content);
    }
}
