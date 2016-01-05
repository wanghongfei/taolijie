package com.fh.taolijie.service.acc.impl;

import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.dao.mapper.MemSessionModelMapper;
import com.fh.taolijie.domain.MemSessionModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.StringUtils;
import com.fh.taolijie.utils.TimeUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by whf on 1/6/16.
 */
@Service
public class SessionServ {
    private static Logger log = LoggerFactory.getLogger(SessionServ.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private MemSessionModelMapper sessionMapper;

    /**
     * 创建一个session
     * @param mem
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public String createSession(MemberModel mem) {
        String sid = genSid();
        String key = genRedisKey4Session(sid);

        // 在redis中创建
        JedisUtils.performOnce( jedisPool, jedis -> {
            Pipeline pip = jedis.pipelined();
            pip.hset(key, "id", mem.getId().toString());
            pip.hset(key, "username", mem.getUsername());
            pip.hset(key, "role", mem.getRoleList().get(0).getRolename());
            pip.expire(key, (int) TimeUnit.DAYS.toSeconds(30));
            pip.sync();

            return null;
        });

        // 向数据库中插入session记录
        MemSessionModel model = new MemSessionModel();
        model.setSid(sid);
        model.setCreatedTime(new Date());
        model.setMemId(mem.getId());
        sessionMapper.insertSelective(model);


        return sid;

    }

    /**
     * 删除session
     * @param sid
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void deleteSession(String sid) {
        String key = genRedisKey4Session(sid);

        // 删除redis中的session信息
        JedisUtils.performOnce(jedisPool, jedis -> jedis.del(key));

        // 删除数据库中的session记录
        sessionMapper.deleteBySid(sid);
    }

    /**
     * 删除指定用户的所有session
     * @param memId
     */
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void deleteSession(Integer memId) {
        // 查询数据库中的session记录
        List<MemSessionModel> sList = getSessionListByUser(memId, false);

        // 清除对应redis中的session信息
        JedisUtils.performOnce( jedisPool, jedis -> {
            Pipeline pipeline = jedis.pipelined();
            sList.forEach( session -> {
                String key = genRedisKey4Session(session.getSid());
                pipeline.del(key);
            });
            pipeline.sync();

            return null;
        });


        // 删除数据库中的session记录
        sessionMapper.deleteByMember(memId);
    }

    /**
     * 得到指定用户所有session记录
     * @param memId
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public List<MemSessionModel> getSessionListByUser(Integer memId, boolean checkValid) {
        MemSessionModel cmd = new MemSessionModel();
        cmd.setMemId(memId);
        List<MemSessionModel> sList = sessionMapper.findBy(cmd);


        if (checkValid) {
            // 除掉已经过期的sid
            // 查一次redis, 把所有不存在的sid一次性返回
            List<Object> surviveStatus = JedisUtils.performOnce(jedisPool, jedis -> {
                Pipeline pipeline = jedis.pipelined();

                sList.forEach(session -> {
                    pipeline.exists(session.getSid());
                });

                return pipeline.exec().get();
            });

            log.debug("sid survival status = {}", surviveStatus);


            // 得到失效的sid List
            int LEN = sList.size();
            if (LEN > 0) {
                List<String> invalidSidList = new ArrayList<>(LEN / 2);
                for (int ix = 0; ix < LEN; ix++) {
                    Boolean exist = (Boolean) surviveStatus.get(ix);
                    if (exist.equals(Boolean.FALSE)) {
                        invalidSidList.add(sList.get(ix).getSid());
                    }
                }

                log.debug("invalid sid list = {}", invalidSidList);

                // 从数据库中删除失效的sid
                sessionMapper.deleteBySidInBatch(invalidSidList);

                return sessionMapper.findBy(cmd);
            }

        }

        return sList;
    }

    /**
     * 生成session在redis中的key名
     * @param sid
     * @return
     */
    public String genRedisKey4Session(String sid) {
        return StringUtils.concat(37, RedisKey.SESSION.toString(), sid);
    }

    /**
     * 生成sid
     * @return
     */
    public String genSid() {
        String random = RandomStringUtils.randomAlphabetic(22);

        return StringUtils.concat(
                30,
                TimeUtil.date2String(new Date(), Constants.DATE_FORMAT_FOR_SESSION),
                random
        );
    }
}
