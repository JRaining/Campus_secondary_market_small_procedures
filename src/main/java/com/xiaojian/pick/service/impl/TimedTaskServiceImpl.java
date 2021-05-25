package com.xiaojian.pick.service.impl;

import com.xiaojian.pick.entity.*;
import com.xiaojian.pick.mapper.*;
import com.xiaojian.pick.service.TimedTaskService;
import com.xiaojian.pick.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 小贱
 * @date 2020/11/17 - 8:33
 */
@Service
public class TimedTaskServiceImpl implements TimedTaskService {

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private CommodityImgMapper commodityImgMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private SeekMapper seekMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private InformMapper informMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TopicImgMapper topicImgMapper;
    @Autowired
    private AttentionMapper attentionMapper;
    @Autowired
    private CommentMapper commentMapper;


/**
 * 30 天后下架商品
 *      并发送商品下架通知
 */
    @Scheduled(cron = "0 0 3 * * ?")
    public void timedDelCommodity(){
System.out.println("55了，执行了么？？？");
        List<Commodity> commodityList = commodityMapper.overtimeCommodity();
System.out.println("========================");
System.out.println(commodityList);
        // 数据列表不为空
        if(commodityList.size() > 0){
            // 商品 id
            int commodityId = 0;
            // 商品 标题
            String title = "";
            // 用户 id
            int userId = 0;
            // 商品下架通知
            Message message = new Message();
            User user = new User();
            // 删除数量
            int count = 0 ;
            for(int i = 0 ; i <  commodityList.size(); i++){
                commodityId = commodityList.get(i).getId();
                userId = commodityList.get(i).getUser().getId();
                // 商品标题
                title = commodityList.get(i).getTitle();
                // 下架该商品
                count = commodityMapper.deleteByid(commodityId);
                // 给最新下架的商品的发布者，发送商品下架消息
                if(count > 0 && commodityList.get(i).getState() != 3){
                    user.setId(userId);
                    message.setUser(user);
                    message.setTitle(Consts.MSG_TITLE_UNDER);
                    message.setContent("你发布的商品 " + "\"" + title + "\" " + Consts.MSG_CONTENT_OVERTIME);
                    message.setState(0);
                    message.setCreateTime(new Date());
                    count = messageMapper.publishMsg(message);
                }
            }
        }

    }

/**
 * 30 天后删除求购信息（真正删除）
 */
    @Scheduled(cron = "0 0 3 * * ?")
    public void timedDelSeek(){
System.out.println("55了，求购==========执行了么？？？");
    List<Seek> seekList = seekMapper.overtimeSeek();
System.out.println("========================");
System.out.println(seekList);
System.out.println("+=======================");
    // 数据列表不为空
    if(seekList.size() > 0){
        // 求购 id
        int seekId = 0;
        // 求购 标题
        String title = "";
        // 用户 id
        int userId = 0;
        // 求购删除通知
        Message message = new Message();
        User user = new User();
        // 删除数量
        int count = 0 ;
        for(int i = 0 ; i <  seekList.size(); i++){
            seekId = seekList.get(i).getId();
            userId = seekList.get(i).getUser().getId();
            // 求购标题
            title = seekList.get(i).getTitle();
            // 删除该求购（状态改为0）
            count = seekMapper.updateSeekState(seekId);
            // 给最新删除的求购信息的发布者，发送求购删除消息
            if(count > 0 && seekList.get(i).getState() != 0){
                user.setId(userId);
                message.setUser(user);
                message.setTitle(Consts.MSG_SEEK_TITLE_DEL);
                message.setContent("你发布的求购" + "\"" + title + "\" " + Consts.MSG_SEEK_CONTENT_OVERTIME);
                message.setState(0);
                message.setCreateTime(new Date());
                count = messageMapper.publishMsg(message);
            }
        }
    }

}





/**
 *  每周一删除
 */

/**
 * 用户消息
 *      消息状态为 2的删除
 */
    @Scheduled(cron = "0 0 4 2/7 * ?")
    public void realDelUserMessage(){
        int count = messageMapper.delMessageByState(2);
        if(count > 0){
            System.out.println("==========================消息删除成功");
            System.out.println(count + "条消息被删除！");
        } else{
            System.out.println("==========================消息删除失败！");
        }
    }

/**
 * 求购信息
 *      1、先删除 举报表 中有该商品id 的记录
 *      2、再求购状态为 0的删除
 */
    @Scheduled(cron = "0 0 4 2/7 * ?")
    public void realDelSeek(){
        // 1、先查询出求购状态 为0 的求购列表
        List<Seek> seekList = seekMapper.findByState(0);
        // 封装所有求购 id 列表
        List<Integer> seekIds = new ArrayList<>();
        for(int i = 0 ;i < seekList.size(); i++){
            seekIds.add(seekList.get(i).getId());
        }

        // 数据列表不为空
        if(seekList.size() > 0){
            try{
                // 2、删除举报表中，求购id 在这个列表中的数据
                informMapper.delInformBySeek(seekIds);

                int count = seekMapper.delSeekByState(0);
                if(count > 0){
                    System.out.println("==========================求购删除成功");
                    System.out.println(count + "条求购被删除！");
                }
            } catch (Exception e){
                System.out.println("！！！==========================求购删除失败");
            }
        }

    }

/**
 * 商品信息
 *      1、先删除 举报表 中有该商品id 的记录
 *      2、删除 商品图片表 有该商品id 的记录
 *      3、删除 收藏表 中有该商品id 的记录
 *      4、再将商品状态为 4的删除
 */
@Scheduled(cron = "0 0 4 2/7 * ?")
    public void realDelCommodity(){
        // 1、先查询出商品状态 为4 的商品列表
        List<Commodity> commodityList = commodityMapper.findByState(4);
        // 封装所有商品 id 列表
        List<Integer> commodityIds = new ArrayList<>();
        for(int i = 0 ;i < commodityList.size(); i++){
            commodityIds.add(commodityList.get(i).getId());
        }

        // 列表不为空
        if(commodityList.size() > 0){
            try{
                // 2、删除举报表中，商品id 在这个列表中的数据
                informMapper.delInformByCommodity(commodityIds);

                // 3、删除商品图片表中数据,该 commodity_id 的 图片 信息
                commodityImgMapper.deleteByCommodityId(commodityIds);
//  要不要把 OSS 服务器中的图片也删除

                // 4、删除收藏表中数据,该 commodity_id 的收藏信息
                collectMapper.deleteByCommodityId(commodityIds);

                // 这里正式，删除商品信息
                int count = commodityMapper.delCommodityByState(4);
                if(count > 0){
                    System.out.println("==========================商品删除成功");
                    System.out.println(count + "条商品被删除！");
                }
            } catch (Exception e){
                System.out.println("！！！==========================商品删除失败");
            }
        }
    }

/**
 * 帖子信息
 *      1、先删除 举报表 中有该帖子id 的记录
 *      2、删除 帖子图片表 有该帖子id 的记录
 *      3、删除 关注表 中有该帖子id 的记录
 *      4、删除 评论表 中有该帖子id 的记录
 *      4、再将帖子状态为 0的删除
 */
@Scheduled(cron = "0 0 4 2/7 * ?")
    public void realDelTopic(){
        // 1、先查询出帖子状态 为0 的帖子列表
        List<Topic> topicList = topicMapper.findByState(0);
        // 封装所有帖子 id 列表
        List<Integer> topicIds = new ArrayList<>();
        for(int i = 0 ;i < topicList.size(); i++){
            topicIds.add(topicList.get(i).getId());
        }

        // 列表不为空
        if(topicList.size() > 0){
            try{
                // 2、删除举报表中，帖子id 在这个列表中的数据
                informMapper.delInformByTopic(topicIds);

                // 3、删除 帖子图片表 中数据,该 topic_id 的 图片 信息
                topicImgMapper.deleteByTopicId(topicIds);
//  ？？？要不要把 OSS 服务器中的图片也删除

                // 4、删除 关注表 中数据,该 topic_id 的 关注 信息
                attentionMapper.deleteByTopicId(topicIds);

                // 5、删除 评论表 中有该 topic_id 的 关注 信息
                commentMapper.deleteByTopicId(topicIds);

                // 这里正式，删除帖子信息
                int count = topicMapper.delTopicByState(0);
                if(count > 0){
                    System.out.println("==========================帖子删除成功");
                    System.out.println(count + "条帖子被删除！");
                }
            } catch (Exception e){
                System.out.println("！！！==========================帖子删除失败");
            }
        }
    }

}
