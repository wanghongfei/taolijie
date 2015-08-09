package com.fh.taolijie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fh.taolijie.utils.Constants;

/**
 * Created by wanghongfei on 15-5-30.
 */
public abstract class Pageable {
    @JsonIgnore
    protected int pageNumber = 0;
    @JsonIgnore
    protected int pageSize = Constants.PAGE_CAPACITY;

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

    @Override
    public String toString() {
        return "Pageable{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }

}
