package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 9/22/15.
 */
public enum AssignStatus {
    ASSIGNED("00"),
    DONE("01"),
    /**
     * 已超时
     */
    ENDED("02"),
    /**
     * 已提交
     */
    SUBMITTED("03");

    private String code;

    private AssignStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static AssignStatus fromCode(String code) {
        switch (code) {
            case "00":
                return ASSIGNED;

            case "01":
                return DONE;

            case "02":
                return ENDED;

            case "03":
                return SUBMITTED;

            default:
                return null;
        }
    }
}
