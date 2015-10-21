package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 10/21/15.
 */
public enum EmpQuestStatus {
    /**
     * 未发布
     */
    UNPUBLISH(0),
    /**
     * 审核中
     */
    WAIT_AUDIT(1),
    /**
     * 审核失败
     */
    FAILED(2),
    /**
     * 审核通过
     */
    DONE(3);

    private int code;

    private EmpQuestStatus(int code) {
        this.code = code;
    }

    public Integer code() {
        return this.code;
    }
}
