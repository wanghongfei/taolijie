package com.fh.taolijie.service.acc;

import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.exception.checked.FinalStatusException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;

/**
 * Created by whf on 10/30/15.
 */
public interface OrderService {
    /**
     * 更新订单状态
     * @param orderId
     * @param status
     * @return
     */
    int updateStatus(Integer orderId, OrderStatus status, String buyer)
            throws OrderNotFoundException, FinalStatusException;
}
