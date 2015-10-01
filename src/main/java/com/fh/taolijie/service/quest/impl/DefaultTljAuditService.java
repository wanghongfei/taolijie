package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.dao.mapper.SysConfigModelMapper;
import com.fh.taolijie.dao.mapper.TljAuditModelMapper;
import com.fh.taolijie.domain.SysConfigModel;
import com.fh.taolijie.domain.TljAuditModel;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.quest.AuditNotEnoughException;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.quest.TljAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by whf on 10/1/15.
 */
@Service
public class DefaultTljAuditService implements TljAuditService {
    @Autowired
    private TljAuditModelMapper auditMapper;

    @Autowired
    private SysConfigModelMapper confMapper;

    @Autowired
    private CashAccService accService;

    @Override
    @Transactional(readOnly = true)
    public TljAuditModel findById(Integer auditId) {
        return auditMapper.selectByPrimaryKey(auditId);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<TljAuditModel> findBy(TljAuditModel cmd) {
        List<TljAuditModel> list = auditMapper.findBy(cmd);
        long tot = auditMapper.countFindBy(cmd);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = false)
    public void addAudit(TljAuditModel example)
            throws BalanceNotEnoughException, CashAccNotExistsException {

        // 计算金额
        SysConfigModel conf = confMapper.selectByPrimaryKey(1);
        BigDecimal amt = conf.getAuditFee().multiply(
                new BigDecimal(
                        example.getTotAmt()
                )
        );

        // 扣钱
        CashAccModel acc = accService.findByMember(example.getEmpId());
        if (null == acc) {
            throw new CashAccNotExistsException("");
        }
        accService.reduceAvailableMoney(acc.getId(), amt);

        auditMapper.insertSelective(example);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateAudit(TljAuditModel example) {
        auditMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    @Transactional(readOnly = false)
    public void decreaseLeftAmt(Integer auditId) throws AuditNotEnoughException {
        // 查询剩余数量
        // 该查询会加行锁
        long left = auditMapper.queryLeftAmt(auditId);
        if (left <= 0) {
            throw new AuditNotEnoughException();
        }

        // 数量-1
        auditMapper.decreaseLeftAmt(auditId);
    }
}
