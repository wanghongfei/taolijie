package com.fh.taolijie.service;

import com.fh.taolijie.controller.dto.EducationExperienceDto;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-5.
 */
public interface EduExpService {
    /**
     * 得到所有教育经历信息
     * @return
     */
    List<EducationExperienceDto> getEduExpList(int firstResult, int capacity);

    /**
     * 新建教育信息
     * @param eduDto
     * @return
     */
    boolean addEduExp(EducationExperienceDto eduDto);

    /**
     * 修改教育信息
     * @param eduId
     * @param eduDto
     * @return
     */
    boolean updateEduExp(Integer eduId, EducationExperienceDto eduDto);

    /**
     * 删除教育信息.
     * <p> 教育信息对应的{@link com.fh.taolijie.domain.MemberEntity}和{@link com.fh.taolijie.domain.SchoolEntity}
     *          不会被删除.
     * @param id
     * @return
     */
    boolean deleteEduExp(Integer id);

    /**
     * 根据id查找教育信息
     * @param id
     * @return
     */
    EducationExperienceDto findEduExp(Integer id);
}
