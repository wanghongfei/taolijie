package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.acc.WithdrawStatus;
import com.fh.taolijie.dao.mapper.CashAccModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.WithdrawApplyModelMapper;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.domain.WithdrawApplyModel;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.acc.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 提现申请业务实现
 * Created by whf on 9/21/15.
 */
@Service
public class DefaultWithdrawService implements WithdrawService {
    @Autowired
    private WithdrawApplyModelMapper drawMapper;

    @Autowired
    private MemberModelMapper memMapper;

    @Autowired
    private CashAccModelMapper accMapper;

    /**
     * 创建提现申请.
     * 0. 检查账户余额
     * 1. 设置冗余字段
     * 2. 设置状态为等待审核
     * 3. 插入记录
     *
     * @param model
     */
    @Override
    @Transactional(readOnly = false)
    public void addWithdraw(WithdrawApplyModel model)
            throws CashAccNotExistsException, BalanceNotEnoughException {

        CashAccModel acc = accMapper.selectByPrimaryKey(model.getAccId());
        if (null == acc) {
            throw new CashAccNotExistsException("");
        }

        // 检查账户余额
        BigDecimal balance = acc.getAvailableBalance();
        if (balance.compareTo(model.getAmount()) < 0) {
            // 余额不足
            throw new BalanceNotEnoughException("");
        }

        // 设置冗余字段
        Date now = new Date();
        model.setUsername(acc.getUsername());
        model.setApplyTime(now);
        model.setUpdateTime(now);
        model.setStatus(WithdrawStatus.WAIT_AUDIT.code());

        // 插入记录
        drawMapper.insertSelective(model);
    }

    /**
     * 更新审核状态.
     *
     * @param withId
     * @param status 新状态
     * @param memo 审核备注
     */
    @Override
    @Transactional(readOnly = false)
    public boolean updateStatus(Integer withId, WithdrawStatus status, String memo) {
        WithdrawApplyModel example = new WithdrawApplyModel();
        example.setId(withId);

        example.setMemo(memo);
        example.setStatus(status.code());
        example.setUpdateTime(new Date());

        return drawMapper.updateByPrimaryKeySelective(example) > 0 ? true : false;
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<WithdrawApplyModel> findAll(int pn, int ps) {
        WithdrawApplyModel example = new WithdrawApplyModel(pn, ps);

        List<WithdrawApplyModel> list = drawMapper.findBy(example);
        long tot = drawMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<WithdrawApplyModel> findAllByStatus(WithdrawStatus status, int pn, int ps) {
        WithdrawApplyModel example = new WithdrawApplyModel(pn, ps);
        example.setStatus(status.code());

        List<WithdrawApplyModel> list = drawMapper.findBy(example);
        long tot = drawMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<WithdrawApplyModel> findByMember(Integer memId, int pn, int ps) {
        WithdrawApplyModel example = new WithdrawApplyModel(pn, ps);
        example.setMemberId(memId);

        List<WithdrawApplyModel> list = drawMapper.findBy(example);
        long tot = drawMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<WithdrawApplyModel> findByMember(Integer memId, WithdrawStatus status, int pn, int ps) {
        WithdrawApplyModel example = new WithdrawApplyModel(pn, ps);
        example.setMemberId(memId);
        example.setStatus(status.code());

        List<WithdrawApplyModel> list = drawMapper.findBy(example);
        long tot = drawMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }
}
