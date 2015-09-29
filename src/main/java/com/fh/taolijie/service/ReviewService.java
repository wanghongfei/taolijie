package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.ReviewModel;

/**
 * 规定与评论相关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface ReviewService {
    /**
     * 获取某个帖子的评论
     * @return
     */
    ListResult<ReviewModel> getReviewList(ReviewModel model);

    /**
     * 得到指定评论的回复
     * @param reviewId
     * @return
     */
    ListResult<ReviewModel> getReply(Integer reviewId, int pageNumber, int pageSize);

    /**
     * 删除指定评论的所有回复
     * @param resumeId
     * @return 返回被删除的条数
     */
    int deleteReplyByReview(Integer resumeId);

    ReviewModel getById(Integer reviewId);

    /**
     * 添加一条评论
     * @param reviewDto
     * @return
     */
    Integer addReview(ReviewModel model);

    /**
     * 回复一条评论
     * @param memId
     * @param reviewId
     */
    void addComment(ReviewModel model);

    /**
     * 删除一条评论
     * @param reviewId
     * @return
     */
    boolean deleteReview(Integer reviewId);

    /**
     * 修改一条评论
     * @param reviewId
     * @param reviewDto
     * @return
     */
    boolean updateReview(Integer reviewId, ReviewModel model);
}
