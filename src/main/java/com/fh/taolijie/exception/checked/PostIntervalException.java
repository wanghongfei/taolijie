package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * 两次发布间隔太短
 * Created by whf on 11/30/15.
 */
public class PostIntervalException extends GeneralCheckedException {
    public PostIntervalException() {
        super("");
        setCode(ErrorCode.TOO_FREQUENT);
    }
}
