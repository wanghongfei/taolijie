package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 9/22/15.
 */
public class QuestZeroException extends GeneralCheckedException {
    public QuestZeroException(String msg) {
        super(msg);
        setCode(ErrorCode.QUEST_ZERO);
    }
}
