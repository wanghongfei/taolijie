package com.fh.taolijie.service.acc;

import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.dto.OrderSignDto;

import java.util.Map;

/**
 * Created by whf on 10/29/15.
 */
public interface PayService {
    OrderSignDto sign(Map<String, String> map, PayType type);
}
