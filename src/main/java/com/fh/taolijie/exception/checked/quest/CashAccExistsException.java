package com.fh.taolijie.exception.checked.quest;

import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.sun.tools.internal.ws.processor.generator.GeneratorException;

/**
 * Created by whf on 9/20/15.
 */
public class CashAccExistsException extends GeneralCheckedException {
    public CashAccExistsException(String msg) {
        super(msg);
    }
}
