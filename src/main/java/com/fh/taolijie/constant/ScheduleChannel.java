package com.fh.taolijie.constant;

/**
 * Created by whf on 9/30/15.
 */
public enum ScheduleChannel {
    POST_JOB("post-job");

    private String code;

    private ScheduleChannel(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
