package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/25/15.
 */
public class SecretQuestionNotExistException extends GeneralCheckedException {
    public SecretQuestionNotExistException() {
        super("");
        setCode(ErrorCode.NOT_FOUND);
    }
}
