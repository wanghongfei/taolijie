package com.fh.taolijie.constant;

import org.springframework.http.HttpStatus;

/**
 * 错误代码定义
 * Created by whf on 8/15/15.
 */
public enum ErrorCode {
    SUCCESS(0, "success"),
    FAILED(-1, "failed"),

    TOO_FREQUENT(1, "too frequent"),
    BAD_USERNAME(2, "bad username"),
    BAD_PASSWORD(3, "bad password"),
    USER_NOT_EXIST(4, "user does not exist"),
    USER_EXIST(5, "user exists"),
    USER_INVALID(6, "invalid user"),
    NOT_LOGGED_IN(7, "not logged in"),
    RE_PASSWORD_ERROR(8, "password not identical"),

    CATE_NOT_EMPTY(9, "this category is not empty!"),

    EMPTY_FIELD(10, "field is empty!"),
    INVALID_PARAMETER(11, "invalid parameter!"),
    NOT_FOUND(12, "not found"),
    PERMISSION_ERROR(13, "PERMISSION_ERROR"),
    CAN_NOT_DEL_CURRENT_USER(14, "cannot del current user!"),
    EXISTS(15, "already exists"),
    ALREADY_DONE(16, "already done"),

    BAD_NUMBER(17, "invalid number"),

    BALANCE_NOT_ENOUGH(18, "balance not enough"),
    CASH_ACC_NOT_EXIST(19, "cash account doesn't exist"),
    QUEST_ASSIGNED(20, "quest has been assigned"),
    QUEST_ZERO(21, "quest has no left amount"),
    QUEST_EXPIRED(22, "expired"),

    QUEST_NOT_ASSIGNED(23, "not assigned"),
    REPEAT(24, "repeat submit"),

    VALIDATION_CODE_ERROR(25, "validation code mismatch"),

    ALREADY_VERIFIED(26, "already verified"),

    NOT_STARTED(27, "not start yet"),
    STATUS_CANNOT_CHANGE(28, "status cannot be changed"),
    UNBIND_FIRST(29, "unbind phone first"),
    AUDIT_NOT_ENOUGH(30, "audit not enough"),

    INVALID_PHONE_NUMBER(31, "phone not found"),

    NO_QUESTION_FOR_QUEST(32, "quest has no question"),
    HACKER(33, "what r u doing?"),

    SMS_INVOKE_FAILED(34, "SMS vendor refused"),

    WRONG_ANSWER(35, "answer's wrong"),
    ANSWER_EXIST(36, "answer exists"),

    AWARD_TOO_LITTLE(37, "not enough award");

    private final String msg;
    private final int code;

    private ErrorCode(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String toString() {
        return this.msg;
    }

    public int value() {
        return this.code;
    }
}
