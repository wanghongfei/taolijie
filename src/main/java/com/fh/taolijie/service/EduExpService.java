package com.fh.taolijie.service;

import com.fh.taolijie.domain.EEModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.SchoolModel;
import com.fh.taolijie.utils.ObjWrapper;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-5.
 */
public interface EduExpService {
    /**
     * 得到所有教育经历信息
     * @return
     */
    List<EEModel> getEduExpList(Integer memberId, int firstResult, int capacity, ObjWrapper wrapper);

    /**
     * 新建教育信息
     * @return
     */
    boolean addEduExp(EEModel model);

    /**
     * 修改教育信息
     * @param eduId
     * @return
     */
    boolean updateEduExp(Integer eduId, EEModel model);

    /**
     * 删除教育信息.
     * <p> 教育信息对应的{@link MemberModel}和{@link SchoolModel}
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
    EEModel findEduExp(Integer id);
}
