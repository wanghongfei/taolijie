package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.dao.mapper.PayOrderModelMapper;
import com.fh.taolijie.domain.order.PayOrderModel;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;
import com.fh.taolijie.service.acc.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Created by whf on 10/30/15.
 */
@Service
public class DefaultOrderService implements OrderService {
    @Autowired
    private PayOrderModelMapper orderMapper;


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
}
