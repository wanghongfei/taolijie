package com.fh.taolijie.service;

import com.fh.taolijie.domain.PVable;

import java.util.List;

/**
 * Created by whf on 11/1/15.
 */
public interface PVService {
    /**
     * 查询兼职PV
     * @param jobId
     * @return
     */
    String getJobPV(Integer jobId);

    /**
     * 查询二手PV
     * @param shId
     * @return
     */
    String getShPV(Integer shId);

    /**
     * 分时间段统计总PV
     */
    void incrAllPV();

    /**
     * 保存当天PV信息到数据库
     * @return
     */
    int saveAllPV();

    /**
     * 一次查询多个PV信息
     * @param queryList
     */
    void pvMatch(List<? extends PVable> queryList);
}
