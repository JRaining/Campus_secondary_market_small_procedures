package com.xiaojian.pick.controller;

import com.xiaojian.pick.entity.*;
import com.xiaojian.pick.page.AjaxResult;
import com.xiaojian.pick.page.CommodityCustom;
import com.xiaojian.pick.service.CommodityImgService;
import com.xiaojian.pick.service.CommodityService;
import com.xiaojian.pick.service.MessageService;
import com.xiaojian.pick.util.Consts;
import com.xiaojian.pick.util.OSSClientUtil;
import com.xiaojian.pick.util.StringUtil;
import com.xiaojian.pick.util.UUIDSerial;
import org.eclipse.jdt.internal.compiler.SourceElementNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小贱
 * @date 2020/10/13 - 14:52
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CommodityImgService commodityImgService;
    @Autowired
    private MessageService messageService;

/**
 * 跳转到商品列表页面
 */
    @RequestMapping("/toCommodity")
    public String toCommodity(){
        return "commodity/list";
    }

/**
 * 返回所有商品列表
 */
    @PostMapping("/commodityList")
    @ResponseBody
    public Map<String,Object> commodityList(CommodityCustom commodityCustom, @RequestParam(value = "categoryId",required = false) String categoryId,
                                            @RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "limit", required = false) Integer pageSize){

    // 如果商品分类 id 不为空，为商品类设值
        if(StringUtil.isNotEmpty(categoryId)){
            Category category = new Category();
            category.setId(Integer.parseInt(categoryId));
            commodityCustom.setCategory(category);
        }
    // 如果标题不为空，赋值为 模糊查询的格式
        if(StringUtil.isNotEmpty(commodityCustom.getTitle())){
            commodityCustom.setTitle("%" + commodityCustom.getTitle() + "%");
        }

        // 返回数据
        Map<String,Object> result = new HashMap<>();
        List<Commodity> commodityList =  commodityService.findByParam(commodityCustom,page,pageSize);
        Long count = commodityService.getCount(commodityCustom);
System.out.println(commodityList);
        result.put("data",commodityList);
        result.put("count",count);
        result.put("code",0);

        return result;
    }

/**
 * 跳转到商品详情页面
 * 并查询该商品所有信息，（包括所有图片），返回
 */
    @RequestMapping("/toCommodityDetail")
    public ModelAndView toCommodityDetail(Integer id){
        ModelAndView mav = new ModelAndView();
        // 获取商品文本信息
        Commodity commodity = commodityService.findById(id);
        // 获取商品所有图片信息
        List<CommodityImg> commodityImgList = commodity.getCommodityImgList();
        mav.addObject("commodity",commodity);
        mav.addObject("commodityImgList",commodityImgList);
        mav.addObject("id",id);

        mav.setViewName("commodity/commodityDetail");
        return mav;
    }

/**
 * 删除（下架）商品，
 *      并给发布者发送 “商品下架” 通知
 */
    @PostMapping("/delCommodity")
    @ResponseBody
    public AjaxResult delCommodity(@RequestParam("commodityId") Integer commodityId,@RequestParam("title")String title,
                                   @RequestParam("userId") Integer userId){
        AjaxResult result = null;

        int count = commodityService.deleteByid(commodityId);
        if(count > 0){
            result = new AjaxResult(true,"商品已成功下架！");
            // 商品下架通知
            Message message = new Message();
            User user = new User();
            user.setId(userId);
            message.setUser(user);
            message.setTitle(Consts.MSG_TITLE_UNDER);
            message.setContent("你发布的商品 " + "\"" + title + "\" " + Consts.MSG_CONTENT_UNDER);
            message.setState(0);
            message.setCreateTime(new Date());
            count = messageService.publishMsg(message);
            if(count > 0){
                result = new AjaxResult(true,"下架通知已发送！");
            }
        } else{
            result = new AjaxResult(false,"商品下架失败！");
        }

        return result;
    }

    /**
     * 跳转到添加商品页面
     */
    @RequestMapping("/toAddCommodity")
    public String toAddCommodity(){
        return "commodity/addCommodity";
    }

/**
 * 添加商品信息
 *      这里作为测试，没有用户（商品发布者），默认使用 管理员 id: 1
 */
    @PostMapping("/addCommodity")
    @ResponseBody
    public AjaxResult addCommodity(@RequestParam("commodityImgs") MultipartFile[] multipartFiles,Commodity commodity,@RequestParam("categoryId") Integer categoryId){
        AjaxResult result = null;
        // 图片上传到 OSS 服务器
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        // OSS 服务器返回的图片地址
        String img_src = "";
    /**
     * 给商品类补充值
     */
System.out.println("商品轮播图======" + commodity.getSwiper());
        // 将商品分类赋值给 commodity
        Category category = new Category();
        category.setId(categoryId);
        commodity.setCategory(category);
        // 设置商品编号 serial
        commodity.setSerial(UUIDSerial.getSerial());
        // 发布时间
        commodity.setPublishDate(new Date());
        // 商品状态：出售中（不用审核）
        commodity.setState(1);
// 发布者，这里默认 1 号，测试
        User user = new User();
        user.setId(1);
        commodity.setUser(user);
        // 第一张图片作为商品封面
        String coverImage = "";
        // 默认不是轮播图
        try {
            boolean swiper = commodity.getSwiper();
            if(swiper){
                commodity.setSwiper(swiper);
            } else{
                commodity.setSwiper(false);
            }
        } catch (NullPointerException e){
            commodity.setSwiper(false);
        }

        try {
            // 上传图片并返回图片完整路径
            coverImage = ossClientUtil.uploadImageAndGetPath(multipartFiles[0],true);
            commodity.setCoverImage(coverImage);
            // 这里只能先把商品信息存入，才能获得该商品的id
            int count = commodityService.addCommodity(commodity);
            if(count > 0){
                result = new AjaxResult(true,"商品添加成功!");
            } else{
                result = new AjaxResult(false,"商品添加失败!");
            }
            // 商品图片类
            CommodityImg commodityImg = new CommodityImg();
            commodityImg.setCommodityId(commodity.getId());
            for(int i = 0;i < multipartFiles.length; i++){
                img_src = ossClientUtil.uploadImageAndGetPath(multipartFiles[i],true);
                commodityImg.setImgSrc(img_src);
                commodityImgService.add(commodityImg);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // 如果上传文件时异常，则删除之前存入的商品记录
            commodityService.realDelCommodity(commodity.getId());
            return new AjaxResult(false,"图片上传异常!");
        }

    }





/**
 * 返回到前端的数据
 */

/**
 * 返回轮播图
 */
    @GetMapping("/swiperCommodity")
    @ResponseBody
    public List<Commodity> swiperCommodity(){
        List<Commodity> commodityList = commodityService.getSwiperCommodity();
System.out.println("轮播图商品");
System.out.println(commodityList);
        return commodityList;
    }
/**
 * 分页、返回所有出售中的商品列表
 */
    @GetMapping("/pageOfCommodity")
    @ResponseBody
    public List<Commodity> pageOfCommodity(@RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,
                                           @RequestParam("categoryId")Integer categoryId){

        Commodity commodity = new Commodity();
        Category category = new Category();
        // 如果 categoryId == 1，则查询所有类别的，故不设置分类
        if(categoryId == 1){
            categoryId = null;
        }
        category.setId(categoryId);
        commodity.setCategory(category);

        List<Commodity> commodityList = commodityService.findByPage(commodity,page,pageSize);
System.out.println("======分页商品列表======");
System.out.println(commodityList);
System.out.println(commodityList.size());
        return commodityList;
    }

/**
 * 返回商品详情
 */
    @GetMapping("/commodityDetail/{id}")
    @ResponseBody
    public Commodity commodityDetail(@PathVariable("id") Integer id){
        Commodity commodity = commodityService.findById(id);
        return commodity;
    }

/**
 * 按分类查询商品列表
 */
    @GetMapping("/commodityByCategory/{id}")
    @ResponseBody
    public List<Commodity> commodityByCategory(@PathVariable("id") Integer categoryId){
        return commodityService.findByCategoryId(categoryId);
    }

/**
 * 小程序端，提交商品信息
 *      并返回商品 ID，在小程序端上传图片需要绑定 商品 ID
 */
    @PostMapping("/uploadData")
    @ResponseBody
    public Map<String,Object> uploadData(@RequestParam("title")String title,@RequestParam("userId")String userId,
                             @RequestParam("categoryId") Integer categoryId,@RequestParam("price")String price,
                             @RequestParam("oldPrice")String oldPrice,@RequestParam("quality")String quality,
                             @RequestParam("repertory")String repertory,@RequestParam("description")String description){
        // 给商品类赋值
        Commodity commodity = new Commodity();
        User user = new User();
        user.setId(Integer.parseInt(userId));
        Category category = new Category();
        category.setId(categoryId);
        commodity.setTitle(title);
        commodity.setUser(user);
        commodity.setCategory(category);
        commodity.setPrice(Double.parseDouble(price));
        commodity.setOldPrice(Double.parseDouble(oldPrice));
        commodity.setQuality(quality);
        commodity.setRepertory(Integer.parseInt(repertory));
        commodity.setDescription(description);

        // 设置商品编号 serial
        commodity.setSerial(UUIDSerial.getSerial());
        // 默认不是轮播图
        commodity.setSwiper(false);
        // 发布时间
        commodity.setPublishDate(new Date());
        // 商品状态：出售中（不用审核）
        commodity.setState(1);

        // 返回数据
        Map<String,Object> result = new HashMap<>();
        // 添加商品数据
        int count = commodityService.addCommodity(commodity);
        if(count > 0){
            result.put("success",true);
            result.put("message","商品信息添加成功!");
            result.put("commodityId",commodity.getId());
        } else{
            result.put("success",false);
            result.put("message","商品信息添加失败！");
        }

        return result;
    }


/**
 * 小程序端，提交商品图片，存入 commodityImg 表，设置成该商品的封面图
 *      并返回，图片存储的位置
 */
    @PostMapping("/uploadCoverImg")
    @ResponseBody
    public String uploadCoverImg(@RequestParam("commodityId") String commodityId,@RequestParam("imgFile") MultipartFile imgFile){
        // 商品 id为空，说明之前的商品没有上传成功
        if(commodityId == null){
            return null;
        }
        Integer commodityIdInt = Integer.parseInt(commodityId);

        // 商品图片类
        CommodityImg commodityImg = new CommodityImg();
        commodityImg.setCommodityId(commodityIdInt);
        // 商品类
        Commodity commodity = new Commodity();
        commodity.setId(commodityIdInt);
        // 图片上传到 OSS 服务器
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        String img_src = "";
        try {
            // 上传图片并返回图片完整路径
            img_src = ossClientUtil.uploadImageAndGetPath(imgFile,true);
            commodity.setCoverImage(img_src);
            // 修改该id商品的封面图
            commodityService.updateCoverImgOfCommodity(commodity);
            // 数据库添加商品图片
            commodityImg.setImgSrc(img_src);
            commodityImgService.add(commodityImg);

        } catch (Exception e) {
            e.printStackTrace();
            // 如果上传文件时异常，则删除之前存入的商品记录
            commodityService.realDelCommodity(commodityIdInt);
        }

        return img_src;
    }

/**
 * 小程序端，提交商品图片，存入 commodityImg 表
 *      并返回，图片存储的位置
 */
    @PostMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(@RequestParam("commodityId") String commodityId,@RequestParam("imgFile") MultipartFile imgFile){
System.out.println("=====commodityId====" + commodityId);
        // 商品 id为空，说明之前的商品没有上传成功
        if(commodityId == null){
            return null;
        }
        Integer commodityIdInt = Integer.parseInt(commodityId);
        // 图片上传到 OSS 服务器
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        // 商品图片类
        CommodityImg commodityImg = new CommodityImg();
        commodityImg.setCommodityId(commodityIdInt);
        String img_src = "";
        try {
            // 上传图片并返回图片完整路径
            img_src = img_src = ossClientUtil.uploadImageAndGetPath(imgFile,true);
            // 数据库添加商品图片
            commodityImg.setImgSrc(img_src);
            commodityImgService.add(commodityImg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return img_src;
    }
/**
 * 点击商品详情页面，浏览量 +1
 */
    @GetMapping("updateClickCount/{id}")
    @ResponseBody
    public String updateClickCount(@PathVariable("id")Integer id){
        String result = "";
        int count = commodityService.addCommodityClick(id);
        if(count > 0){
            result = "+1";
        } else{
            result = "1";
        }
        return result;
    }
/**
 * 关键字搜索、出售中商品、分页
 */
    @GetMapping("/search")
    @ResponseBody
    public List<Commodity> search(@RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,
                                           @RequestParam("content")String content){

System.out.println("=======page === " + page);
System.out.println("=======pageSize === " + pageSize);
        // 模糊查询处理
        content = "%"+ content + "%";

        List<Commodity> commodityList = commodityService.search(content,page,pageSize);

System.out.println("===搜索===分页商品列表======");
System.out.println(commodityList);
        return commodityList;
    }

}
