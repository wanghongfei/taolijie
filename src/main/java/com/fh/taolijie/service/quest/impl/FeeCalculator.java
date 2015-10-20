package com.fh.taolijie.service.quest.impl;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 红包费用计算
 * Created by whf on 9/21/15.
 */
@Service
public class FeeCalculator {
    /**
     * 根据费率计算单个红包的最终价格
     * @param rate
     * @param single
     * @return
     */
    public BigDecimal calculateFee(int rate, BigDecimal single) {
        float newSingle = (1 + rate / 100F) * single.floatValue();

        BigDecimal newFee = new BigDecimal(newSingle);
        newFee.setScale(2, BigDecimal.ROUND_HALF_UP);

        return newFee;
    }
}
