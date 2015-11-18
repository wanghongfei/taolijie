package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/25/15.
 */
public class SecretQuestionExistException extends GeneralCheckedException {
    public SecretQuestionExistException() {
        super("");
        setCode(ErrorCode.EXISTS);
    }
}
