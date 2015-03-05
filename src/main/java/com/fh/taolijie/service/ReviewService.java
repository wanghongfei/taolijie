package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.ReviewDto;

import java.util.List;

/**
 * 规定与评论相关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface ReviewService {
    /**
     * 获取某个帖子的评论
     * @param postId {@link com.fh.taolijie.domain.JobPostEntity}的主键值
     * @param firstResult See {@link com.fh.taolijie.service.ResumeService#getResumeList}
     * @param capacity See {@link com.fh.taolijie.service.ResumeService#getResumeList}
     * @return
     */
    List<ReviewDto> getReviewList(Integer postId, int firstResult, int capacity);

    /**
     * 添加一条评论
     * @param reviewDto
     * @return
     */
    boolean addReview(ReviewDto reviewDto);

    /**
     * 删除一条评论
     * @param reviewId
     * @param memId 当前已登陆用户的id, 用来判断是否有删除权限
     * @return
     */
    boolean deleteReview(Integer reviewId, Integer memId);

    /**
     * 修改一条评论
     * @param reviewId
     * @param reviewDto
     * @return
     */
    boolean updateReview(Integer reviewId, ReviewDto reviewDto);
}
