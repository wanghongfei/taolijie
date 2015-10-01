package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.TljAuditModel;
import com.fh.taolijie.exception.checked.quest.AuditNotEnoughException;

import java.util.List;

/**
 * Created by whf on 10/1/15.
 */
public interface TljAuditService {
    TljAuditModel findById(Integer auditId);

    ListResult<TljAuditModel> findBy(TljAuditModel cmd);

    void addAudit(TljAuditModel example);

    void updateAudit(TljAuditModel example);

    /**
     * 剩余数量-1
     * @param auditId
     */
    void decreaseLeftAmt(Integer auditId)
            throws AuditNotEnoughException;
}
