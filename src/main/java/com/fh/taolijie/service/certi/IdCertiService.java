package com.fh.taolijie.service.certi;

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

    IdCertiModel findById(Integer certiId);
}
