package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 12/18/15.
 */
public class ScheduleException extends GeneralCheckedException {
    public ScheduleException(String msg) {
        super(msg);
        setCode(ErrorCode.FAILED);
    }

}
