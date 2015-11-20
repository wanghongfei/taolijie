package com.fh.taolijie.controller.restful.pay;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.AlipayOrderStatus;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.acc.OrderStatus;
import com.fh.taolijie.constant.acc.OrderType;
import com.fh.taolijie.constant.acc.PayChanType;
import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.domain.order.PayOrderModel;
import com.fh.taolijie.dto.AlipayAsyncDto;
import com.fh.taolijie.dto.OrderSignDto;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.acc.ChargeService;
import com.fh.taolijie.service.acc.OrderService;
import com.fh.taolijie.service.acc.PayService;
import com.fh.taolijie.service.acc.WithdrawService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by whf on 10/30/15.
 */
@RestController
@RequestMapping("/api/pay")
public class RestPayCtr {

    private static Logger logger = LoggerFactory.getLogger(RestPayCtr.class);

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WithdrawService withdrawService;

    /**
     * 下单
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText chargeApply(@RequestParam String subject,
                                    @RequestParam(defaultValue = "1") String paymentType,
                                    @RequestParam String totalFee,
                                    @RequestParam String body,

                                    @RequestParam String orderType,
                                    @RequestParam Integer payChan,
                                    HttpServletRequest req) throws GeneralCheckedException {

        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        Integer memId = credential.getId();

        // 验证orderType
        OrderType type = OrderType.fromCode(orderType);
        // 验证payChan
        PayChanType chanType = PayChanType.fromCode(payChan);
        if (null == chanType || null == type) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);

        }

        PayOrderModel order = new PayOrderModel();
        order.setMemberId(memId);
        //order.setAlipayTradeNum(tradeNum);
        order.setAmount(new BigDecimal(totalFee));
        order.setType(orderType);
        order.setPayChan(payChan);


        // 生成订单
        chargeService.chargeApply(order);
        // 得到订单号
        Integer orderId = order.getId();

        // 签名
        Map<String, String> map = new HashMap<>(6);
        map.put("subject", StringUtils.surroundQuotation(subject));
        map.put("payment_type", StringUtils.surroundQuotation(paymentType));
        map.put("total_fee", StringUtils.surroundQuotation(totalFee));
        map.put("body", StringUtils.surroundQuotation(body));
        map.put("out_trade_no", StringUtils.surroundQuotation(orderId.toString()));

        OrderSignDto dto = payService.sign(map, PayType.ALIPAY);
        dto.setOrderId(orderId);

        return new ResponseText(dto);
    }

    /**
     * 支付宝异步通知.
     *
     * 两步验证: 1. 验证签名是否正确
     * 2. 验证notify_id是否合法
     * @return
     */
    @RequestMapping(value = "/alipay/async", method = RequestMethod.POST)
    public String alipayAsyncNotification(AlipayAsyncDto dto,
                                          HttpServletRequest req) {

        if (logger.isDebugEnabled()) {
            logger.debug("alipay async message:{}", dto.toString());
        }

        // 验证签名
        Map<String, String> paramMap = SessionUtils.getAllParameters(req);
        String sign = payService.sign(paramMap, PayType.ALIPAY).getSign();

        if (logger.isDebugEnabled()) {
            logger.debug("alipay sign = {}, my sign = {}", dto.getSign(), sign);
        }

        if (false == dto.getSign().equals(sign)) {
            return "FUCK YOU";
        }

        // 验证通知是否是支付宝发送
        // (验证notify_id参数)
        try {
            boolean result = payService.verifyNotify(dto.getNotify_id());
            // 验证失败
            if (!result) {
                return "invalid request";
            }

        } catch (IOException e) {
            LogUtils.logException(e);
            e.printStackTrace();
            return "500";

        }


        try {
            // 取出订单号
            Integer orderId = Integer.valueOf(dto.out_trade_no);

            // 只处理支付成功的通知
            if (dto.getTrade_status().equals(AlipayOrderStatus.TRADE_SUCCESS)) {
                // 标记订单状态为支付成功
                orderService.updateStatus(orderId, OrderStatus.PAY_SUCCEED, dto.trade_no);
            }

        } catch (Exception ex) {
            return "F";
        }


        return "success";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseText wechatPayment() throws Exception {
        withdrawService.doWechatPaymentHttp();
        return ResponseText.getSuccessResponseText();
    }
}
