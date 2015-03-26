package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.JobPostDto;

import java.util.List;

/**
 * 规定与兼职信息相关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface JobPostService {
    /**
     * 查找所有兼职
     * @return
     */
    List<JobPostDto> getAllJobPostList(int firstResult, int capacity);

    /**
     * 获取某个用户发的所有兼职帖子
     * @param memId 用户id
     * @return
     */
    List<JobPostDto> getJobPostListByMember(Integer memId , int firstResult, int capacity);

    /**
     * 获取某分类下所有的兼职帖子
     * @param cateId
     * @return
     */
    List<JobPostDto> getJobPostListByCategory(Integer cateId, int firstResult, int capacity);

    /**
     * 根据id查找某个兼职帖子
     * @param postId
     * @return
     */
    JobPostDto findJobPost(Integer postId);

    /**
     * 发布帖子
     * @param dto
     */
    void addJobPost(JobPostDto dto);

    /**
     * 修改兼职帖子, 无法修改评论信息
     * @param postId
     * @param postDto
     * @return
     */
    boolean updateJobPost(Integer postId, JobPostDto postDto);

    /**
     * 删除兼职帖子, 同时删除帖子下的评论
     * @param postId
     * @return
     */
    boolean deleteJobPost(Integer postId);
}
