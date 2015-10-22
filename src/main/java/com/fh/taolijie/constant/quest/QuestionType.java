package com.fh.taolijie.constant.quest;

/**
 * Created by whf on 10/22/15.
 */
public enum QuestionType {
    /**
     * 答题
     */
    EXAMINATION(0),
    /**
     * 调查问卷
     */
    SURVEY(1);

    private int code;

    private QuestionType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    public static QuestionType fromCode(Integer code) {
        if (null == code) {
            return null;
        }

        switch (code) {
            case 0:
                return EXAMINATION;

            case 1:
                return SURVEY;
        }

        return null;
    }
}
