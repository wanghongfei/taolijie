package com.fh.taolijie.exception.checked.certi;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 9/26/15.
 */
public class ApplicationDuplicatedException extends GeneralCheckedException {
    public ApplicationDuplicatedException(String msg) {
        super(msg);
        setCode(ErrorCode.REPEAT);
    }
}
