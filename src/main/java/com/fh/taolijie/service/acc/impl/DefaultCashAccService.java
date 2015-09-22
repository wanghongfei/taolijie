package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.constant.acc.CashAccStatus;
import com.fh.taolijie.dao.mapper.AccFlowModelMapper;
import com.fh.taolijie.dao.mapper.CashAccModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.domain.AccFlowModel;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccExistsException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.acc.AccFlowService;
import com.fh.taolijie.service.acc.CashAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by whf on 9/20/15.
 */
@Service
public class DefaultCashAccService implements CashAccService {
    @Autowired
    private CashAccModelMapper accMapper;

    @Autowired
    private AccFlowModelMapper flowMapper;

    @Autowired
    private MemberModelMapper memMapper;


    @Autowired
    private AccFlowService flowService;

    @Override
    @Transactional(readOnly = false)
    public void addAcc(CashAccModel model) throws CashAccExistsException, UserNotExistsException {
        if (checkCashAccExists(model.getMemberId())) {
            throw new CashAccExistsException("用户" + model.getMemberId() + "的现金账户已经存在");
        }

        // 设置username冗余字段
        MemberModel m = memMapper.selectByPrimaryKey(model.getMemberId());
        if (null == m) {
            throw new UserNotExistsException("");
        }
        model.setUsername(m.getUsername());
        // 设置member role冗余字段
        model.setMemberRole(m.getRoleList().get(0).getRolename());

        model.setUpdateTime(new Date());
        model.setCreatedTime(new Date());
        accMapper.insertSelective(model);
    }

    /**
     * 检查指定用户的现金账户是否已经开通了
     * @param memId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean checkCashAccExists(Integer memId) {
        return accMapper.checkAccExists(memId);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateStatus(Integer accId, CashAccStatus status) throws CashAccNotExistsException{
        if (!checkAccIdExists(accId)) {
            throw new CashAccNotExistsException("现金账户" + accId + "不存在");
        }

        CashAccModel example = new CashAccModel();
        example.setId(accId);
        example.setStatus(status.code());
        accMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkAccIdExists(Integer accId) {
        return accMapper.checkAccIdExists(accId);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean addAvailableMoney(Integer accId, BigDecimal amt) throws CashAccNotExistsException{
        // 记录流水
        flowService.recordAvaBalanceChange(accId, AccFlow.CHARGE, amt);

        return accMapper.addAvailableAmt(accId, amt) > 0 ? true : false;
    }

    @Override
    @Transactional(readOnly = false)
    public void reduceAvailableMoney(Integer accId, BigDecimal amt)
            throws CashAccNotExistsException, BalanceNotEnoughException {

        CashAccModel acc = accMapper.selectByPrimaryKey(accId);
        if (null == acc) {
            throw new CashAccNotExistsException("现金账户" + accId + "不存在");
        }

        // 判断余额是否充足
        BigDecimal avaBalance = acc.getAvailableBalance();
        if (avaBalance.compareTo(amt) < 0) {
            throw new BalanceNotEnoughException("");
        }

        // 减少可用余额
        BigDecimal newBalance = avaBalance.subtract(amt);
        //acc.setAvailableBalance(newBalance);
        //calculateTotalBalance(acc);

        // 记录流水
        flowService.recordAvaBalanceChange(accId, AccFlow.WITHDRAW, amt.negate());


        // 执行更新操作
        CashAccModel example = new CashAccModel();
        example.setId(accId);
        example.setAvailableBalance(newBalance);
        //example.setTotalBalance(acc.getTotalBalance());
        example.setUpdateTime(new Date());
        accMapper.updateByPrimaryKeySelective(example);
    }

    /**
     * @deprecated
     * 计算总余额
     * @param acc
     */
    private void calculateTotalBalance(CashAccModel acc) {
        BigDecimal tot = acc.getAvailableBalance().add(acc.getFrozenBalance());
        acc.setTotalBalance(tot);
    }


    /**
     * @deprecated
     */
    @Override
    @Transactional(readOnly = false)
    public void frozenMoney(Integer accId, BigDecimal amt)
            throws CashAccNotExistsException, BalanceNotEnoughException {

        CashAccModel acc = accMapper.selectByPrimaryKey(accId);
        if (null == acc) {
            throw new CashAccNotExistsException("");
        }

        // 检查可用余额是否充足
        if (acc.getAvailableBalance().compareTo(amt) < 0) {
            throw new BalanceNotEnoughException("");
        }

        // 减少可用余额
        BigDecimal newAva = acc.getAvailableBalance().subtract(amt);
        acc.setAvailableBalance(newAva);

        // 增加冻结余额
        BigDecimal newFrozen = acc.getFrozenBalance().add(amt);
        acc.setFrozenBalance(newFrozen);

        // 更新
        CashAccModel example = new CashAccModel();
        example.setId(accId);
        example.setUpdateTime(new Date());
        example.setAvailableBalance(newAva);
        example.setFrozenBalance(newFrozen);
        accMapper.updateByPrimaryKeySelective(example);


        // 记录流水
        BigDecimal zero = new BigDecimal("0.00");

        // 创建流水
        AccFlowModel flowModel = new AccFlowModel();
        flowModel.setAccId(accId);
        flowModel.setActionType(AccFlow.FROZEN.code());
        flowModel.setCreatedTime(new Date());

        // 记录可用余额变化
        flowModel.setAvaBalanceCh(amt.negate());
        flowModel.setAvaBalanceNew(newAva);

        // 记录冻结余额变化
        flowModel.setFroBalanceCh(amt);
        flowModel.setFroBalanceNew(newFrozen);

        // 记录总余额变化
        flowModel.setTotBalanceCh(zero);
        flowModel.setTotBalanceNew(acc.getTotalBalance());

        flowMapper.insertSelective(flowModel);
    }

    @Override
    @Transactional(readOnly = true)
    public CashAccModel findByMember(Integer memId) {
        return accMapper.findByMemberId(memId);
    }

    @Override
    @Transactional(readOnly = true)
    public CashAccModel findByAcc(Integer accId) {
        return accMapper.selectByPrimaryKey(accId);
    }
}
