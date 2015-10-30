package com.fh.taolijie.controller.restful.pay;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.acc.OrderType;
import com.fh.taolijie.constant.acc.PayChanType;
import com.fh.taolijie.constant.acc.PayType;
import com.fh.taolijie.domain.order.PayOrderModel;
import com.fh.taolijie.dto.OrderSignDto;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.acc.ChargeService;
import com.fh.taolijie.service.acc.PayService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by whf on 10/30/15.
 */
@RestController
@RequestMapping("/api/pay")
public class RestPayCtr {

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private PayService payService;

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
                                    HttpServletRequest req) {

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


        try {
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

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);
        }


    }
}
