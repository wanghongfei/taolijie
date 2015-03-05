package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.JobPostDto;

import java.util.List;

/**
 * 规定与兼职信息相关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface JobPostService {
    /**
     * 获取某个用户发的所有兼职帖子
     * @param username 用户名
     * @return
     */
    List<JobPostDto> getJobPostList(String username);

    /**
     * 获取某分类下所有的兼职帖子
     * @param cateId
     * @return
     */
    List<JobPostDto> getJobPostList(Integer cateId);

    /**
     * 根据id查找某个兼职帖子
     * @param postId
     * @return
     */
    JobPostDto findJobPost(Integer postId);

    /**
     * 修改兼职帖子
     * @param postId
     * @param postDto
     * @return
     */
    boolean updateJobPost(Integer postId, JobPostDto postDto);

    /**
     * 删除兼职帖子
     * @param postId
     * @return
     */
    boolean deleteJobPost(Integer postId);
}
