package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.ResumeDto;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-5.
 */
public interface ResumeService {
    /**
     * 查找所有简历
     * @return
     */
    List<ResumeDto> getAllResumeList(int firstResult, int capacity);

    /**
     * 获取某个用户的部分简历.
     *
     * @param memId 用户的id号
     * @param firstResult 指定要跳过前多少个结果,分页时使用.
     * @param capacity 指定一页最多有多少结果. 传递0表示使用默认值.
     * @see com.fh.taolijie.utils.Constants
     * @return
     */
    List<ResumeDto> getResumeList(Integer memId, int firstResult, int capacity);

    /**
     * 修改简历信息
     * @param resumeId
     * @param resumeDto
     * @return
     */
    boolean updateResume(Integer resumeId, ResumeDto resumeDto);

    /**
     * 刷新简历更新时间
     * @return 一天只能刷一次，刷新失败返回false
     */
    boolean refresh(Integer resumeId);

    /**
     * 创建新简历
     * @param dto
     */
    void addResume(ResumeDto dto);


    /**
     * 根据id查找简历
     * @param resumeId
     * @return
     */
    ResumeDto findResume(Integer resumeId);

    /**
     * 根据id删除简历
     * @param resumeId
     * @return
     */
    boolean deleteResume(Integer resumeId);
}
