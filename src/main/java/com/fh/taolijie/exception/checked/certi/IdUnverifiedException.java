package com.fh.taolijie.exception.checked.certi;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.exception.checked.GeneralCheckedException;

/**
 * 个人身份未认证
 * @author wanghongfei
 */
public class IdUnverifiedException extends GeneralCheckedException {
    public IdUnverifiedException() {
        super("");
        setCode(ErrorCode.LACK_ID_CERTIFICATION);
    }
}
