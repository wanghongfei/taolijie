package com.fh.taolijie.service;

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
     * 赞二手信息
     * @param memId
     * @param shId
     * @return
     */
    boolean likeSHPost(Integer memId, Integer shId);

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
}
