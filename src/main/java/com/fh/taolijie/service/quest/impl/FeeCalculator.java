package com.fh.taolijie.service.quest.impl;

import com.fh.taolijie.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 费用计算
 * Created by whf on 9/21/15.
 */
@Service
public class FeeCalculator {

    @Qualifier("redisTemplateForString")
    @Autowired
    private StringRedisTemplate rt;


    /**
     * 计算发布任务费用
     * @param amt 任务数量
     * @return
     */
    public BigDecimal computeQuestFee(int amt) {
        // 得到单个任务的费用
        Map<Object, Object> map = retrieveConfig();
        String strSingleFee = (String) map.get(RedisKey.QUEST_FEE_RATE.toString());
        double singleFee = Double.valueOf(strSingleFee);
        double result = (1 + singleFee / 100) * amt;

        // 计算总费用
        return new BigDecimal(result);
    }

    private Map<Object, Object> retrieveConfig() {
        return rt.opsForHash().entries(RedisKey.SYSCONF.toString());
    }
}
