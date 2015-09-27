package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.constant.acc.OrderType;
import com.fh.taolijie.dao.mapper.CashAccModelMapper;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.PayOrderModelMapper;
import com.fh.taolijie.domain.CashAccModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.PayOrderModel;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.acc.CashAccService;
import com.fh.taolijie.service.acc.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 充值业务实现
 * Created by whf on 9/21/15.
 */
@Service
public class DefaultChargeService implements ChargeService {
    @Autowired
    private PayOrderModelMapper orderMapper;

    @Autowired
    private CashAccModelMapper accMapper;

    @Autowired
    private CashAccService accService;

    @Override
    @Transactional(readOnly = false)
    public void chargeApply(PayOrderModel model)
            throws CashAccNotExistsException {

        Date now = new Date();

        model.setTitle("账户充值申请");
        model.setCreatedTime(now);
        model.setUpdateTime(now);
        model.setType(OrderType.CHARGE.code());
        model.setStatus(OrderStatus.WAIT_AUDIT.code());

        // 查出账户id
        CashAccModel acc = accMapper.findByMemberId(model.getMemberId());
        if (null == acc) {
            throw new CashAccNotExistsException("");
        }
        model.setCashAccId(acc.getId());


        orderMapper.insertSelective(model);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateStatus(Integer orderId, OrderStatus status, String memo)
            throws CashAccNotExistsException {

        PayOrderModel example = new PayOrderModel();

        example.setId(orderId);
        example.setUpdateTime(new Date());
        example.setMemo(memo);

        if (status == OrderStatus.DONE) {
            // 充值成功
            // 添加账户金额
            PayOrderModel order = orderMapper.selectByPrimaryKey(orderId);
            accService.addAvailableMoney(order.getCashAccId(), order.getAmount());
        }

        example.setStatus(status.code());

        return orderMapper.updateByPrimaryKeySelective(example) > 0 ? true : false;
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<PayOrderModel> findByAcc(Integer accId, int pn, int ps) {
        PayOrderModel example = new PayOrderModel(pn, ps);
        example.setCashAccId(accId);

        List<PayOrderModel> list = orderMapper.findBy(example);
        long tot = orderMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<PayOrderModel> findByAcc(Integer accId, OrderStatus status, int pn, int ps) {
        PayOrderModel example = new PayOrderModel(pn, ps);
        example.setCashAccId(accId);
        example.setStatus(status.code());

        List<PayOrderModel> list = orderMapper.findBy(example);
        long tot = orderMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<PayOrderModel> findAllAcc(OrderStatus status, int pn, int ps) {
        PayOrderModel example = new PayOrderModel(pn, ps);
        if (null != status) {
            example.setStatus(status.code());
        }

        List<PayOrderModel> list = orderMapper.findBy(example);
        long tot = orderMapper.countFindBy(example);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = true)
    public PayOrderModel findById(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }
}
