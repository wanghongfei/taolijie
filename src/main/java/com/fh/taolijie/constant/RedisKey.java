package com.fh.taolijie.constant;

/**
 * Created by whf on 8/2/15.
 */
public enum RedisKey {
    CREDITS_LEVEL,
    CREDITS_OPERATION,
    SESSION,

    // for Redis
    SYSCONF,
    QUEST_FEE_RATE,
    AUDIT_FEE,
    TOP_FEE,
    TAG_FEE,
    QUESTION_FEE,
    SURVEY_FEE,
    MAX_EMP_WITHDRAW_DAY,
    MAX_STU_WITHDRAW_DAY;

    public String toString() {
        switch (this) {
            case SESSION:
                return "SESSION";

            case CREDITS_LEVEL:
                return CREDITS_LEVEL.toString();

            case CREDITS_OPERATION:
                return CREDITS_OPERATION.toString();

            case SYSCONF:
                return "sysconf";

            case QUEST_FEE_RATE:
                return "quest_fee_rate";

            case SURVEY_FEE:
                return "survey_fee";

            case AUDIT_FEE:
                return "audit_fee";

            case TAG_FEE:
                return "tag_fee";

            case TOP_FEE:
                return "top_fee";

            case QUESTION_FEE:
                return "question_fee";

            case MAX_EMP_WITHDRAW_DAY:
                return "max_emp_withdraw_fee";

            case MAX_STU_WITHDRAW_DAY:
                return "max_stu_withdraw_fee";
        }

        return null;
    }
}
