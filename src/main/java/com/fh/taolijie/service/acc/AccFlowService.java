package com.fh.taolijie.service.acc;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.AccFlowModel;

import java.util.Date;

/**
 * 账户流水查询业务接口
 * Created by whf on 9/21/15.
 */
public interface AccFlowService {
    /**
     * 查询指定账户的流水记录
     * @param accId
     * @param pn
     * @param ps
     * @return
     */
    ListResult<AccFlowModel> findByAcc(Integer accId, int pn, int ps);

    /**
     * 查询指定账户指定时间段的流水记录
     * @param accId
     * @param start 开始日期
     * @param end 结束日期
     * @param pn
     * @param ps
     * @return
     */
    ListResult<AccFlowModel> findByAccAndInterval(Integer accId, Date start, Date end, int pn, int ps);
}
