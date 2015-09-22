package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 9/22/15.
 */
public class QuestNotAssignedException extends GeneralCheckedException {
    public QuestNotAssignedException(String msg) {
        super(msg);
    }
}
