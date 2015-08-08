package com.fh.taolijie.service;

import com.fh.taolijie.constant.OperationType;
import com.fh.taolijie.controller.dto.CreditsInfo;

/**
 * 规定与用户业务逻辑相关操作
 * Created by wanghongfei on 15-4-3.
 */
public interface UserService {
    /**
     * 赞工作信息
     * @param memId
     * @param postId
     * @return
     */
    boolean likeJobPost(Integer memId, Integer postId);

    /**
     * 取消赞工作信息
     */
    boolean unLikeJobPost(Integer memId, Integer postId);

    /**
     * 赞二手信息
     * @param memId
     * @param shId
     * @return
     */
    boolean likeSHPost(Integer memId, Integer shId);

    /**
     * 取消赞二手信息
     * @return
     */
    boolean unlikeShPost(Integer memId, Integer shId);

    /**
     * 判断兼职信息是否已赞
     * @param memId
     * @param posId
     * @return
     */
    boolean isJobPostAlreadyLiked(Integer memId, Integer posId);

    /**
     * 判断二手信息是否已赞
     * @param memId
     * @param shId
     * @return
     */
    boolean isSHPostAlreadyLiked(Integer memId, Integer shId);

    /**
     * 查询积分信息
     * @param memberId
     * @return
     */
    CreditsInfo queryCreditsInfo(Integer memberId);

    /**
     * 加减分
     * @param memberId
     * @return 返回新的积分
     */
    int changeCredits(Integer memberId, OperationType type, int oldCredits);

    /**
     * 查询当前积分对应等级
     * @param credits
     * @return
     */
    String queryLevel(int credits);
}
