package com.fh.taolijie.exception.checked.acc;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/31/15.
 */
public class SecretQuestionWrongException extends GeneralCheckedException {
    public SecretQuestionWrongException() {
        super("");
        setCode(ErrorCode.WRONG_ANSWER);
    }
}
