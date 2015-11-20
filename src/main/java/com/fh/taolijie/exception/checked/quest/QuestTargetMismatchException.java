package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 11/20/15.
 */
public class QuestTargetMismatchException extends GeneralCheckedException {
    public QuestTargetMismatchException() {
        super("");
        setCode(ErrorCode.QUEST_TARGET_MISMATCH);
    }
}
