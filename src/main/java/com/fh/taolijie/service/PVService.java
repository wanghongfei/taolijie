package com.fh.taolijie.service;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.PostType;
import com.fh.taolijie.domain.PVModel;
import com.fh.taolijie.domain.PVable;

import java.util.Date;
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
     * 一次查询多个帖子的PV信息
     * @param queryList
     */
    void pvMatch(List<? extends PVable> queryList, PostType type);

    /**
     * 查询指定时间段pv数据
     * @param start
     * @param end
     * @return
     */
    ListResult<PVModel> queryPv(Date start, Date end);
}
