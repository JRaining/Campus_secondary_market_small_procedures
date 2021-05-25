package com.xiaojian.pick.controller;

import com.xiaojian.pick.entity.*;
import com.xiaojian.pick.page.AjaxResult;
import com.xiaojian.pick.page.CommodityCustom;
import com.xiaojian.pick.page.SeekCustom;
import com.xiaojian.pick.page.TopicCustom;
import com.xiaojian.pick.service.*;
import com.xiaojian.pick.util.Consts;
import com.xiaojian.pick.util.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 小贱
 * @date 2020/10/12 - 9:25
 */
@Controller
public class LoginController {

    @Autowired
    private SysAdminService sysAdminService;
    @Autowired
    private InformService informService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private SeekService seekService;
    @Autowired
    private TopicService topicService;

/**
 * 首页
 *      在进入首页时，加载首页所需数据（举报数、商品发布数、求购数）
 *      并将这些数据存入 Redis
 */
    @RequestMapping({"/","/index"})
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();

        // 查询举报信息未处理的数量
        Inform inform = new Inform();
        inform.setState(false);
        Long count = informService.getCount(inform);

        mav.addObject("informCount",count);
        mav.setViewName("index");

        return mav;
    }

/**
 * 首页主体
 * 在进入首页时，加载首页所需数据（举报数、商品发布数、求购数）
 */
    @RequestMapping("/main")
    public ModelAndView main(){
        ModelAndView mav = new ModelAndView();
        // 返回到页面数据
        Map<String,Object> result = new HashMap<>();

        // 查询举报信息未处理的数量
        Inform inform = new Inform();
        inform.setState(false);
        Long count = informService.getCount(inform);
        result.put("informCount",count);

        // 查询商品数，今日发布，所有商品状态（出售中，售罄，下架）
        CommodityCustom commodityCustom = new CommodityCustom();
        // 出售中商品
        commodityCustom.setState(1);
        Long onOfferCommodity = commodityService.getCount(commodityCustom);
        result.put("onOfferCommodity",onOfferCommodity);
        // 售罄商品
        commodityCustom.setState(2);
        Long sellOutCommodity = commodityService.getCount(commodityCustom);
        result.put("sellOutCommodity",sellOutCommodity);
        // 下架商品
        commodityCustom.setState(3);
        Long outCommodity = commodityService.getCount(commodityCustom);
        result.put("outCommodity",outCommodity);
        // 今日发布
        commodityCustom.setState(null);
        commodityCustom.setPublishDate(new Date());
        Long commodityToday = commodityService.getCount(commodityCustom);
        result.put("commodityToday",commodityToday);

        // 求购数量
        SeekCustom seekCustom = new SeekCustom();
        // 所有数量
        Long allSeek = seekService.getCount(seekCustom);
        result.put("allSeek",allSeek);
        // 今日求购
        seekCustom.setPublishDate(new Date());
        Long seekToday = seekService.getCount(seekCustom);
        result.put("seekToday",seekToday);

        // 帖子数量
        TopicCustom topicCustom = new TopicCustom();
        topicCustom.setState(1);
        // 所有数量
        Long allTopic = topicService.getCount(topicCustom);
        result.put("allTopic",allTopic);
        // 今日帖子
        topicCustom.setPublishDate(new Date());
        Long topicToday = topicService.getCount(topicCustom);
        result.put("topicToday",topicToday);
        // 热门帖子
        topicCustom.setPublishDate(null);
        topicCustom.setHotDegree(4);
        Long hotTopic = topicService.getCount(topicCustom);
        result.put("hotTopic",hotTopic);

System.out.println("+++++++++帖子++++++++");
        System.out.println(allTopic + "," + topicToday + "," + hotTopic);


        mav.addObject("result",result);
        mav.setViewName("main");
        return mav;
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录
     */
    @ResponseBody
    @PostMapping("/login")
    public AjaxResult login(String username ,String password, HttpSession session){

        AjaxResult result = null;

        // 加密密码
        password = CryptographyUtil.md5(password,CryptographyUtil.SALT);
        // 1. 获取 Subject
        Subject subject = SecurityUtils.getSubject();
        // 2. 封装用户信息
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try{
            subject.login(token);
            SysAdmin admin = sysAdminService.findByUsername(username);
            result = new AjaxResult(true,"登录成功！");
            // 登录成功，将用户信息存入session中
            session.setAttribute(Consts.CURRENT_USER,admin);
        } catch (UnknownAccountException e){
            // 登录失败，用户名错误
            result = new AjaxResult(false,"用户名错误！");
        } catch (IncorrectCredentialsException e){
            // 登录失败，密码错误
            result = new AjaxResult(false,"密码错误！");
        }

        return result;
    }

}
