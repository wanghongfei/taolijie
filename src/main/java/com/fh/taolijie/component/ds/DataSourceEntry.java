package com.fh.taolijie.component.ds;

import org.springframework.stereotype.Component;

/**
 * 数据源切换工具
 * Created by whf on 5/28/16.
 */
@Component
public class DataSourceEntry {
    private ThreadLocal<String> dsMap = new ThreadLocal<>();

    private String DEFAULT_DS = "w1";


    public void set(String dsName) {
        dsMap.set(dsName);
    }

    public String get() {
        return dsMap.get();
    }

    public void reset() {
        dsMap.set(DEFAULT_DS);
    }
}
