package com.fh.taolijie.constant;

/**
 * Created by whf on 12/18/15.
 */
public enum ScheduleAddr {
    /**
     * 任务自动过期
     */
    QUEST_AUTO_EXPIRE,

    /**
     * 任务自动审核通过
     */
    QUEST_AUTO_AUDIT,

    /**
     * 任务过期后的24小时(结算)
     */
    QUEST_EXPIRE,

    /**
     * 每日pv落地
     */
    STATISTICS_PV_ALL;


    public String code() {
        return this.toString().toLowerCase();
    }
}
