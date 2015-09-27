package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * Created by whf on 9/27/15.
 */
public class QuestNotStartException extends GeneralCheckedException {
    public QuestNotStartException(String msg) {
        super(msg);
    }
}
