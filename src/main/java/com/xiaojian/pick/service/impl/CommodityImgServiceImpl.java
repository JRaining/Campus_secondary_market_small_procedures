package com.xiaojian.pick.service.impl;

import com.xiaojian.pick.entity.CommodityImg;
import com.xiaojian.pick.mapper.CommodityImgMapper;
import com.xiaojian.pick.service.CommodityImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/22 - 19:15
 */
@Service
public class CommodityImgServiceImpl implements CommodityImgService {

    @Autowired
    private CommodityImgMapper commodityImgMapper;


    @Override
    public List<CommodityImg> findByCommodityId(Integer id) {
        return commodityImgMapper.findByCommodityId(id);
    }

    @Override
    public List<CommodityImg> getSwiperList() {
        return commodityImgMapper.getSwiperList();
    }

    @Override
    public int add(CommodityImg commodityImg) {
        return commodityImgMapper.add(commodityImg);
    }

    @Override
    public int deleteByCommodityId(List<Integer> commodityIds) {
        return commodityImgMapper.deleteByCommodityId(commodityIds);
    }
}
