package com.fh.taolijie.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghongfei on 15-6-5.
 */
public class Pagination {
    private int pageNumber;
    private int pageSize;

    private static String PAGE_NUMBER = "pageNumber";
    private static String PAGE_SIZE = "pageSize";

    private Map<String, Integer> map;

    public Pagination(int pageNumber, int pageSize) {
        map = new HashMap<>();
        map.put(PAGE_NUMBER, pageNumber);
        map.put(PAGE_SIZE, pageSize);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Map<String, Integer> getMap() {
        return map;
    }
}
