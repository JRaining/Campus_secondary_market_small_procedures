package com.xiaojian.pick.controller;

import com.xiaojian.pick.entity.*;
import com.xiaojian.pick.page.AjaxResult;
import com.xiaojian.pick.service.InformService;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小贱
 * @date 2020/10/25 - 17:04
 */
@Controller
@RequestMapping("/inform")
public class InformController {

    @Autowired
    private InformService informService;

/**
 * 跳转到举报信息列表
 */
    @RequestMapping("/toInform")
    public String toInform(){
        return "inform/informList";
    }
/**
 * 举报信息列表
 */
    @RequestMapping("/informList")
    @ResponseBody
    public Map<String,Object> informList(Inform inform,
                                         @RequestParam(value = "page",required = false)Integer page,
                                         @RequestParam(value = "limit",required = false)Integer pageSize){
        Map<String,Object> result = new HashMap();


        List<Inform> informList = informService.findByParam(inform,page,pageSize);
        Long count = informService.getCount(inform);

        result.put("data",informList);
        result.put("count",count);
        result.put("code",0);

        return result;
    }

/**
 * 修改举报信息状态：1，已处理
 */
    @PostMapping("/updateInform")
    @ResponseBody
    public AjaxResult updateInform(@RequestParam("id")Integer id){
        AjaxResult result = null;

        int count = informService.updateInform(id);
        if(count > 0){
            result = new AjaxResult(true,"完成处理！");
        } else{
            result = new AjaxResult(false,"未知错误！");
        }

        return result;
    }

/**
 * 小程序端
 */


/**
 * 小程序端提交，商品举报信息
 */
    @PostMapping("/commodityInform")
    @ResponseBody
    public AjaxResult commodityInform(@RequestParam("content")String content,@RequestParam("userId")Integer userId,
                                      @RequestParam("commodityId")Integer commodityId){

        AjaxResult result = null;
        User user = new User();
        user.setId(userId);
        Commodity commodity = new Commodity();
        commodity.setId(commodityId);
        Inform inform = new Inform();
        inform.setUser(user);
        inform.setCommodity(commodity);
        inform.setContent(content);
        inform.setCreateTime(new Date());
System.out.println("inform====" + inform);
        int count = informService.addInform(inform);
        if(count > 0){
            result = new AjaxResult(true,"举报信息已发送！");
        } else{
            result = new AjaxResult(false,"发送失败");
        }

        return result;
    }
/**
 * 小程序端提交，求购举报信息
 */
    @PostMapping("/seekInform")
    @ResponseBody
    public AjaxResult seekInform(@RequestParam("content")String content,@RequestParam("userId")Integer userId,
                                      @RequestParam("seekId")Integer seekId){
        AjaxResult result = null;
        User user = new User();
        user.setId(userId);
        Seek seek = new Seek();
        seek.setId(seekId);
        Inform inform = new Inform();
        inform.setUser(user);
        inform.setSeek(seek);
        inform.setContent(content);
        inform.setCreateTime(new Date());
System.out.println("====求购举报====");
System.out.println(inform);
        int count = informService.addInform(inform);
        if(count > 0){
            result = new AjaxResult(true,"举报信息已发送！");
        } else{
            result = new AjaxResult(false,"发送失败");
        }

        return result;
    }

/**
 * 小程序端提交，帖子举报信息
 */
    @PostMapping("/topicInform")
    @ResponseBody
    public AjaxResult topicInform(@RequestParam("content")String content,@RequestParam("userId")Integer userId,
                                      @RequestParam("topicId")Integer topicId){

        AjaxResult result = null;
        User user = new User();
        user.setId(userId);
        // 被举报的帖子
        Topic topic = new Topic();
        topic.setId(topicId);
        // 举报类
        Inform inform = new Inform();
        inform.setUser(user);
        inform.setTopic(topic);
        inform.setContent(content);
        inform.setCreateTime(new Date());
System.out.println("inform====" + inform);
        int count = informService.addInform(inform);
        if(count > 0){
            result = new AjaxResult(true,"举报信息已发送！");
        } else{
            result = new AjaxResult(false,"发送失败");
        }

        return result;
    }

}
