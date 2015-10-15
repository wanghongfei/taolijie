package com.fh.taolijie.service.job;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.service.PageService;
import com.fh.taolijie.utils.Constants;

import java.util.List;

/**
 * 规定与兼职信息相关的操作
 * Created by wanghongfei on 15-3-5.
 */
public interface JobPostService extends PageService {
    /**
     * 查找所有兼职
     * @return
     */
    ListResult<JobPostModel> getAllJobPostList(int firstResult, int capacity);

    /**
     * 获取某个用户发的所有兼职帖子
     * @param memId 用户id
     * @return
     */
    ListResult<JobPostModel> getJobPostListByMember(Integer memId , int firstResult, int capacity);

    /**
     * 获取某分类下所有的兼职帖子
     * @param cateId
     * @return
     */
    ListResult<JobPostModel> getJobPostListByCategory(Integer cateId, int firstResult, int capacity);

    /**
     * @deprecated
     * 查询未审核的post
     * @param firstResult
     * @param capacity
     * @param wrapper
     * @return
     */
    List<JobPostModel> getUnverifiedPostList(int firstResult, int capacity);

    /**
     * 根据多个id批量查询兼职信息
     * @param ids
     * @return
     */
    ListResult<JobPostModel> getPostListByIds(Integer... ids);

    /**
     * 根据多个id批量查询兼职信息
     * @return
     */
    ListResult<JobPostModel> getInBatch(List<Integer> idList);

    /**
     * 查询被投诉的兼职信息，值最高的在前
     * @return
     */
    ListResult<JobPostModel> getByComplaint(int firstResult, int capacity);

    /**
     * 根据条件查找兼职信息.
     * 所有的boolean值**最多只能有一个为true**
     *
     * @param categoryId 分类id
     * @param wayToPay 结算方式
     * @param orderByDate 传递true为最新在前，false为最旧在前
     * @param orderByPageVisit 访问量高的在前, false为忽略
     * @param schoolId 学校实体id
     * @param firstResult
     * @param capacity
     * @return
     */
    List<JobPostModel> getAndFilter(Integer categoryId,
                                    Constants.WayToPay wayToPay,
                                    boolean orderByDate,
                                    boolean orderByPageVisit,
                                    Integer schoolId,
                                    int firstResult,
                                    int capacity);

    /**
     * 根据兼职信息的某个字段执行模糊查询
     * @param field {@link JobPostModel}的某个字段名
     * @param includeString
     * @return
     */
    ListResult<JobPostModel> runSearch(JobPostModel model, int firstResult, int capacity);

    ListResult<JobPostModel> findByExample(JobPostModel example);

    /**
     * 根据id查找某个兼职帖子
     * @param postId
     * @return
     */
    JobPostModel findJobPost(Integer postId);

    /**
     * 投诉数+1
     */
    void complaint(Integer postId);

    /**
     * 收藏一个工作信息
     * @param memId
     * @param postId
     */
    void favoritePost(Integer memId, Integer postId);

    /**
     * 取消收藏一个工作信息
     * @param memId
     * @param postId
     */
    void unfavoritePost(Integer memId, Integer postId);

    /**
     * 查检post是否已经收藏
     * @param memId
     * @param postId
     * @return
     */
    boolean isPostFavorite(Integer memId, Integer postId);

    /**
     * 查询收藏的兼职
     * @param memberId
     * @return
     */
    ListResult<JobPostModel> getFavoritePost(Integer memberId, int pn, int ps);

    /**
     * @deprecated
     * 投递简历
     * @param postId
     * @param resumeId
     */
    void postResume(Integer postId, Integer resumeId, Integer memberId);

    /**
     * 发布帖子
     * @param dto
     */
    void addJobPost(JobPostModel model);

    /**
     * 修改兼职帖子, 无法修改评论信息
     * @param postId
     * @param postDto
     * @return
     */
    boolean updateJobPost(Integer postId, JobPostModel model);

    /**
     * 删除兼职帖子, 同时删除帖子下的评论
     * @param postId
     * @return
     */
    boolean deleteJobPost(Integer postId);
}
