package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 费用计算
 * Created by whf on 9/21/15.
 */
@Service
public class FeeCalculator {

    @Autowired
    private Jedis jedis;


    /**
     * 计算发布任务费用
     * @param amt 任务数量
     * @return
     */
    public BigDecimal computeQuestFee(double award, int amt) {
        // 得到单个任务的费用
        Map<String, String> map = retrieveConfig();
        String strSingleFee = map.get(RedisKey.QUEST_FEE_RATE.toString());

        BigDecimal feeRate = new BigDecimal(strSingleFee).divide(BigDecimal.valueOf(100));
        BigDecimal singleFee = BigDecimal.ONE.add(feeRate).multiply(BigDecimal.valueOf(award));
        BigDecimal result = singleFee.multiply(BigDecimal.valueOf(amt));

        return result;
    }

    /**
     * 计算桃李街代审核费用
     * @param amt
     * @return
     */
    public BigDecimal computeTljAuditFee(int amt) {
        return multiple(RedisKey.AUDIT_FEE, amt);
    }

    /**
     * 计算顶置费用
     * @param hours
     * @return
     */
    public BigDecimal computeTopFee(int hours) {
        return multiple(RedisKey.TOP_FEE, hours);
    }

    /**
     * 计算加标签费用
     * @param hours
     * @return
     */
    public BigDecimal computeTagFee(int hours) {
        return multiple(RedisKey.TAG_FEE, hours);

    }

    /**
     * 计算刷新费用
     * @return
     */
    public BigDecimal computeFlushFee() {
        Map<String, String> map = retrieveConfig();
        String strSingleFee = map.get(RedisKey.FLUSH_FEE.toString());

        return new BigDecimal(strSingleFee);
    }

    /**
     * 计算调查问卷费用
     * @param amt
     * @return
     */
    public BigDecimal computeSurveyFee(int amt) {
        return multiple(RedisKey.SURVEY_FEE, amt);
    }

    /**
     * 计算题目费用
     * @param amt
     * @return
     */
    public BigDecimal computeQuestionFee(int amt) {
        return multiple(RedisKey.QUESTION_FEE, amt);

    }

    private BigDecimal multiple(RedisKey key, int amt) {
        // 得到单个任务的费用
        Map<String, String> map = retrieveConfig();
        String strSingleFee = map.get(key.toString());
        BigDecimal singleFee = new BigDecimal(strSingleFee);

        return singleFee.multiply(BigDecimal.valueOf(amt));

    }

    private Map<String, String> retrieveConfig() {
        return jedis.hgetAll(RedisKey.SYSCONF.toString());
    }
}
