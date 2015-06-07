package com.fh.taolijie.service;

import com.fh.taolijie.domain.ResumeModel;
import com.fh.taolijie.domain.ResumeModel;
import com.fh.taolijie.domain.middle.ResumePostRecord;
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
    List<ResumeModel> getAllResumeList(int firstResult, int capacity, ObjWrapper wrap);

    /**
     * 根据权限查找所有简历
     * @return
     */
    List<ResumeModel> getAllResumeList(Constants.AccessAuthority authority, int firstResult, int capacity, ObjWrapper wrap);

    /**
     * 获取某个用户的部分简历.
     *
     * @param memId 用户的id号
     * @param firstResult 指定要跳过前多少个结果,分页时使用.
     * @param capacity 指定一页最多有多少结果. 传递0表示使用默认值.
     * @see com.fh.taolijie.utils.Constants
     * @return
     */
    List<ResumeModel> getResumeList(Integer memId, int firstResult, int capacity, ObjWrapper wrap);

    /**
     *
     * 获取某个用户的部分简历.
     * @param authority 设置简历权限类型
     * @return
     */
    List<ResumeModel> getResumeList(Integer memId, Constants.AccessAuthority authority, int firstResult, int capacity, ObjWrapper wrap);

    /**
     * 根据求职意向查找简历
     * @param categoryId
     * @param firstResult
     * @param capacity
     * @return
     */
    List<ResumeModel> getResumeListByIntend(Integer categoryId, int firstResult, int capacity);

    /**
     * 查询投递记录
     * @return
     */
    List<ResumePostRecord> getPostRecord(Integer memId, int page, int capacity, ObjWrapper wrap);


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

    /**
     * 创建新简历
     */
    void addResume(ResumeModel model);


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
}
