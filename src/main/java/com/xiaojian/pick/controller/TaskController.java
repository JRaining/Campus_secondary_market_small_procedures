package com.xiaojian.pick.controller;

import com.xiaojian.pick.service.TimedTaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * @author 小贱
 * @date 2020/11/25 - 15:35
 *
 * 服务器定时任务
 *      定时删除数据库中无状态（已被删除的）数据
 */
@Controller
public class TaskController {

    private TimedTaskService timedTaskService;

    // 30天 下架商品、求购信息改为0

    public void timedDel(){
//        timedTaskService.timedDelCommodity();
//        timedTaskService.timedDelSeek();
    }


}
