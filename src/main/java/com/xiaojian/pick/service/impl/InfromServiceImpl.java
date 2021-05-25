package com.xiaojian.pick.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiaojian.pick.entity.Inform;
import com.xiaojian.pick.mapper.InformMapper;
import com.xiaojian.pick.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/25 - 17:11
 */
@Service
public class InfromServiceImpl implements InformService {

    @Autowired
    private InformMapper informMapper;

    @Override
    public Inform findById(Integer id) {
        return informMapper.findById(id);
    }

    @Override
    public List<Inform> findAll() {
        return informMapper.findAll();
    }

    @Override
    public List<Inform> findByParam(Inform inform,Integer page,Integer pageSize) {

        if(page != null && pageSize != null){
            PageHelper.startPage(page,pageSize);
        }

        return informMapper.findByParam(inform);
    }

    @Override
    public Long getCount(Inform inform) {
        return informMapper.getCount(inform);
    }

    @Override
    public int updateInform(Integer id) {
        return informMapper.updateInform(id);
    }

    @Override
    public int addInform(Inform inform) {
        return informMapper.addInform(inform);
    }

    @Override
    public int delInform(Inform inform) {
        return informMapper.delInform(inform);
    }
}
