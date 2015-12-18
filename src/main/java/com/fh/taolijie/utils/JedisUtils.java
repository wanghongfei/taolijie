package com.fh.taolijie.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.function.Function;

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

    /**
     * 通过lambda实现自动获取,释放连接对象
     * @param pool jedis连接池
     * @param func 要执行的操作
     * @param <R> 方法的返回类型
     * @return
     */
    public static <R> R performOnce(JedisPool pool, Function<Jedis, R> func) {
        Jedis jedis = pool.getResource();

        R retVal = null;
        try {
            // 执行资源操作逻辑
            retVal = func.apply(jedis);

        } catch (Exception ex) {
            LogUtils.logException(ex);

        } finally {
            pool.returnResourceObject(jedis);
        }


        return retVal;
    }
}
