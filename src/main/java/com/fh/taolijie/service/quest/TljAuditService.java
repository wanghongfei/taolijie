package com.fh.taolijie.service.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.TljAuditModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.AuditNotEnoughException;
import com.fh.taolijie.exception.checked.quest.RequestRepeatedException;

import java.math.BigDecimal;


/**
 * 桃李街代审核业务实现
 * Created by whf on 10/1/15.
 */
public interface TljAuditService {
    TljAuditModel findById(Integer auditId);

    ListResult<TljAuditModel> findBy(TljAuditModel cmd);

    /**
     * 添加一条代审核申请.
     * @param example
     */
    void addAudit(TljAuditModel example)
            throws BalanceNotEnoughException, CashAccNotExistsException, RequestRepeatedException;

    /**
     * 计算代审核总费用
     * @param amt
     * @return
     */
    BigDecimal calculateFee(Integer amt);

    void updateAudit(TljAuditModel example);

    /**
     * 剩余数量-1
     * @param auditId
     */
    void decreaseLeftAmt(Integer auditId)
            throws AuditNotEnoughException;
}
