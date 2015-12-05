package com.fh.taolijie.service.certi;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.domain.IdCertiModel;
import com.fh.taolijie.exception.checked.certi.ApplicationDuplicatedException;

/**
 * Created by whf on 11/16/15.
 */
public interface IdCertiService {
    /**
     * 申请个人身份认证
     */
    void addApplication(IdCertiModel model)
            throws ApplicationDuplicatedException;

    /**
     * 更新认证状态
     * @param memId
     * @param status
     * @param memo
     */
    void updateStatus(Integer certiId, Integer memId, CertiStatus status, String memo);

    /**
     * 检查用户是否通过了个人认证
     * @param memId
     * @return
     */
    boolean checkVerified(Integer memId);

    /**
     * 根据状态筛选个人认证申请
     * @param status
     * @param pn
     * @param ps
     * @return
     */
    ListResult<IdCertiModel> findByStatus(CertiStatus status, int pn, int ps);

    IdCertiModel findById(Integer certiId);

    /**
     * 查找最新的认证成功的认证信息
     * @param memId
     * @return
     */
    IdCertiModel findLastSuccess(Integer memId);
}
