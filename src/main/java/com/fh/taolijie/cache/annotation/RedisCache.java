package com.fh.taolijie.cache.annotation;

import java.lang.annotation.*;

/**
 * Created by whf on 7/7/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisCache {
    /**
     * 指定在Redis Hash Set数据结构中的Set名
     */
    Class value();
}
