package com.fh.taolijie.service.certi;

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
}
