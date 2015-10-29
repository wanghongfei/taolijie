package com.fh.taolijie.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by whf on 10/29/15.
 */
public class JedisUtils {
    private JedisUtils() {}


    /**
     * 从连接池中取出一个client
     * @param pool
     * @return
     */
    public static Jedis getClient(JedisPool pool) {

        try {
            return pool.getResource();

        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtils.logException(ex);

        }

        return null;
    }

    /**
     * 返还client给连接池
     * @param pool
     * @param jedis
     */
    public static void returnJedis(JedisPool pool, Jedis jedis) {
        if (null != jedis) {
            pool.returnResourceObject(jedis);
        }
    }
}
