package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 9/27/15.
 */
public enum QuestStatus {
    /**
     * 未领取
     */
    NOT_ASSIGNED(0),
    /**
     * 已领取
     */
    ASSIGNED(1),
    /**
     * 已结束
     */
    ENDED(2),
    /**
     * 已完成
     */
    DONE(3);

    private int code;

    private QuestStatus(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

}
