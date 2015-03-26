package com.fh.taolijie.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongfei on 15-3-26.
 */
public class FieldCache {
    /**
     * K: 对象的全限定名
     * V: 这个对象的Field列表
     */
    public static Map<String, List<Field>> cacheMap = new HashMap<>();
}
