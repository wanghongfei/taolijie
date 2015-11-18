package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * 任务重复领取
 * Created by whf on 9/22/15.
 */
public class QuestAssignedException extends GeneralCheckedException {
    public QuestAssignedException(String msg) {
        super(msg);
        setCode(ErrorCode.QUEST_ASSIGNED);
    }
}
