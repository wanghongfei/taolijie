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
    DONE(3),

    /**
     * 已经过期(结束)
     */
    ENDED(4);

    private int code;

    private EmpQuestStatus(int code) {
        this.code = code;
    }

    public Integer code() {
        return this.code;
    }

    public static EmpQuestStatus fromCode(Integer code) {
        if (null == code) {
            return null;
        }

        switch (code) {
            case 0:
                return UNPUBLISH;

            case 1:
                return WAIT_AUDIT;

            case 2:
                return FAILED;

            case 3:
                return DONE;

            case 4:
                return ENDED;
        }

        return null;
    }
}
