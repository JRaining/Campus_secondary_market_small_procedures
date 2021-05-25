package com.xiaojian.pick.controller;

import com.sun.mail.imap.protocol.ID;
import com.xiaojian.pick.entity.*;
import com.xiaojian.pick.page.AjaxResult;
import com.xiaojian.pick.page.TopicCustom;
import com.xiaojian.pick.service.CommentService;
import com.xiaojian.pick.service.MessageService;
import com.xiaojian.pick.service.TopicImgService;
import com.xiaojian.pick.service.TopicService;
import com.xiaojian.pick.util.Consts;
import com.xiaojian.pick.util.OSSClientUtil;
import com.xiaojian.pick.util.StringUtil;
import com.xiaojian.pick.util.UUIDSerial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小贱
 * @date 2020/11/22 - 22:04
 */
@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicImgService topicImgService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CommentService commentService;

/**
 * 跳转到帖子列表页面
 */
    @RequestMapping("/toTopic")
    public String toTopic(){
        return "topic/topicList";
    }

/**
 * 返回所有帖子列表
 */
    @PostMapping("/topicList")
    @ResponseBody
    public Map<String,Object> topicList(TopicCustom topicCustom,
                                            @RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "limit", required = false) Integer pageSize){

        // 如果帖子主题不为空，赋值为 模糊查询的格式
        if(StringUtil.isNotEmpty(topicCustom.getTheme())){
            topicCustom.setTheme("%" + topicCustom.getTheme() + "%");
        }
        // 如果标题不为空，赋值为 模糊查询的格式
        if(StringUtil.isNotEmpty(topicCustom.getDescription())){
            topicCustom.setDescription("%" + topicCustom.getDescription() + "%");
        }

        // 返回数据
        Map<String,Object> result = new HashMap<>();
        List<Topic> topicList =  topicService.findByParam(topicCustom,page,pageSize);
        Long count = topicService.getCount(topicCustom);
System.out.println(topicList);
        result.put("data",topicList);
        result.put("count",count);
        result.put("code",0);

        return result;
    }
/**
 * 跳转到发布帖子页面
 */
    @RequestMapping("/toAddTopic")
    public String toAddTopic(){
        return "topic/addTopic";
    }

/**
 * 添加帖子信息
 *      这里作为测试，没有用户（帖子发布者），默认使用 管理员 id: 1
 */
    @PostMapping("/addTopic")
    @ResponseBody
    public AjaxResult addTopic(@RequestParam("topicImgs") MultipartFile[] multipartFiles, Topic topic){
        AjaxResult result = null;
        // 图片上传到 OSS 服务器
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        ossClientUtil.setHomeimagedir("forum/" + StringUtil.getDateForm() + "/");
        // OSS 服务器返回的图片地址
        String img_src = "";
        /**
         * 给帖子类补充值
         */
// 发布者，这里默认 1 号，测试
        User user = new User();
        user.setId(11);
        topic.setUser(user);
        // 点击数
        topic.setClickCount(0);
        // 评论数
        topic.setCommentCount(0);
        // 发布时间
        topic.setPublishDate(new Date());
        // 状态
        topic.setState(1);
        // 默认不是热门，为 0
        topic.setHotDegree(0);

        try {
            // 这里只能先把帖子信息存入，才能获得该帖子的id
            int count = topicService.addTopic(topic);
            if(count > 0){
                result = new AjaxResult(true,"帖子发布成功!");
            } else{
                result = new AjaxResult(false,"帖子发布失败!");
            }
            // 帖子图片类
            TopicImg topicImg = new TopicImg();
            topicImg.setTopicId(topic.getId());
            for(int i = 0;i < multipartFiles.length; i++){
                img_src = ossClientUtil.uploadImageAndGetPath(multipartFiles[i],true);
                topicImg.setImgSrc(img_src);
                topicImgService.addTopicImg(topicImg);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // 如果上传文件时异常，则删除之前存入的帖子记录
            topicService.realDelTopic(topic.getId());
            return new AjaxResult(false,"图片上传异常!");
        }

    }

/**
 * 跳转到帖子详情页面
 * 并查询该帖子所有信息，（包括所有图片、评论），返回
 */
    @RequestMapping("/toTopicDetail")
    public ModelAndView toTopicDetail(Integer id){
        ModelAndView mav = new ModelAndView();
        // 获取帖子文本信息
        Topic topic = topicService.findById(id);
        // 获取帖子所有图片信息
        List<TopicImg> topicImgList = topic.getTopicImgList();
System.out.println("图片");
System.out.println(topicImgList);
        // 获取帖子所有评论
        List<Comment> commentList = topic.getCommentList();
        // 帖子数量
        Integer commentCount = commentList.size();
System.out.println("评论");
System.out.println(commentList);
        mav.addObject("topic",topic);
        mav.addObject("topicImgList",topicImgList);
        mav.addObject("commentList",commentList);
        mav.addObject("commentCount",commentCount);
        mav.addObject("id",id);

        mav.setViewName("topic/topicDetail");
        return mav;
    }



/**
 * 删除（改状态为0）帖子，
 *      并给发布者发送 “帖子删除” 通知
 */
    @PostMapping("/delTopic")
    @ResponseBody
    public AjaxResult delTopic(@RequestParam("topicId") Integer topicId,@RequestParam("theme")String theme,
                                   @RequestParam("userId") Integer userId){
        AjaxResult result = null;

        Topic topic = new Topic();
        topic.setId(topicId);
        // 删除该帖子（状态改为 0）
        topic.setState(0);

        int count = topicService.updateTopicState(topic);
        if(count > 0){
            result = new AjaxResult(true,"帖子已删除！");
            // 帖子删除通知
            Message message = new Message();
            User user = new User();
            user.setId(userId);
            message.setUser(user);
            message.setTitle(Consts.MSG_TOPIC_TITLE_DEL);
            message.setContent("你发布的帖子 " + "\"" + theme + "\" " + Consts.MSG_TOPIC_CONTENT_DEL);
            message.setState(0);
            message.setCreateTime(new Date());
            count = messageService.publishMsg(message);
            if(count > 0){
                result = new AjaxResult(true,"帖子删除通知已发送！");
            }
        } else{
            result = new AjaxResult(false,"帖子删除失败！");
        }

        return result;
    }






/**
 * 返回前端数据
 */
    /**
     * 分页、返回所有帖子
     */
    @GetMapping("/pageOfTopic")
    @ResponseBody
    public List<Topic> pageOfTopic(@RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,
                                   @RequestParam("hotDegree")Integer hotDegree){

System.out.println("=======page === " + page);
System.out.println("=======pageSize === " + pageSize);

        Topic topic = new Topic();
        if(hotDegree != null){
            topic.setHotDegree(hotDegree);
        }

        List<Topic> topicList = topicService.findByPage(topic,page,pageSize);
        return topicList;
    }
/**
 * 返回帖子详情
 */
    @GetMapping("/topicDetail/{id}")
    @ResponseBody
    public Topic topicDetail(@PathVariable("id") Integer id){
        Topic topic = topicService.findById(id);
        return topic;
    }

/**
 * 小程序端，提交帖子信息
 *      并返回帖子 ID，在小程序端上传图片需要绑定 帖子 ID
 */
    @PostMapping("/uploadData")
    @ResponseBody
    public Map<String,Object> uploadData(@RequestParam("userId")Integer userId,
                                         @RequestParam("theme")String theme,@RequestParam("description")String description){
        // 给帖子类赋值
        Topic topic = new Topic();
        User user = new User();
        user.setId(userId);
        topic.setUser(user);
        // 主题
        topic.setTheme(theme);
        // 内容
        topic.setDescription(description);
        // 发布时间
        topic.setPublishDate(new Date());
        // 点击数
        topic.setClickCount(0);
        // 评论数
        topic.setCommentCount(0);
        // 状态
        topic.setState(1);
        // 默认不是热门，为 0
        topic.setHotDegree(0);
System.out.println("-============帖子==========-");
System.out.println(topic);
        // 返回数据
        Map<String,Object> result = new HashMap<>();
        // 添加帖子数据
        int count = topicService.addTopic(topic);
        if(count > 0){
            result.put("success",true);
            result.put("message","帖子发布成功!");
            result.put("topicId",topic.getId());
        } else{
            result.put("success",false);
            result.put("message","帖子发布失败！");
        }

        return result;
    }
/**
 * 小程序端，提交帖子图片，存入 topicImg 表
 *      并返回，图片存储的位置
 */
    @PostMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(@RequestParam("topicId") Integer topicId,@RequestParam("imgFile") MultipartFile imgFile){
System.out.println("=====topicId====" + topicId);
        // 帖子 id为空，说明之前的帖子没有上传成功
        if(topicId == null){
            return null;
        }
        // 图片上传到 OSS 服务器
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        ossClientUtil.setHomeimagedir("forum/");
        // 帖子图片类
        TopicImg topicImg = new TopicImg();
        topicImg.setTopicId(topicId);
        String img_src = "";
        try {
            // 上传图片并返回图片完整路径
            img_src = img_src = ossClientUtil.uploadImageAndGetPath(imgFile,true);
            // 数据库添加帖子图片
            topicImg.setImgSrc(img_src);
            topicImgService.addTopicImg(topicImg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return img_src;
    }

/**
 * 点击帖子详情页面，浏览量 +1
 */
    @GetMapping("updateClickCount/{id}")
    @ResponseBody
    public String updateClickCount(@PathVariable("id")Integer id){
        String result = "";
        int count = topicService.addTopicClick(id);
        if(count > 0){
            result = "+1";
            // 满足条件，修改帖子为热门
            Topic topic = topicService.findById(id);
            Integer clickCount = topic.getClickCount();
            Integer hotDegree = topic.getHotDegree();
            if(clickCount > Consts.TOPIC_CLICK_HOT_3 && hotDegree != 3){
                topic.setHotDegree(3);
                topicService.updateTopicHot(topic);
            } else if(clickCount > Consts.TOPIC_CLICK_HOT_2 && hotDegree != 2){
                topic.setHotDegree(2);
                topicService.updateTopicHot(topic);
            } else if(clickCount > Consts.TOPIC_CLICK_HOT_1 && hotDegree != 1){
                topic.setHotDegree(1);
                topicService.updateTopicHot(topic);
            }

        } else{
            result = "1";
        }
        return result;
    }

/**
 * 小程序端提交，帖子评论信息
 *      并给 帖子的评论数 + 1
 */
    @PostMapping("/addComment")
    @ResponseBody
    public AjaxResult addComment(@RequestParam("content")String content,@RequestParam("userId")Integer userId,
                                      @RequestParam("topicId")Integer topicId){

        AjaxResult result = null;
        User user = new User();
        user.setId(userId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setTopicId(topicId);
        comment.setPublishDate(new Date());
        comment.setState(1);

        int count = commentService.addComment(comment);
        if(count > 0){
            // 帖子的评论数 + 1
            topicService.addTopicComment(topicId);
            result = new AjaxResult(true,"评论已发送！");

            // 满足条件，修改帖子为热门
            Topic topic = topicService.findById(topicId);
            Integer commentCount = topic.getCommentCount();
            Integer hotDegree = topic.getHotDegree();
            if(commentCount > Consts.TOPIC_COMMENT_HOT_3 && hotDegree != 3){
                topic.setHotDegree(3);
                topicService.updateTopicHot(topic);
            } else if(commentCount > Consts.TOPIC_COMMENT_HOT_2 && hotDegree != 2){
                topic.setHotDegree(2);
                topicService.updateTopicHot(topic);
            } else if(commentCount > Consts.TOPIC_COMMENT_HOT_1 && hotDegree != 1){
                topic.setHotDegree(1);
                topicService.updateTopicHot(topic);
            }
        } else{
            result = new AjaxResult(false,"发送失败");
        }

        return result;
    }

/**
 * 修改评论状态 为 0
 *      并给 帖子的评论数 - 1
 */
    @PostMapping("delComment")
    @ResponseBody
    public AjaxResult delComment(@RequestParam("commentId")Integer commentId,@RequestParam("topicId")Integer topicId){
        AjaxResult result = null;

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setState(0);
        int count = commentService.updateCommentState(comment);
        if(count > 0){
            // 帖子的评论数 -1
            topicService.subTopicComment(topicId);
            result = new AjaxResult(true,"评论已删除！");
        } else{
            result = new AjaxResult(false,"删除失败！");
        }
        return result;
    }


/**
 * 关键字搜索帖子、分页
 */
    @GetMapping("/search")
    @ResponseBody
    public List<Topic> search(@RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,
                             @RequestParam("content")String content){

System.out.println("=======page === " + page);
System.out.println("=======pageSize === " + pageSize);
        // 模糊查询处理
        content = "%"+ content + "%";

        List<Topic> seekList = topicService.search(content,page,pageSize);
System.out.println("===搜索===分页帖子列表======");
System.out.println(seekList);
        return seekList;
    }

}
