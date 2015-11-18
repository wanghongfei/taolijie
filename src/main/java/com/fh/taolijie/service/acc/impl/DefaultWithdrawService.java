package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.constant.acc.WithdrawStatus;
import com.fh.taolijie.dao.mapper.CashAccModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.WithdrawApplyModelMapper;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.acc.WithdrawApplyModel;
import com.fh.taolijie.exception.checked.acc.*;
import com.fh.taolijie.service.acc.CashAccService;
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

    @Autowired
    private CashAccService accService;

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
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addWithdraw(WithdrawApplyModel model, String dealPwd, PayType payType)
            throws CashAccNotExistsException, BalanceNotEnoughException, InvalidDealPwdException, AccountNotSetException {

        // 查询钱包账户
        CashAccModel acc = accMapper.findByMemberId(model.getMemberId());
        if (null == acc) {
            throw new CashAccNotExistsException("");
        }

        // 创建提现申请model对象
        Date now = new Date();
        model.setUsername(acc.getUsername());
        model.setApplyTime(now);
        model.setUpdateTime(now);
        model.setAccId(acc.getId());
        model.setStatus(WithdrawStatus.WAIT_AUDIT.code());
        // 根据支付类型判断相关账号是否已经设置
        switch (payType) {
            case ALIPAY:
                if (null == acc.getAlipayAcc()) {
                    throw new AccountNotSetException();
                }
                model.setAlipayAcc(acc.getAlipayAcc());
                break;

            case WECHAT: // 暂时不支持微信
                throw new AccountNotSetException();

            case BANK_ACC:
                if (null == acc.getBankAcc()) {
                    throw new AccountNotSetException();
                }
                model.setBankAcc(acc.getBankAcc());
                break;
        }

        // 验证交易密码
        if (!acc.getDealPassword().equals(dealPwd)) {
            throw new InvalidDealPwdException("");
        }

        // 检查账户余额
        BigDecimal balance = acc.getAvailableBalance();
        if (balance.compareTo(model.getAmount()) < 0) {
            // 余额不足
            throw new BalanceNotEnoughException("");
        }

        // 减少账户余额
        accService.reduceAvailableMoney(acc.getId(), model.getAmount(), AccFlow.WITHDRAW);

        // 插入提现申请记录
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
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean updateStatus(Integer withId, WithdrawStatus status, String memo)
            throws WithdrawNotExistsException, CashAccNotExistsException, BalanceNotEnoughException {

        WithdrawApplyModel model = drawMapper.selectByPrimaryKey(withId);
        if (null == model) {
            throw new WithdrawNotExistsException("");
        }

        if (status == WithdrawStatus.FAILED) {
            // 如果状态是失败
            // 则添加账户余额
            accService.addAvailableMoney(model.getAccId(), model.getAmount(), AccFlow.REFUND);
        }

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

    @Override
    @Transactional(readOnly = true)
    public WithdrawApplyModel findById(Integer drawId) {
        return drawMapper.selectByPrimaryKey(drawId);
    }
}
