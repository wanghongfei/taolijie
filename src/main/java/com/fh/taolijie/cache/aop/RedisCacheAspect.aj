package com.fh.taolijie.cache.aop;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by whf on 7/8/15.
 */
@Aspect
@Component
public class RedisCacheAspect {
    @Qualifier("redisTemplateForString")
    @Autowired
    StringRedisTemplate rt;

    //@Pointcut("@annotation(com.fh.taolijie.cache.annotation.RedisCache)")
/*    @Pointcut("execution(* select*(..))")
    public void cacheMethod() {
    }*/

    @Around("@annotation(com.fh.taolijie.cache.annotation.RedisCache)")
    public void before() {
        LogUtils.getInfoLogger().info("!!!!!!!!!!!! this is before!");
    }


    @Around("@annotation(com.fh.taolijie.cache.annotation.RedisCache)")
    public Object around(ProceedingJoinPoint jp) throws Throwable {

        String clazzName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();

        LogUtils.getInfoLogger().info("!!!!!!!!!!!!!!!!!!缓存方法{}", methodName);

        String key = genKey(clazzName, methodName, args);

        // 检查redis中是否有缓存
        String value = rt.opsForValue().get(key);
        Object result = null;
        if (null == value) {
            // cache wasn't hit
            // invoke target method
            result = jp.proceed(args);

            // serialize result
            value = JSON.toJSONString(result);

            // put value into redis cache
            rt.opsForValue().set(key, value);
        } else {
            // cache was hit
            // deserialize string from cache
            Class returnType = ((MethodSignature) jp.getSignature()).getReturnType();
            result = JSON.parseObject(value, returnType);
        }

        return result;
    }

    /**
     * 根据类名、方法名和参数生成key
     * @param clazzName
     * @param methodName
     * @param args
     * @return
     */
    protected String genKey(String clazzName, String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder(clazzName);
        sb.append(Constants.DELIMITER);
        sb.append(methodName);
        sb.append(Constants.DELIMITER);

        for (Object obj : args) {
            sb.append(obj.toString());
            sb.append(Constants.DELIMITER);
        }

        return sb.toString();
    }
}
