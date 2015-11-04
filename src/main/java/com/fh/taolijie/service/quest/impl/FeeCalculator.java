package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 费用计算
 * Created by whf on 9/21/15.
 */
@Service
public class FeeCalculator {

    @Autowired
    private JedisPool jedisPool;


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
     * @param days
     * @return
     */
    public BigDecimal computeTopFee(int days) {
        return multiple(RedisKey.TOP_FEE, days);
    }

    /**
     * 计算加标签费用
     * @param days
     * @return
     */
    public BigDecimal computeTagFee(int days) {
        return multiple(RedisKey.TAG_FEE, days);

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
     * @return
     */
    public BigDecimal computeQuestionFee(BigDecimal questAward, int questionAmt) {
        return questAward.divide(BigDecimal.valueOf(questionAmt), BigDecimal.ROUND_HALF_UP);

    }

    private BigDecimal multiple(RedisKey key, int amt) {
        // 得到单个任务的费用
        Map<String, String> map = retrieveConfig();
        String strSingleFee = map.get(key.toString());
        BigDecimal singleFee = new BigDecimal(strSingleFee);

        return singleFee.multiply(BigDecimal.valueOf(amt));

    }

    private Map<String, String> retrieveConfig() {
        Jedis jedis = JedisUtils.getClient(jedisPool);

        Map<String, String> map = jedis.hgetAll(RedisKey.SYSCONF.toString());

        JedisUtils.returnJedis(jedisPool, jedis);

        return map;
    }
}
