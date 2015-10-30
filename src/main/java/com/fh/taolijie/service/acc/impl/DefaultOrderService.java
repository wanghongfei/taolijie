package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.constant.acc.OrderType;
import com.fh.taolijie.dao.mapper.PayOrderModelMapper;
import com.fh.taolijie.domain.order.PayOrderModel;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.PermissionException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;
import com.fh.taolijie.service.acc.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by whf on 10/30/15.
 */
@Service
public class DefaultOrderService implements OrderService {
    @Autowired
    private PayOrderModelMapper orderMapper;


    @Override
    @Transactional(readOnly = true)
    public PayOrderModel findOrder(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int updateStatus(Integer orderId, OrderStatus status, String buyer)
            throws OrderNotFoundException, FinalStatusException {

        // 检查订单存在性
        PayOrderModel order = orderMapper.selectByPrimaryKey(orderId);
        if (null == order) {
            throw new OrderNotFoundException();
        }

        // 判断是不是最终状态
        OrderStatus oldStatus = OrderStatus.fromCode(order.getStatus());
        boolean finalStatus = oldStatus.isFinalStatus();
        // 是最终状态
        // 不允许修改
        if (finalStatus) {
            throw new FinalStatusException();
        }


        PayOrderModel example = new PayOrderModel();
        example.setId(orderId);
        example.setUpdateTime(new Date());
        example.setStatus(status.code());
        example.setAlipayTradeNum(buyer);

        return orderMapper.updateByPrimaryKeySelective(example);
    }

    @Override
    public PayOrderModel orderPayCheck(Integer orderId, Integer memId, OrderType type, BigDecimal amt)
            throws OrderNotFoundException, PermissionException, FinalStatusException {

        PayOrderModel order = orderMapper.selectByPrimaryKey(orderId);
        if (null == order) {
            throw new OrderNotFoundException();
        }

        // 检查订单状态是不是已支付
        OrderStatus status = OrderStatus.fromCode(order.getStatus());
        if (status != OrderStatus.PAY_SUCCEED) {
            throw new FinalStatusException();
        }

        // 检查订单类型是不是任务
        OrderType ot = OrderType.fromCode(order.getType());
        if (ot != OrderType.QUEST_PUBLISH) {
            throw new FinalStatusException();
        }

        // 检查订单是不是自己提交的
        if (false == memId.equals(order.getMemberId())) {
            throw new PermissionException();
        }

        // 核对订单金额
        if (null != amt) {
            if (false == order.getAmount().equals(amt)) {
                throw new PermissionException();
            }
        }

        return order;
    }
}
