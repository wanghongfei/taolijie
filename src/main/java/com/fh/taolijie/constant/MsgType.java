package com.fh.taolijie.constant;

/**
 * Created by whf on 9/30/15.
 */
public enum MsgType {
    /**
     * cron表达式风格
     */
    CRON_STYLE(0),
    /**
     * 指定日期执行
     */
    DATE_STYLE(1),
    /**
     * 删除任务请求
     */
    DEL_JOB(2),

    /**
     * 由调度中心发向网站后台
     */
    AUTO_AUDIT(3);

    private int code;

    private MsgType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    public static MsgType fromCode(int code) {
        switch (code) {
            case 0:
                return CRON_STYLE;

            case 1:
                return DATE_STYLE;

            case 2:
                return DEL_JOB;

            case 3:
                return AUTO_AUDIT;

            default:
                return null;
        }
    }
}
