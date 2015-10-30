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
    MAX_STU_WITHDRAW_DAY,
    FLUSH_FEE,
    RSA_PUB_KEY,
    RSA_PRI_KEY,

    // for alipay
    ALIPAY_CONF,
    ALIPAY_ACC,
    PID,
    SERVICE_NAME,
    CHARSET,
    SIGN_TYPE,
    NOTIFY_URL;

    public String toString() {
        switch (this) {
            case ALIPAY_ACC:
                return "acc";

            case RSA_PRI_KEY:
                return "rsa_pri_key";

            case RSA_PUB_KEY:
                return "rsa_pub_key";

            case ALIPAY_CONF:
                return "conf:alipay";

            case PID:
                return "PID";

            case SERVICE_NAME:
                return "app_pay_service";

            case CHARSET:
                return "charset";

            case SIGN_TYPE:
                return "sign_type";

            case NOTIFY_URL:
                return "notify_url";

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

            case FLUSH_FEE:
                return "flush_fee";
        }

        return null;
    }
}
