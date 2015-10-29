package com.fh.taolijie.test.service.quest;

import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.service.acc.impl.DefaultPayService;
import com.fh.taolijie.test.BaseSpringDataTestClass;
import com.fh.taolijie.utils.SignUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whf on 10/29/15.
 */
@ContextConfiguration(classes = {
        DefaultPayService.class
})
public class PayServiceTest extends BaseSpringDataTestClass {
    @Autowired
    private DefaultPayService service;

    @Test
    public void testGenParam() {
        Map<String, String> map = new HashMap<>();
        map.put("service", "create_direct_pay_by_user");
        map.put("partner", "2088501624560335");
        map.put("_input_charset", "utf-8");
        map.put("payment_type", "1");
        map.put("notify_url", "http://www.xxx.com/alipay/notify_url.jsp");
        map.put("return_url", "http://www.xxx.com/alipay/return_url.jsp");
        map.put("seller_email", "alipayrisk10@alipay.com");
        map.put("out_trade_no", "2012113000001");
        map.put("subject", "测试订单");
        map.put("total_fee", "0.01");

/*        String queryStr = service.genParameter(map, PayType.ALIPAY);
        System.out.println(queryStr);
        Assert.assertEquals(queryStr, "_input_charset=utf-8&notify_url=http://www.xxx.com/alipay/notify_url.jsp&out_trade_no=2012113000001&partner=2088501624560335&payment_type=1&return_url=http://www.xxx.com/alipay/return_url.jsp&seller_email=alipayrisk10@alipay.com&service=create_direct_pay_by_user&subject=测试订单&total_fee=0.01");*/



    }
}
