package com.fh.taolijie.constant;

import sun.security.x509.AVA;

/**
 * Created by whf on 9/25/15.
 */
public enum PicType {
    JOB(0),
    SH(1),
    AVATAR(2);

    private int code;

    private PicType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }


    public static PicType fromCode(int code) {
        switch (code) {
            case 0:
                return JOB;

            case 1:
                return SH;

            case 2:
                return AVATAR;

            default:
                return null;
        }
    }
}
