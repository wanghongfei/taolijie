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

    // for qiniu
    CONF_QINIU,
    CONF_QINIU_AK,
    CONF_QINIU_SK,
    CONF_QINIU_BUCKET,

    // 统计信息
    HASH_PV_SH,
    HASH_PV_JOB,
    HASH_PV_ALL,

    // for alipay
    ALIPAY_CONF,
    ALIPAY_ACC,
    PID,
    SERVICE_NAME,
    CHARSET,
    SIGN_TYPE,
    NOTIFY_URL,

    // for weichat
    WECHAT_CONF,
    WECHAT_APPID,
    WECHAT_MCHID,
    WECHAT_SECRET,
    WECHAT_CERTI_PATH,

    // for Baidu Map
    MAP_CONF,
    MAP_AK,
    MAP_COORD_TYPE,
    MAP_CREATE_PT,
    MAP_RANGE_QUERY,
    MAP_GEOTABLE_ID;

    public String toString() {
        switch (this) {
            case MAP_RANGE_QUERY:
                return "url_range_query";

            case MAP_CREATE_PT:
                return "url_create_pt";

            case MAP_CONF:
                return "conf:map";

            case MAP_AK:
                return "ak";

            case MAP_COORD_TYPE:
                return "coord_type";

            case MAP_GEOTABLE_ID:
                return "geotable_id";

            case WECHAT_CERTI_PATH:
                return "certi_path";

            case WECHAT_SECRET:
                return "secret";

            case WECHAT_CONF:
                return "conf:wechat";

            case WECHAT_APPID:
                return "appid";

            case WECHAT_MCHID:
                return "mchid";

            case CONF_QINIU_BUCKET:
                return "bucket";

            case CONF_QINIU:
                return "conf:qiniu";

            case CONF_QINIU_AK:
                return "ak";

            case CONF_QINIU_SK:
                return "sk";

            case HASH_PV_JOB:
                return "pv:job";

            case HASH_PV_SH:
                return "pv:sh";

            case HASH_PV_ALL:
                return "pv:all";

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
