package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.ApplicationIntendModel;
import com.fh.taolijie.domain.ResumeModel;
import com.fh.taolijie.domain.ResumeModel;
import com.fh.taolijie.domain.middle.ResumePostRecord;
import com.fh.taolijie.domain.middle.ResumeWithIntend;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-5.
 */
public interface ResumeService extends PageService {
    /**
     * 查找所有简历
     * @return
     */
    List<ResumeModel> getAllResumeList(int firstResult, int capacity);

    /**
     * 根据权限查找所有简历
     * @return
     */
    List<ResumeModel> getAllResumeList(Constants.AccessAuthority authority, int firstResult, int capacity);

    ListResult<ResumeModel> findByAuthes(int pageNumber, int pageSize, Constants.AccessAuthority... authList);

    /**
     * 获取某个用户的部分简历.
     *
     * @param memId 用户的id号
     * @param firstResult 指定要跳过前多少个结果,分页时使用.
     * @param capacity 指定一页最多有多少结果. 传递0表示使用默认值.
     * @see com.fh.taolijie.utils.Constants
     * @return
     */
    List<ResumeModel> getResumeList(Integer memId, int firstResult, int capacity);

    /**
     *
     * 获取某个用户的部分简历.
     * @param authority 设置简历权限类型
     * @return
     */
    List<ResumeModel> getResumeList(Integer memId, Constants.AccessAuthority authority, int firstResult, int capacity);

    /**
     * 根据求职意向查找简历
     * @param categoryId
     * @param firstResult
     * @param capacity
     * @return
     */
    ListResult<ResumeModel> getResumeListByIntend(Integer categoryId, int firstResult, int capacity);


    ListResult<ResumeModel> getResumeByGender(String gender, int pageNumber, int pageSize);

    ListResult<ResumeModel> findBy(ResumeModel example);

    ListResult<ResumeModel> findByGenderAndIntend(Integer intendId, String gender, int pageNumber, int pageSize);

    /**
     * 为每个简历查询对应的意向
     * @param resumeIdList
     * @return
     */
    List<ResumeWithIntend> findIntendForResume(List<Integer> resumeIdList);

    /**
     * 根据简历查找意向
     * @param resumeId
     * @return
     */
    List<ApplicationIntendModel> getIntendByResume(Integer resumeId);


    /**
     * 查询投递记录
     * @return
     */
    ListResult<ResumePostRecord> getPostRecord(Integer memId, int page, int capacity);


    /**
     * 可同时查询多个简历
     * @param ids
     * @return
     */
    List<ResumeModel> getResumesByIds(int page, int capacity, ObjWrapper wrapper, Integer... ids);

    /**
     * 修改简历信息
     * @param resumeId
     * @param resumeDto
     * @return
     */
    boolean updateResume(Integer resumeId, ResumeModel model);

    /**
     * 刷新简历更新时间
     * @return 一天只能刷一次，刷新失败返回false
     */
    boolean refresh(Integer resumeId);

    /**
     * 收藏简历
     * @param memId
     * @param resumeId
     */
    void favoriteResume(Integer memId, Integer resumeId);

    void unFavorite(Integer memId, Integer resumeId);

    boolean isAlreadyFavorite(Integer memId, Integer resumeId);

    /**
     * 创建新简历
     * @return 刚创建的简历的id
     */
    int addResume(ResumeModel model);

    /**
     * 给简历添加求职意向
     * @param intend
     */
    void addIntend(ApplicationIntendModel intend);

    /**
     * 删除简历的求职意向
     * @param intendModel
     */
    void deleteIntend(ApplicationIntendModel intendModel);


    /**
     * 根据id查找简历
     * @param resumeId
     * @return
     */
    ResumeModel findResume(Integer resumeId);

    /**
     * 根据id删除简历
     * @param resumeId
     * @return
     */
    boolean deleteResume(Integer resumeId);

    /**
     * 给ResumeModel对象的intend赋值.
     * 即将意向设置到对应的简历中
     *
     * @param withList 带意向信息和简历id的中间对象
     * @param reList 简历List
     * @return 赋好值的简历List
     */
    List<ResumeModel> assignIntend(List<ResumeWithIntend> withList, List<ResumeModel> reList);
}
