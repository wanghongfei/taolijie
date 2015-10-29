package com.fh.taolijie.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by whf on 10/29/15.
 */
public class JedisUtils {
    private JedisUtils() {}

    public static void returnJedis(JedisPool pool, Jedis jedis) {
        if (null != jedis) {
            pool.returnResourceObject(jedis);
        }
    }
}
