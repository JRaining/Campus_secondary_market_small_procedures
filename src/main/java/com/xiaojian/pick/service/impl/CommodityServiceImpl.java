package com.xiaojian.pick.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiaojian.pick.entity.Category;
import com.xiaojian.pick.entity.Commodity;
import com.xiaojian.pick.entity.CommodityImg;
import com.xiaojian.pick.mapper.CommodityMapper;
import com.xiaojian.pick.page.CommodityCustom;
import com.xiaojian.pick.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/13 - 16:38
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;


    @Override
    public Commodity findById(Integer id) {
        return commodityMapper.findById(id);
    }

    @Override
    public List<Commodity> findByUserId(Integer id) {
        return commodityMapper.findByUserId(id);
    }

    @Override
    public List<Commodity> findByCategoryId(Integer id) {
        return commodityMapper.findByCategoryId(id);
    }

    @Override
    public List<Commodity> findAll() {
        return commodityMapper.findAll();
    }

    @Override
    public List<Commodity> findByParam(CommodityCustom commodityCustom, Integer page, Integer pageSize) {
        if(page != null && pageSize != null){
            PageHelper.startPage(page,pageSize);
        }
        return commodityMapper.findByParam(commodityCustom);
    }

    @Override
    public Long getCount(CommodityCustom commodityCustom) {
        return commodityMapper.getCount(commodityCustom);
    }

    @Override
    public int addCommodity(Commodity commodity) {
        return commodityMapper.addCommodity(commodity);
    }

//    @Override
//    public Commodity getCommodityImgList(String id) {
//        return commodityMapper.getCommodityImgList(id);
//    }

    @Override
    public int deleteByid(Integer id) {
        return commodityMapper.deleteByid(id);
    }

    @Override
    public int realDelCommodity(Integer id) {
        return commodityMapper.realDelCommodity(id);
    }

    @Override
    public int updateCoverImgOfCommodity(Commodity commodity) {
        return commodityMapper.updateCoverImgOfCommodity(commodity);
    }

    @Override
    public int updateCommodityState(Integer state, Integer id) {
        return commodityMapper.updateCommodityState(state,id);
    }

    @Override
    public int addCommodityClick(Integer id) {
        return commodityMapper.addCommodityClick(id);
    }

    @Override
    public List<Commodity> getUserPublish(Commodity commodity) {
        return commodityMapper.getUserPublish(commodity);
    }

    @Override
    public List<Commodity> getSwiperCommodity() {
        return commodityMapper.getSwiperCommodity();
    }

    @Override
    public List<Commodity> findByPage(Commodity commodity,Integer page,Integer pageSize) {
        PageHelper.startPage(page,pageSize);

        return commodityMapper.findByPage(commodity);
    }

    @Override
    public List<Commodity> search(String content,Integer page,Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        return commodityMapper.search(content);
    }

}
