package com.fh.taolijie.service.acc;

import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.constant.acc.OrderType;
import com.fh.taolijie.domain.order.PayOrderModel;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.PermissionException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;

import java.math.BigDecimal;

/**
 * Created by whf on 10/30/15.
 */
public interface OrderService {
    PayOrderModel findOrder(Integer orderId);

    /**
     * 更新订单状态
     * @param orderId
     * @param status
     * @return
     */
    int updateStatus(Integer orderId, OrderStatus status, String buyer)
            throws OrderNotFoundException, FinalStatusException;

    /**
     * 支付订单检查
     */
    PayOrderModel orderPayCheck(Integer orderId, Integer memId, OrderType type, BigDecimal amt)
            throws OrderNotFoundException, PermissionException, FinalStatusException;
}
