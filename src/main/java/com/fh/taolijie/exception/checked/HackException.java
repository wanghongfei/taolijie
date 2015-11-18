package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 10/22/15.
 */
public class HackException extends GeneralCheckedException {
    public HackException() {
        super("");
        setCode(ErrorCode.HACKER);
    }
}
