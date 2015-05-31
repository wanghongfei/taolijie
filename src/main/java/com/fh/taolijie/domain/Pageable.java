package com.fh.taolijie.domain;

/**
 * Created by wanghongfei on 15-5-30.
 */
public abstract class Pageable {
    protected int pageNumber;
    protected int pageSize;

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
