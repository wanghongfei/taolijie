package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.PrivateNotificationModel;
import com.fh.taolijie.domain.SysNotificationModel;

import java.util.List;

/**
 * 规定与通知有关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface NotificationService {
    /**
     * 查询当前用户的系统通知
     * @param memberId 当前登陆用户的id
     * @param rangeList {@link com.fh.taolijie.utils.Constants.NotificationRange}常量的字面值
     * @return
     */
    ListResult<SysNotificationModel> getSysNotification(Integer memberId, List<String> rangeList, int pageNumber, int pageSize);

    /**
     * 查询当前用户的所有个人通知
     * @param memberId 当前登陆用户的id
     */
    ListResult<PrivateNotificationModel> getPriNotification(Integer memberId, int pageNumber, int pageSize);

    /**
     * 查询当前用户所有未读的个人通知
     * @return
     */
    ListResult<PrivateNotificationModel> getUnreadPriNotification(Integer memberId, int pageNumber, int pageSize);

    /**
     * 查询所有系统通知
     * @return
     */
    ListResult<SysNotificationModel> getAllSysNotification(int pageNumber, int pageSize);

    /**
     * 查询所有个人通知
     * @return
     */
    ListResult<PrivateNotificationModel> getAllPriNotification(int pageNumber, int pageSize);

    /**
     * 根据id查询个人通知
     */
    PrivateNotificationModel findPriById(Integer priNotiId);

    /**
     * 根据id查询系统通知
     */
    SysNotificationModel findSysById(Integer notiId);

    /**
     * 添加一条私人通知
     * @param model
     */
    void addNotification(PrivateNotificationModel model);

    /**
     * 添加一条系统通知
     * @param model
     */
    void addNotification(SysNotificationModel model);

    /**
     * 发送一条帖子被评论产生的通知
     * @param memberId 通知接收者
     * @param title
     * @param content
     */
    void addCommentNotification(Integer memberId, String title, String content);

    void deleteSysNotification(Integer sysId);
    void deletePriNotification(Integer priId);

    /**
     * 将个人通知标记为已读
     * @param sysId
     */
    void markPriAsRead(Integer notiId);

    /**
     * 批量标记个人通知为已读
     * @param priNotiList List必须至少有一个元素
     */
    void markPriAsReadInBatch(List<Integer> priNotiList);

    /**
     * 将系统通知标记为已读
     * @param notiId
     */
    void markSysAsRead(Integer memId, Integer notiId);

    /**
     * 批量标记系统通知为已读
     * @param memId
     */
    void markSysAsReadInBatch(Integer memId, List<Integer> idList);
}
