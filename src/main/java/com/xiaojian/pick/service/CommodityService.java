package com.xiaojian.pick.service;

import com.xiaojian.pick.entity.Category;
import com.xiaojian.pick.entity.Commodity;
import com.xiaojian.pick.entity.CommodityImg;
import com.xiaojian.pick.page.CommodityCustom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小贱
 * @date 2020/10/13 - 16:35
 */
public interface CommodityService {
    // 根据 id 查询商品
    Commodity findById(Integer id);
    // 根据 用户id 查询其发布的商品
    List<Commodity> findByUserId(Integer id);
    // 根据分类 id 查询商品列表
    List<Commodity> findByCategoryId(Integer id);
    // 查询所有商品
    List<Commodity> findAll();
    // 根据条件（商品标题 、商品编号、分类）查询 商品列表
    List<Commodity> findByParam(CommodityCustom commodityCustom, Integer page, Integer pageSize);
    // 获取所有 商品数量
    Long getCount(CommodityCustom commodityCustom);
    // 添加商品信息
    int addCommodity(Commodity commodity);
    // 获取所有 商品图片
//    Commodity getCommodityImgList(Integer id);

    // 将商品状态改为 下架
    int deleteByid(Integer id);
    // 真实删除商品记录
    int realDelCommodity(Integer id);

    // 修改商品信息，主要修改商品封面图片
    int updateCoverImgOfCommodity(Commodity commodity);
    // 修改商品状态（1，出售中；2，售罄；3，下架）
    int updateCommodityState(Integer state, Integer id);
    // 商品点击量 +1
    int addCommodityClick(Integer id);
    // 小程序中查看一个用户发布的商品（售出中的）
    List<Commodity> getUserPublish(Commodity commodity);
    // 小程序首页的轮播图
    List<Commodity> getSwiperCommodity();
    // 分页、分类查询 出售中 的商品列表
    List<Commodity> findByPage(Commodity commodity,Integer page,Integer pageSize);
    // 关键字搜索、出售中商品、分页
    List<Commodity> search(String content,Integer page,Integer pageSize);
}
