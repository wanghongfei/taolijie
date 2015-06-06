package com.fh.taolijie.domain;

/**
 * Created by wanghongfei on 15-5-30.
 */
public abstract class Pageable {
    protected int pageNumber = 0;
    protected int pageSize = 10;

    protected Pageable() {}

    protected Pageable(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

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
