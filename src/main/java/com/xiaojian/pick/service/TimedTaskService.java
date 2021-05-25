package com.xiaojian.pick.service;

/**
 * @author 小贱
 * @date 2020/11/25 - 21:36
 */
public interface TimedTaskService {
    void timedDelCommodity();
    void timedDelSeek();

    void realDelUserMessage();
    void realDelSeek();
    void realDelCommodity();
    void realDelTopic();
}
