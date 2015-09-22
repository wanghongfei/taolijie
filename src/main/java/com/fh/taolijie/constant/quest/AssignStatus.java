package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 9/22/15.
 */
public enum AssignStatus {
    ASSIGNED("00"),
    DONE("01"),
    ENDED("02");

    private String code;

    private AssignStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
