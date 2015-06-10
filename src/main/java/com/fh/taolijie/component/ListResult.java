package com.fh.taolijie.component;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-10.
 */
public class ListResult<T> {
    private List<T> list;
    private int pageCount;

    public ListResult(List<T> list, int pageCount) {
        this(list);

        this.pageCount = pageCount;
    }

    public ListResult(List<T> list) {
        this.list = list;
    }

    public ListResult() {}

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
