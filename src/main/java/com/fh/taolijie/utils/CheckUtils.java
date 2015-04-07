package com.fh.taolijie.utils;

import com.fh.taolijie.exception.unchecked.EntityNotExistException;

/**
 * Created by wanghongfei on 15-4-3.
 */
public class CheckUtils {
    private CheckUtils() {}

    /**
     * 检查形参中是否全为非null
     * @param objs
     * @return 如果有一个null则返回false
     */
    public static void nullCheck(Object... objs) {
        if (0 == objs.length) {
            throw new EntityNotExistException("entity cannot be null!");
        }

        for (Object o : objs) {
            if (null == o) {
                throw new EntityNotExistException("entity " + o + " cannot be null!");
            }
        }
    }
}
