package com.fh.taolijie.component.ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源实现类
 * Created by whf on 5/28/16.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private DataSourceEntry dataSourceEntry;



    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceEntry.get();
    }

    public void setDataSourceEntry(DataSourceEntry dataSourceEntry) {
        this.dataSourceEntry = dataSourceEntry;
    }
}
