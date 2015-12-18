package com.fh.taolijie.constant;

/**
 * Created by whf on 12/18/15.
 */
public enum ScheduleAddr {
    /**
     * 任务自动过期
     */
    QUEST_AUTO_EXPIRE;


    public String code() {
        return this.toString().toLowerCase();
    }
}
