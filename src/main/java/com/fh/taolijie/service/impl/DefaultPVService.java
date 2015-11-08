package com.fh.taolijie.service.impl;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.PostType;
import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.dao.mapper.PVModelMapper;
import com.fh.taolijie.domain.PVModel;
import com.fh.taolijie.domain.PVable;
import com.fh.taolijie.service.PVService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.*;

/**
 * Created by whf on 11/1/15.
 */
@Service
public class DefaultPVService implements PVService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private PVModelMapper pvMapper;

    @Override
    public String getJobPV(Integer jobId) {
        return queryPV(jobId, RedisKey.HASH_PV_JOB);
    }

    @Override
    public String getShPV(Integer shId) {
        return queryPV(shId, RedisKey.HASH_PV_SH);
    }

    @Override
    public void incrAllPV() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY) + 1;

        Jedis jedis = JedisUtils.getClient(jedisPool);
        jedis.hincrBy(RedisKey.HASH_PV_ALL.toString(), String.valueOf(hour), 1);
        JedisUtils.returnJedis(jedisPool, jedis);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public int saveAllPV() {
        Jedis jedis = null;

        try {
            // 从Redis中查出今日PV数据
            jedis = JedisUtils.getClient(jedisPool);
            Map<String, String> map = jedis.hgetAll(RedisKey.HASH_PV_ALL.toString());

            // 排序
            Map<String, String> sortedMap = new TreeMap<>( (k1, k2) -> k1.compareTo(k2));
            sortedMap.putAll(map);

            // 遍历
            StringBuilder sb = new StringBuilder(120);
            for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                // 一组数据的格式: "序号:PV值", 每组数据之前以;分隔
                sb.append(entry.getKey());
                sb.append(":");
                sb.append(entry.getValue());
                sb.append(Constants.DELIMITER);
            }

            // 插入到数据库
            PVModel pvModel = new PVModel();
            pvModel.setCreatedTime(new Date());
            pvModel.setData(sb.toString());
            pvMapper.insertSelective(pvModel);

            // 清空redis中的PV数据
            jedis.del(RedisKey.HASH_PV_ALL.toString());

        } catch (Exception e) {
            throw e;

        } finally {
            // 防止redis连接泄漏
            JedisUtils.returnJedis(jedisPool, jedis);
        }

        return 0;
    }

    @Override
    public void pvMatch(List<? extends PVable> queryList, PostType type) {
        if (null == queryList) {
            return;
        }


        RedisKey key = null;
        if (type == PostType.JOB) {
            key = RedisKey.HASH_PV_JOB;
        } else if (type == PostType.SH) {
            key = RedisKey.HASH_PV_SH;
        }

        RedisKey redisKey = key;

        // 一次查询多个PV信息
        Jedis jedis = JedisUtils.getClient(jedisPool);
        Pipeline pip = jedis.pipelined();
        queryList.forEach( model -> {
            pip.hget(redisKey.toString(), model.getId().toString());
        });

        // 得到批量查询结果
        List<Object> pvList = pip.syncAndReturnAll();
        JedisUtils.returnJedis(jedisPool, jedis);

        // 赋值
        final int LEN = queryList.size();
        for (int ix = 0 ; ix < LEN ; ++ix) {
            String pv = (String) pvList.get(ix);
            if (null == pv) {
                pv = "0";
            }
            queryList.get(ix).setPv(pv);
        }

    }

    @Override
    public ListResult<PVModel> queryPv(Date start, Date end) {
        List<PVModel> list = pvMapper.selectByInterval(start, end);

        return new ListResult<>(list, list.size());
    }

    private String queryPV(Integer postId, RedisKey key) {
        Jedis jedis = JedisUtils.getClient(jedisPool);
        String pv = jedis.hget(key.toString(), postId.toString());
        if (null == pv) {
            pv = "0";
        }

        JedisUtils.returnJedis(jedisPool, jedis);

        return pv;
    }
}
