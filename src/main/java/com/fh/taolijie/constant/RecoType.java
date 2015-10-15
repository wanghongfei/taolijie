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
}
