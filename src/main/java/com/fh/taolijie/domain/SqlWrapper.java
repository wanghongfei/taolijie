package com.fh.taolijie.domain;

/**
 * Created by whf on 8/14/15.
 */
public class SqlWrapper {
    private String sql;

    public SqlWrapper(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
