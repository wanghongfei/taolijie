package com.fh.taolijie.constant.acc;


import com.fh.taolijie.service.acc.ChargeService;

/**
 * Created by whf on 9/21/15.
 */
public enum OrderType {
    /**
     * 充值到钱包订单
     */
    CHARGE("00"),

    /**
     * 任务推荐
     */
    QUEST_TOP_ORDER("01"),

    /**
     * 任务加标签
     */
    QUEST_TAG_ORDER("02"),

    /**
     * 发布任务
     */
    QUEST_PUBLISH("03");


    private String code;

    private OrderType(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public String memo() {
        switch (this) {
            case CHARGE:
                return "钱包充值";

            case QUEST_TOP_ORDER:
                return "任务推荐";

            case QUEST_TAG_ORDER:
                return "标签推荐";

            case QUEST_PUBLISH:
                return "任务发布";
        }

        return null;
    }

    public static OrderType fromCode(String code) {
        switch (code) {
            case "00":
                return CHARGE;

            case "01":
                return QUEST_TOP_ORDER;

            case "02":
                return QUEST_TAG_ORDER;

            case "03":
                return QUEST_PUBLISH;

        }

        return null;
    }

}
