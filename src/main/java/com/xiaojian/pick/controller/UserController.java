package com.xiaojian.pick.controller;

import com.xiaojian.pick.entity.*;
import com.xiaojian.pick.page.AjaxResult;
import com.xiaojian.pick.page.ReturnData;
import com.xiaojian.pick.service.*;
import com.xiaojian.pick.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author 小贱
 * @date 2020/10/31 - 16:48
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CommodityImgService commodityImgService;
    @Autowired
    private SeekService seekService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicImgService topicImgService;
    @Autowired
    private AttentionService attentionService;


/**
 *  跳转到用户列表页面
 */
   @RequestMapping("/toUser")
    public String toUser(){
       return "user/userList";
   }
/**
 * 跳转到消息发送页面
 */
    @RequestMapping("/toSendMsg")
    public ModelAndView toSendMsg(@RequestParam("userId") Integer userId){
        ModelAndView mav = new ModelAndView();

        mav.addObject("userId",userId);
        mav.setViewName("user/sendMsg");
        return mav;
    }

/**
 * 返回用户列表
 */
    @PostMapping("/userList")
    @ResponseBody
    public Map<String,Object> userList(User user,@RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "limit", required = false) Integer pageSize){

    // 如果昵称不为空，赋值为 模糊查询的格式
        if(StringUtil.isNotEmpty(user.getNickName())){
            user.setNickName("%" + user.getNickName() + "%");
        }

        Map<String,Object> result = new HashMap<>();

        List<User> userList = userService.findByParam(user,page,pageSize);
        Long count = userService.getCount(user);

        result.put("data",userList);
        result.put("count",count);
        result.put("code",0);

        return result;
    }

/**
 * 发送消息给用户
 */
    @PostMapping("/sendMsg")
    @ResponseBody
    public AjaxResult sendMsg(Message message,@RequestParam("userId")Integer userId){
        AjaxResult result = null;

        User user = new User();
        user.setId(userId);
        message.setUser(user);
        message.setState(0);
        message.setCreateTime(new Date());
System.out.println("消息:" + message);
        int count = messageService.publishMsg(message);
        if(count > 0){
            result = new AjaxResult(true,"消息发送成功!");
        } else{
            result = new AjaxResult(false,"发送失败!");
        }

        return result;

    }



/**
 * 禁用该用户发布商品的功能
 * 恢复该用户发布商品的功能
 */
    @PostMapping("/updateHaveUserInfo")
    @ResponseBody
    public AjaxResult disableUser(@RequestParam("openid")String openid,@RequestParam("haveUserInfo") String haveUserInfo){
        AjaxResult result = null;

        User user = new User();
        user.setOpenid(openid);
        user.setHaveUserInfo(haveUserInfo);

       int count = userService.updateUser(user);
       if(count > 0){
           result = new AjaxResult(true,"操作成功");
       } else{
           result = new AjaxResult(false,"操作失败");
       }

        return result;
    }






/**
 * 小程序端登录
 * 用户登录
 */
    @PostMapping("/login/{code}")
    @ResponseBody
    public ReturnData login(@PathVariable("code") String code){

        return userService.loginByCode(code);
    }

/**
 *  登录用户信息完善，但是这里还是没有 QQ号 和 WeCaht
 * @return
 */
    @PostMapping("/updateOne")
    @ResponseBody
    public ReturnData updateOne(@RequestParam("openid") String openid,@RequestParam("nickName") String nickName,
                                @RequestParam("avatarUrl") String avatarUrl,@RequestParam("gender")Integer gender){
        User user = new User();
        user.setOpenid(openid);
        user.setNickName(nickName);
        user.setAvatarUrl(avatarUrl);
        user.setGender((byte) gender.intValue());
        user.setUpdateTime(new Date());
        // 用户信息初步完善
        user.setHaveUserInfo("1");

System.out.println("用户信息=============" + user);

        ReturnData returnData = null;
        int count = userService.updateUser(user);
        if(count > 0){
            user = userService.findByOpenid(openid);
            returnData = new ReturnData(true,"用户信息已经初步完善！",User.HAVE_USER_INFO,user);
        } else{
            user.setHaveUserInfo("0");
            returnData = new ReturnData(false,"用户信息更新失败！",User.NOT_USER_INFO,user);
        }

        return returnData;
    }
/**
 *  登录用户信息完善，用户添加 QQ号 和 WeCaht
 * @return
 */
    @PostMapping("/updateComplete")
    @ResponseBody
    public ReturnData updateComplete(@RequestParam("openid") String openid,
                                     @RequestParam("qqNum") String qqNum,@RequestParam("wechatNum") String wechatNum){
        ReturnData returnData = null;
        if(StringUtil.isEmpty(openid)){
            return new ReturnData(false,"用户信息更新失败！");
        }

        User user = new User();
        user.setOpenid(openid);
        user.setQqNum(qqNum);
        user.setWechatNum(wechatNum);
        user.setHaveUserInfo(User.ALL_USER_INFO);
        user.setUpdateTime(new Date());

        int count = userService.updateUser(user);
        if(count > 0){
            user = userService.findByOpenid(openid);
            returnData = new ReturnData(true,"用户信息已完善！",User.ALL_USER_INFO,user);
        } else{
            returnData = new ReturnData(false,"用户信息更新失败！");
        }


        return returnData;
    }

/**
 * 返回给小程序端的数据
 */

/**
 *  获取用户信息
 */
    @PostMapping("/getHaveUserInfo")
    @ResponseBody
    public ReturnData getHaveUserInfo(@RequestParam("openid") String openid){
        ReturnData returnData = null;

        User user = userService.findByOpenid(openid);
        if(user != null){
            returnData = new ReturnData(true,"用户信息已完善！",user.getHaveUserInfo(),user);
        } else{
            returnData = new ReturnData(false,"用户信息获取失败！");
        }
        return returnData;
    }



/**
 * 用户发布的商品列表
 */
    @GetMapping("/getCommodityList/{userId}")
    @ResponseBody
    public List<Commodity> getCommodityList(@PathVariable("userId") Integer userId){
        List<Commodity> commodityList = commodityService.findByUserId(userId);
        return commodityList;
    }
/**
 * 小程序中查看一个用户发布的商品（售出中的）
 */
    @GetMapping("/getUserPublish/{userId}")
    @ResponseBody
    public List<Commodity> getUserPublish(@PathVariable("userId") Integer userId){
        Commodity commodity = new Commodity();
        User user = new User();
        user.setId(userId);
        commodity.setUser(user);
        // 售出中的商品
        commodity.setState(1);

        List<Commodity> commodityList = commodityService.getUserPublish(commodity);
        return commodityList;
    }

/**
 * 更新商品状态
 */
    @PostMapping("/updateCommodityState")
    @ResponseBody
    public AjaxResult updateCommodityState(@RequestParam("id")Integer id,@RequestParam("state") Integer state){
        AjaxResult result = null;
System.out.println("商品状态、id"+ state + "/" + id);
        int count = commodityService.updateCommodityState(state,id);
        if(count > 0){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }
/**
 *  删除商品记录
 *      商品状态改为 4
 */
    @PostMapping("/delCommodity/{id}")
    @ResponseBody
    public AjaxResult delCommodity(@PathVariable("id")Integer id){
        AjaxResult result = null;

        int count = commodityService.updateCommodityState(4,id);
        if(count > 0){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }
/**
 * 用户发布的求购列表
 */
    @GetMapping("/getSeekList/{userId}")
    @ResponseBody
    public List<Seek> getSeekList(@PathVariable("userId")Integer userId){
        List<Seek> seekList = seekService.findByUserId(userId);
        return seekList;
    }
/**
 * 删除求购信息
 *      将求购信息状态改为 0
 */
    @GetMapping("/deleteSeek/{id}")
    @ResponseBody
    public AjaxResult deleteSeek(@PathVariable("id")Integer id){
        AjaxResult result = null;

        // 更新求购信息，主要修改求购信息状态
        Seek seek = new Seek();
        seek.setId(id);
        seek.setState(0);
        int count = seekService.updateSeekState(id);
        if(count > 0){
            result = new AjaxResult(true,"求购信息已删除！");
        } else{
            result = new AjaxResult(false,"删除失败！");
        }

        return result;
    }


/**
 * 用户的收藏列表
 */
    @GetMapping("/getCollectList/{userId}")
    @ResponseBody
    public List<Collect> getCollectList(@PathVariable("userId")Integer userId){
        List<Collect> collectList = collectService.findByUserId(userId);
System.out.println("=====collectList====");
System.out.println(collectList);
        return collectList;
    }
/**
 * 获取用户与商品间是否有收藏关系
 */
    @GetMapping("/startCollectState")
    @ResponseBody
    public AjaxResult startCollectState(@RequestParam("userId")Integer userId,@RequestParam("commodityId")Integer commodityId){
        AjaxResult result = null;
        Commodity commodity = new Commodity();
        commodity.setId(commodityId);
        User user = new User();
        user.setId(userId);

        Collect collect = new Collect();
        collect.setCommodity(commodity);
        collect.setUser(user);
        collect.setCreateTime(new Date());

        Collect count = collectService.findCollect(collect);
        if(count != null){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }
/**
 * 添加收藏信息
 */
    @PostMapping("/addCollect")
    @ResponseBody
    public AjaxResult addCollect(@RequestParam("userId")Integer userId,@RequestParam("commodityId")Integer commodityId){

        AjaxResult result = null;
        Commodity commodity = new Commodity();
        commodity.setId(commodityId);
        User user = new User();
        user.setId(userId);

        Collect collect = new Collect();
        collect.setCommodity(commodity);
        collect.setUser(user);
        collect.setCreateTime(new Date());

        int count = collectService.addCollect(collect);
        if(count > 0){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }
/**
 * 删除用户收藏
 */
    @GetMapping("/deleteCollect/{id}")
    @ResponseBody
    public AjaxResult deleteCollect(@PathVariable("id")Integer id){
        AjaxResult result = null;

        int count = collectService.deleteCollect(id);
        if(count > 0){
            result = new AjaxResult(true,"删除成功！");
        } else{
            result = new AjaxResult(false,"删除失败！");
        }

        return result;
    }
/**
 * 取消收藏，在商品详情中，取消收藏
 */
    @PostMapping("/cancelCollect")
    @ResponseBody
    public AjaxResult cancelCollect(@RequestParam("userId")Integer userId,@RequestParam("commodityId")Integer commodityId){

        AjaxResult result = null;
        Commodity commodity = new Commodity();
        commodity.setId(commodityId);
        User user = new User();
        user.setId(userId);

        Collect collect = new Collect();
        collect.setCommodity(commodity);
        collect.setUser(user);
        collect.setCreateTime(new Date());

        int count = collectService.cancelCollect(collect);
        if(count > 0){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }


/**
 *  用户的消息列表
 */
    @GetMapping("/getMessageList/{userId}")
    @ResponseBody
    public List<Message> getMessageList(@PathVariable("userId")Integer userId){
        List<Message> messageList = messageService.findByUserId(userId);
System.out.println("======消息列表");
System.out.println(messageList);
        return messageList;
    }
/**
 * 修改消息阅读状态（0，未读；1，已读；2，删除状态）
 */
    @PostMapping("/updateMessageState")
    @ResponseBody
    public AjaxResult updateMessageState(@RequestParam("id")Integer id,@RequestParam("state") Integer state){
        AjaxResult result = null;
        Message message = new Message();
        message.setId(id);
        message.setState(state);
System.out.println("消息的状态、id"+ state + "/" + id);
        int count = messageService.updateMsgState(message);
        if(count > 0){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }
/**
 * 获取用户的消息数
 */
    @GetMapping("/getMsgCount/{userId}")
    @ResponseBody
    public Long getMsgCount(@PathVariable("userId")Integer userId){

        Long count = messageService.getCountByUserId(userId);
System.out.println("该用户消息数为：" + count);
        return count;
    }




/**
 * 论坛模块
 */
/**
 * 用户发布的帖子列表
 */
    @GetMapping("/getTopicList/{userId}")
    @ResponseBody
    public List<Topic> getTopicList(@PathVariable("userId") Integer userId){
        List<Topic>  topicList = topicService.findByUserId(userId);
        return topicList;
    }

/**
 * 用户的话题关注列表
 */
    @GetMapping("/getAttentionList/{userId}")
    @ResponseBody
    public List<Attention> getAttentionList(@PathVariable("userId")Integer userId){
        List<Attention> attentionList = attentionService.findByUserId(userId);
System.out.println("=====attentionList====");
System.out.println(attentionList);
        return attentionList;
    }

/**
 * 获取用户与帖子间是否有关注关系
 */
    @GetMapping("/startAttentionState")
    @ResponseBody
    public AjaxResult startAttentionState(@RequestParam("userId")Integer userId,@RequestParam("topicId")Integer topicId){
        AjaxResult result = null;

        Topic topic = new Topic();
        topic.setId(topicId);
        User user = new User();
        user.setId(userId);

        Attention attention = new Attention();
        attention.setTopic(topic);
        attention.setUser(user);
        attention.setCreateTime(new Date());

        Attention count = attentionService.findAttention(attention);
        if(count != null){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }

/**
 * 添加关注信息
 */
    @PostMapping("/addAttention")
    @ResponseBody
    public AjaxResult addAttention(@RequestParam("userId")Integer userId,@RequestParam("topicId")Integer topicId){
        AjaxResult result = null;

        Topic topic = new Topic();
        topic.setId(topicId);
        User user = new User();
        user.setId(userId);

        Attention attention = new Attention();
        attention.setTopic(topic);
        attention.setUser(user);
        attention.setCreateTime(new Date());

        int count = attentionService.addAttention(attention);
        if(count > 0){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }
/**
 * 删除用户关注
 */
    @GetMapping("/deleteAttention/{id}")
    @ResponseBody
    public AjaxResult deleteAttention(@PathVariable("id")Integer id){
        AjaxResult result = null;

        int count = attentionService.deleteAttention(id);
        if(count > 0){
            result = new AjaxResult(true,"已取消关注！");
        } else{
            result = new AjaxResult(false,"取消失败！");
        }

        return result;
    }
/**
 * 取消关注，在帖子详情中，取消关注
 */
    @PostMapping("/cancelAttention")
    @ResponseBody
    public AjaxResult cancelAttention(@RequestParam("userId")Integer userId,@RequestParam("topicId")Integer topicId){
        AjaxResult result = null;

        Topic topic = new Topic();
        topic.setId(topicId);
        User user = new User();
        user.setId(userId);

        Attention attention = new Attention();
        attention.setTopic(topic);
        attention.setUser(user);
        attention.setCreateTime(new Date());

        int count = attentionService.cancelAttention(attention);
        if(count > 0){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }
/**
 *  删除帖子
 *      帖子状态改为 0
 */
    @PostMapping("/delTopic/{id}")
    @ResponseBody
    public AjaxResult delTopic(@PathVariable("id")Integer id){
        AjaxResult result = null;

        Topic topic = new Topic();
        topic.setId(id);
        topic.setState(0);

        int count = topicService.updateTopicState(topic);
        if(count > 0){
            result = new AjaxResult(true);
        } else{
            result = new AjaxResult(false);
        }
        return result;
    }

}
