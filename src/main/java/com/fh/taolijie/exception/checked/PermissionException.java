package com.fh.taolijie.exception.checked;

import com.fh.taolijie.constant.ErrorCode;

/**
 * Created by whf on 10/30/15.
 */
public class PermissionException extends GeneralCheckedException {
    public PermissionException() {
        super("");
        setCode(ErrorCode.PERMISSION_ERROR);
    }
}
