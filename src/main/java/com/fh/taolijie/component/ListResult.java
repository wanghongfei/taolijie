package com.fh.taolijie.component;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-10.
 */
public class ListResult<T> {
    private List<T> list;
    private long resultCount;

    public ListResult(List<T> list, long pageCount) {
        this(list);

        this.resultCount = pageCount;
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

    public long getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
}
