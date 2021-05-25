package com.xiaojian.pick.util;

/**
 * @author 小贱
 * @date 2020/10/12 - 19:13
 * 常量类
 */
public class Consts {

    public static final String CURRENT_USER = "currentUser";

    // 发送消息
    // 商品
    public static final String MSG_TITLE_UNDER = "商品下架提醒";
    public static final String MSG_CONTENT_UNDER = "已被举报，经管理员审查，我们已将该商品下架！";
    public static final String MSG_CONTENT_OVERTIME = "30天已到期！现已将该商品下架！";
    // 求购信息
    public static final String MSG_SEEK_TITLE_DEL = "求购信息删除提醒";
    public static final String MSG_SEEK_CONTENT_DEL = "已被举报，经管理员审查，我们已将该求购信息删除！";
    public static final String MSG_SEEK_CONTENT_OVERTIME = "30天已到期！现已将该求购信息删除！";
    // 论坛帖子
    public static final String MSG_TOPIC_TITLE_DEL = "论坛帖子删除提醒";
    public static final String MSG_TOPIC_CONTENT_DEL = "已被举报，经管理员审查，我们已将该帖子信息删除！";


    // 帖子热门所需条件
    // 热门 1
    public static final Integer TOPIC_CLICK_HOT_1 = 100;
    public static final Integer TOPIC_COMMENT_HOT_1 = 30;
    // 热门 2
    public static final Integer TOPIC_CLICK_HOT_2 = 200;
    public static final Integer TOPIC_COMMENT_HOT_2 = 50;
    // 热门 3
    public static final Integer TOPIC_CLICK_HOT_3 = 300;
    public static final Integer TOPIC_COMMENT_HOT_3 = 100;
}
