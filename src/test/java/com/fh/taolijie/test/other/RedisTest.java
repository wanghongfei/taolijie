package com.fh.taolijie.test.other;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by whf on 10/29/15.
 */
public class RedisTest {
    @Test
    public void testRedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1);
        config.setMaxIdle(1);
        config.setBlockWhenExhausted(false);
        //config.setTestOnBorrow(TEST_ON_BORROW);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379, 1000, "111111");

        Jedis jedis = pool.getResource();
        pool.returnResourceObject(jedis);
        jedis = pool.getResource();
    }
}
