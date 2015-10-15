package com.fh.taolijie.constant;

/**
 * 推荐类型
 * Created by whf on 10/15/15.
 */
public enum RecoType {
    JOB(0),
    SH(1),
    RESUME(2),
    QUEST(3);

    private int code;

    private RecoType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    public static RecoType fromCode(Integer code) {
        if (null == code) {
            return null;
        }

        switch (code) {
            case 0:
                return JOB;

            case 1:
                return SH;

            case 2:
                return RESUME;

            case 3:
                return QUEST;

            default:
                return null;
        }
    }
}
