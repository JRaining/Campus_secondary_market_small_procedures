package com.xiaojian.pick.controller;

import com.sun.mail.imap.protocol.ID;
import com.xiaojian.pick.entity.*;
import com.xiaojian.pick.page.AjaxResult;
import com.xiaojian.pick.page.SeekCustom;
import com.xiaojian.pick.service.InformService;
import com.xiaojian.pick.service.MessageService;
import com.xiaojian.pick.service.SeekService;
import com.xiaojian.pick.util.Consts;
import com.xiaojian.pick.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小贱
 * @date 2020/10/24 - 16:04
 */
@Controller
@RequestMapping("/seek")
public class SeekController {

    @Autowired
    private SeekService seekService;
    @Autowired
    private InformService informService;
    @Autowired
    private MessageService messageService;


/**
 * 跳转到 求购中心
 */
    @RequestMapping("/toSeek")
    public String toSeek(){
        return "seek/seekList";
    }

/**
 * 求购列表
 */
    @PostMapping("/seekList")
    @ResponseBody
    public Map<String,Object> seekList(SeekCustom seekCustom,
                                       @RequestParam(value = "page",required = false)Integer page,
                                       @RequestParam(value = "limit",required = false)Integer pageSize){


        Map<String,Object> result = new HashMap<>();
    // 如果标题不为空，赋值为 模糊查询的格式
        if(StringUtil.isNotEmpty(seekCustom.getTitle())){
            seekCustom.setTitle("%" + seekCustom.getTitle() + "%");
        }
        List<Seek> seekList = seekService.findByParam(seekCustom,page,pageSize);
        Long count = seekService.getCount(seekCustom);

        result.put("data",seekList);
        result.put("count",count);
        result.put("code",0);

        return result;
    }
/**
 * 跳转到添加求购表单页面
 */
    @GetMapping("/toAddSeek")
    public String toAddSeek(){
        return "seek/addSeek";
    }

/**
 * 后台，添加求购信息
 *      添加操作：一般参数会携带用户信息
 */
    @PostMapping("/addSeek")
    @ResponseBody
    public AjaxResult addSeek(Seek seek, User user){

        AjaxResult result = null;
System.out.println("==========后台添加求购信息");
System.out.println(seek);
System.out.println("++========用户信息");
System.out.println(user);
        // 用户为：后台管理员
        if(user.getId() == null){
            user.setId(1);
            seek.setUser(user);
        }
        // 默认求购信息状态：1
        seek.setState(1);
        // 求购时间
        seek.setPublishDate(new Date());
        int count = seekService.addSeek(seek);
        if(count > 0){
            result = new AjaxResult(true,"求购信息添加成功！");
        } else{
            result = new AjaxResult(false,"添加失败！");
        }

        return result;
    }

/**
 * 跳转到编辑求购信息页面
 *  并进行数据回显
 */
    @GetMapping("/toEditSeek/{id}")
    public ModelAndView toEditSeek(@PathVariable("id")Integer id){

        ModelAndView mav = new ModelAndView();
        Seek seek = seekService.findById(id);
        if(seek == null){
            mav.addObject("errorMsg","数据请求失败！");
        } else{
            mav.addObject("seek",seek);
        }
        mav.setViewName("seek/editSeek");
        return mav;
    }

/**
 * 删除求购信息
 *      将求购信息状态改为 0
 */
    @PostMapping("/delSeek")
    @ResponseBody
    public AjaxResult delSeek(@RequestParam("seekId") Integer seekId,@RequestParam("title")String title,
                              @RequestParam("userId")Integer userId){

        AjaxResult result = null;

        Seek seek = new Seek();
        seek.setId(seekId);
        // 状态设为 0
        seek.setState(0);

System.out.println("=========================求购信息详情：");
System.out.println(seek);
        // 如果该求购信息有举报信息
        int count = seekService.updateSeekState(seekId);
        if(count > 0){
            result = new AjaxResult(true,"求购信息已删除！");
            // 商品下架通知
            Message message = new Message();
            User user = new User();
            user.setId(userId);
            message.setUser(user);
            message.setTitle(Consts.MSG_SEEK_TITLE_DEL);
            message.setContent("你发布的求购信息 " + "\"" + title + "\" " + Consts.MSG_SEEK_CONTENT_DEL);
            message.setState(0);
            message.setCreateTime(new Date());
            count = messageService.publishMsg(message);
            if(count > 0){
                result = new AjaxResult(true,"删除通知已发送！");
            }
        } else{
            result = new AjaxResult(false,"删除失败！");
        }

        return result;
    }


/**
 * 返回给小程序前端的内容
 */
    /**
     * 返回所有的求购信息
     */
    /**
     * 分页、返回所有出售中的商品列表
     */
    @GetMapping("/pageOfSeek")
    @ResponseBody
    public List<Seek> pageOfSeek(@RequestParam("page")Integer page, @RequestParam("pageSize")Integer pageSize){

        List<Seek> seekList = seekService.findByPage(page,pageSize);
        return seekList;
    }
/**
 * 小程序添加求购信息
 */
    @PostMapping("/uploadSeek")
    @ResponseBody
    public AjaxResult uploadSeek(@RequestParam("userId")String userId,@RequestParam("title")String title,@RequestParam("minPrice")String minPrice,
                             @RequestParam("maxPrice")String maxPrice,@RequestParam("remark")String remark){

        AjaxResult result = null;

        Seek seek = new Seek();
        User user = new User();
        user.setId(Integer.parseInt(userId));
        seek.setTitle(title);
        seek.setMinPrice(Double.parseDouble(minPrice));
        seek.setMaxPrice(Double.parseDouble(maxPrice));
        seek.setRemark(remark);
        // 求购信息默认状态 1
        seek.setState(1);
        seek.setUser(user);
        seek.setPublishDate(new Date());

        int count = seekService.addSeek(seek);
        if(count > 0){
            result = new AjaxResult(true,"求购信息添加成功！");
        } else{
            result = new AjaxResult(false,"求购信息添加失败！");
        }

        return result;
    }

/**
 * 关键字搜索求购、分页
 */
    @GetMapping("/search")
    @ResponseBody
    public List<Seek> search(@RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,
                                  @RequestParam("content")String content){

System.out.println("=======page === " + page);
System.out.println("=======pageSize === " + pageSize);
        // 模糊查询处理
        content = "%"+ content + "%";

        List<Seek> seekList = seekService.search(content,page,pageSize);

System.out.println("===搜错===分页求购列表======");
System.out.println(seekList);
        return seekList;
    }

}
