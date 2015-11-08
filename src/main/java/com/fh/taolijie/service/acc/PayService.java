package com.fh.taolijie.service.acc;

import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.dto.OrderSignDto;

import java.io.IOException;
import java.util.Map;

/**
 * Created by whf on 10/29/15.
 */
public interface PayService {
    /**
     * 对请求参数进行签名
     * @param map
     * @param type
     * @return
     */
    OrderSignDto sign(Map<String, String> map, PayType type);

    /**
     * 验证alipay的notify_id
     * @param notifyId
     * @return
     * @throws IOException
     */
    boolean verifyNotify(String notifyId) throws IOException;
}
