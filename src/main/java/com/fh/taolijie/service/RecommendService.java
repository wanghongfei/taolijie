package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.RecommendedPostModel;

/**
 * Created by whf on 7/11/15.
 */
public interface RecommendService {
    /**
     * 根据id查询推荐信息.
     * 返回结果不包含关联Model对象
     * @param recommendId
     * @return
     */
    RecommendedPostModel findById(Integer recommendId);

    /**
     * 添加推荐信息
     * @param model
     * @return 刚刚添加的记录的主键id
     */
    int add(RecommendedPostModel model);

    /**
     * 根据id删除推荐信息
     * @param recommendId
     */
    void deleteById(Integer recommendId);

    /**
     * 根据id更新推荐信息
     * @param model
     */
    void updateByIdSelective(RecommendedPostModel model);

    /**
     * 得到推荐的兼职信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    ListResult<RecommendedPostModel> getJobList(int pageNumber, int pageSize);

    /**
     * 得到推荐的二手信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    ListResult<RecommendedPostModel> getShList(int pageNumber, int pageSize);

    /**
     * 得到推荐的简历信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    ListResult<RecommendedPostModel> getResumeList(int pageNumber, int pageSize);
}
