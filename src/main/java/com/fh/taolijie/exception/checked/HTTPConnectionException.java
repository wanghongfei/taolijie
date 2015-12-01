package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 12/1/15.
 */
public class HTTPConnectionException extends GeneralCheckedException {
    public HTTPConnectionException() {
        super("");
        setCode(ErrorCode.HTTP_CONN_ERROR);
    }
}
