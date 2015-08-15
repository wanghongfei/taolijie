package com.fh.taolijie.domain;

import sun.awt.image.IntegerInterleavedRaster;

/**
 * Created by whf on 8/14/15.
 */
public class SqlWrapper {
    private String sql;
    private Integer id;

    public SqlWrapper(String sql) {
        this.sql = sql;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
