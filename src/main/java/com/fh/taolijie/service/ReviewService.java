package com.fh.taolijie.service;

import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * 规定与评论相关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface ReviewService {
    /**
     * 获取某个帖子的评论
     * @param postId {@link JobPostModel}的主键值
     * @param firstResult See {@link com.fh.taolijie.service.ResumeService#getResumeList}
     * @param capacity See {@link com.fh.taolijie.service.ResumeService#getResumeList}
     * @return
     */
    List<ReviewModel> getReviewList(Integer postId, int firstResult, int capacity, ObjWrapper wrapper);

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
    void addComment(Integer memId, Integer reviewId, ReviewModel model);

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
