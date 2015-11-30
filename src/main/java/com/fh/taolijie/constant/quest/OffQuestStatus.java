package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 11/30/15.
 */
public enum  OffQuestStatus {
    PUBLISHED("00"),
    /**
     * @deprecated
     */
    ASSIGNED("01"),
    OFFLINE("02");

    private String code;

    private OffQuestStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
