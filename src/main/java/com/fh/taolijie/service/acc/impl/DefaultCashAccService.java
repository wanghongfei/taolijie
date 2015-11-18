package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.constant.acc.AccFlow;
import com.fh.taolijie.constant.acc.CashAccStatus;
import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.constant.acc.OrderType;
import com.fh.taolijie.dao.mapper.AccFlowModelMapper;
import com.fh.taolijie.dao.mapper.CashAccModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.PayOrderModelMapper;
import com.fh.taolijie.domain.SeQuestionModel;
import com.fh.taolijie.domain.acc.AccFlowModel;
import com.fh.taolijie.domain.acc.CashAccModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.order.PayOrderModel;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.PermissionException;
import com.fh.taolijie.exception.checked.acc.UserNotExistsException;
import com.fh.taolijie.exception.checked.acc.*;
import com.fh.taolijie.service.acc.AccFlowService;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.acc.OrderService;
import com.fh.taolijie.service.acc.SeQuestionService;
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

    @Autowired
    private SeQuestionService seService;

    @Autowired
    private PayOrderModelMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int charge(Integer orderId, Integer memId) throws OrderNotFoundException, PermissionException, CashAccNotExistsException, FinalStatusException {
        // 订单验证
        PayOrderModel order = orderService.orderPayCheck(orderId, memId, OrderType.CHARGE, null);


        // 取出订单金额
        BigDecimal amt = order.getAmount();
        // 充值
        Integer accId = accMapper.findIdByMemberId(memId);
        addAvailableMoney(accId, amt, AccFlow.CHARGE);

        // 更新订单状态为已完成
        orderService.updateStatus(orderId, OrderStatus.DONE, null);

        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void addAcc(CashAccModel model, SeQuestionModel question)
            throws CashAccExistsException, UserNotExistsException, SecretQuestionExistException {

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


        // 创建密保问题
        seService.add(question);

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
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
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
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateAlipay(Integer accId, String alipay) throws CashAccNotExistsException {
        if (!checkAccIdExists(accId)) {
            throw new CashAccNotExistsException("现金账户" + accId + "不存在");
        }

        CashAccModel example = new CashAccModel();
        example.setId(accId);
        example.setAlipayAcc(alipay);
        accMapper.updateByPrimaryKeySelective(example);

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateBankAcc(Integer accId, String bank) throws CashAccNotExistsException {
        if (!checkAccIdExists(accId)) {
            throw new CashAccNotExistsException("现金账户" + accId + "不存在");
        }

        CashAccModel example = new CashAccModel();
        example.setId(accId);
        example.setBankAcc(bank);
        accMapper.updateByPrimaryKeySelective(example);

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updatePhone(Integer accId, String phone) {
        CashAccModel example = new CashAccModel();
        example.setId(accId);
        example.setPhoneNumber(phone);

        accMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateDealPwd(Integer memId, String pwd) throws CashAccNotExistsException {
        Integer accId = accMapper.findIdByMemberId(memId);
        if (null == accId) {
            throw new CashAccNotExistsException("memId = " + memId);
        }

        CashAccModel example = new CashAccModel();
        example.setId(accId);
        example.setDealPassword(pwd);
        accMapper.updateByPrimaryKeySelective(example);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkAccIdExists(Integer accId) {
        return accMapper.checkAccIdExists(accId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal checkBalance(Integer memId) {
        return accMapper.selectBalance(memId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean addAvailableMoney(Integer accId, BigDecimal amt) throws CashAccNotExistsException{
        return addAvailableMoney(accId, amt, AccFlow.CHARGE);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public boolean addAvailableMoney(Integer accId, BigDecimal amt, AccFlow type) throws CashAccNotExistsException {
        // 记录流水
        flowService.recordAvaBalanceChange(accId, type, amt);

        return accMapper.addAvailableAmt(accId, amt) > 0 ? true : false;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void reduceAvailableMoney(Integer accId, BigDecimal amt)
            throws CashAccNotExistsException, BalanceNotEnoughException {

        reduceAvailableMoney(accId, amt, AccFlow.WITHDRAW);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void reduceAvailableMoney(Integer accId, BigDecimal amt, AccFlow type) throws CashAccNotExistsException, BalanceNotEnoughException {

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
        flowService.recordAvaBalanceChange(accId, type, amt.negate());


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
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
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
    public Integer findIdByMember(Integer memId) {
        return accMapper.findIdByMemberId(memId);
    }

    @Override
    @Transactional(readOnly = true)
    public CashAccModel findByAcc(Integer accId) {
        return accMapper.selectByPrimaryKey(accId);
    }
}
