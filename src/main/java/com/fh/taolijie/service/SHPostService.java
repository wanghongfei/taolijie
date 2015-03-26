package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.SecondHandPostDto;

import java.util.List;

/**
 * 规定与二手帖子有关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface SHPostService {
    /**
     * 查找所有二手帖子
     * @return
     */
    List<SecondHandPostDto> getAllPostList(int firstResult, int capacity);

    /**
     * 得到某分类下的二手帖子
     * @param cateId {@link com.fh.taolijie.domain.SecondHandPostCategoryEntity}实体的主键值
     * @param firstResult See {@link com.fh.taolijie.service.ResumeService#getResumeList}
     * @param capacity See {@link com.fh.taolijie.service.ResumeService#getResumeList}
     * @return
     */
    List<SecondHandPostDto> getPostList(Integer cateId, int firstResult, int capacity);

    /**
     * 得到某个用户发的二手帖子信息
     * @param memId 用户id号
     * @param filtered 是否过滤掉已经过期信息
     * @param firstResult
     * @param capacity
     * @return
     */
    List<SecondHandPostDto> getPostList(Integer memId, boolean filtered, int firstResult, int capacity);

    /**
     * 发布新二手帖子
     * @param postDto
     * @return
     */
    boolean addPost(SecondHandPostDto postDto);

    /**
     * 根据id查找帖子
     * @param postId {@link com.fh.taolijie.domain.SecondHandPostEntity}实体的主键值
     * @return
     */
    SecondHandPostDto findPost(Integer postId);

    /**
     * 删除一个帖子
     * @param postId {@link com.fh.taolijie.domain.SecondHandPostEntity}实体的主键值
     * @return
     */
    boolean deletePost(Integer postId);

    /**
     * 修改帖子
     * @param postId
     * @param postDto
     * @return
     */
    boolean updatePost(Integer postId, SecondHandPostDto postDto);

    /**
     * 修改帖子分类
     * @param postId
     * @param cateId
     */
    void changeCategory(Integer postId, Integer cateId);
}
