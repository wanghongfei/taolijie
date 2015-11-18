package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 10/22/15.
 */
public class QuestionNotFoundException extends GeneralCheckedException {
    public QuestionNotFoundException() {
        super("");
        setCode(ErrorCode.NOT_FOUND);
    }
}
