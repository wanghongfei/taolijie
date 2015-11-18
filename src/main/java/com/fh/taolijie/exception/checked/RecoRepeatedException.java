package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 10/17/15.
 */
public class RecoRepeatedException extends GeneralCheckedException {
    public RecoRepeatedException() {
        super("");
        setCode(ErrorCode.REPEAT);
    }
}
