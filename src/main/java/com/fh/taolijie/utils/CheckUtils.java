package com.fh.taolijie.utils;

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
    public static boolean nullCheck(Object... objs) {
        if (0 == objs.length) {
            return false;
        }

        for (Object o : objs) {
            if (null == o) {
                return false;
            }
        }

        return true;
    }
}
