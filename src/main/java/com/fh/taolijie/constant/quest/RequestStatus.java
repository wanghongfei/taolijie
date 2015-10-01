package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 9/22/15.
 */
public enum RequestStatus {

    /**
     * 等待审核
     */
    WAIT_AUDIT("01"),

    /**
     * 商家审核未通过
     */
    EMP_FAILED("02"),

    /**
     * 桃李街审核未通过
     */
    TLJ_FAILED("03"),

    /**
     * 自动通过
     */
    AUTO_PASSED("04"),

    /**
     * 商家审核通过
     */
    EMP_PASSED("05"),

    /**
     * 桃李街审核通过
     */
    TLJ_PASSED("06");

    private String code;

    private RequestStatus(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    /**
     * 判断是不是最终状态
     * @param status
     * @return
     */
    public static boolean isFinalStatus(RequestStatus status) {
        if (status == WAIT_AUDIT) {
            return false;
        }

        return true;
    }

    public static RequestStatus fromCode(String code) {
        if (null == code) {
            return null;
        }

        switch (code) {

            case "01":
                return WAIT_AUDIT;

            case "02":
                return EMP_FAILED;

            case "03":
                return TLJ_FAILED;

            case "04":
                return AUTO_PASSED;

            case "05":
                return EMP_PASSED;

            case "06":
                return TLJ_PASSED;

            default:
                return null;
        }
    }
}
