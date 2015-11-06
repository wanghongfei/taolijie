package com.fh.taolijie.utils;

/**
 * Created by wanghongfei on 15-6-21.
 */
public class PageUtils {
    private PageUtils() {}

    public static int getFirstResult(int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            pageNumber = 0;
        }

        return pageNumber * pageSize;
    }
}
