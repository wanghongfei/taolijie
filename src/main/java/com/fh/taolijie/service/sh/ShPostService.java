package com.fh.taolijie.service.sh;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.sh.SHPostModel;
import com.fh.taolijie.service.PageService;

import java.util.List;

/**
 * 规定与二手帖子有关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface ShPostService extends PageService {
    /**
     * 查找所有二手帖子
     * @return
     */
    ListResult<SHPostModel> getAllPostList(int firstResult, int capacity);

    /**
     * 得到某分类下的二手帖子
     * @param cateId {@link SHPostModel}实体的主键值
     * @param firstResult See {@link com.fh.taolijie.service.ResumeService#getResumeList}
     * @param capacity See {@link com.fh.taolijie.service.ResumeService#getResumeList}
     * @return
     */
    ListResult<SHPostModel> getPostList(Integer cateId, int firstResult, int capacity);

    /**
     * 得到某个用户发的二手帖子信息
     * @param memId 用户id号
     * @param filtered 是否过滤掉已经过期信息
     * @param firstResult
     * @param capacity
     * @return
     */
    ListResult<SHPostModel> getPostList(Integer memId, boolean filtered, int firstResult, int capacity);

    /**
     * 根据分类查找二手
     * @param cateId
     * @param pageView 传递true为最火的在前
     * @return
     */
    ListResult<SHPostModel> getAndFilter(Integer cateId, boolean pageView, int firstResult, int capacity);

    ListResult<SHPostModel> runSearch(SHPostModel model);

    /**
     * 根据条件过虑查询
     * @param model
     * @return
     */
    ListResult<SHPostModel> filterQuery(SHPostModel model);

    /**
     * 查询还未审核的二手信息
     * @return
     */
    List<SHPostModel> getUnverifiedPostList(SHPostModel model);

    /**
     * 查询被投诉的二手信息
     * @return
     */
    List<SHPostModel> getSuedPost(int firstResult, int capacity);

    /**
     * 发布新二手帖子
     * @param postDto
     * @return
     */
    boolean addPost(SHPostModel model);

    /**
     * 收藏二手信息
     * @param memId
     * @param postId
     */
    void favoritePost(Integer memId, Integer postId);

    void unfavoritePost(Integer memId, Integer postId);

    boolean isPostFavorite(Integer memId, Integer postId);

    /**
     * 得到某人收藏的二手
     * @param memberId
     * @param pageNumber 从0开始
     * @return
     */
    ListResult<SHPostModel> getFavoritePost(Integer memberId, int pn, int ps);

    boolean isPostAlreadyFavorite(Integer memId, Integer postId);

    /**
     * 投诉+1
     */
    void complaint(Integer postId);

    /**
     * 根据id查找帖子
     * @return
     */
    SHPostModel findPost(Integer postId);

    /**
     * 删除一个帖子. 将删除标记取反
     * @return
     */
    boolean deletePost(Integer postId);

    /**
     * 修改帖子
     * @param postId
     * @param postDto
     * @return
     */
    boolean updatePost(Integer postId, SHPostModel model);

    /**
     * 修改帖子分类
     * @param postId
     * @param cateId
     */
    void changeCategory(Integer postId, Integer cateId);

    boolean checkExist(Integer postId);
}
