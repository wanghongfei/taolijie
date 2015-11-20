package com.fh.taolijie.service.certi;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.domain.certi.StuCertiModel;

import java.util.List;

/**
 * 学生认证业务接口
 * Created by whf on 9/22/15.
 */
public interface StuCertiService {
    /**
     * 申请学生认证
     * @param model
     */
    void addApplication(StuCertiModel model);

    /**
     * 更新学生认证状态
     * @param certiId
     * @param memId 被认证用户的id
     * @param status
     * @param memo
     */
    void updateStatus(Integer certiId, Integer memId, CertiStatus status, String memo);

    /**
     * 检查是否通过了学生认证
     * @param memId
     * @return
     */
    boolean checkVerified(Integer memId);

    StuCertiModel findById(Integer certiId);

    /**
     * 根据状态筛选认证信息
     * @param status
     * @return
     */
    ListResult<StuCertiModel> findByStatus(CertiStatus status, int pn, int ps);

    /**
     * 查找指定用户的学生认证申请记录
     * @param memId
     * @return
     */
    ListResult<StuCertiModel> findByMember(Integer memId);

    /**
     * 查找指定用户通过了的认证信息
     * @param memId
     * @return
     */
    StuCertiModel findDoneByMember(Integer memId);
}
