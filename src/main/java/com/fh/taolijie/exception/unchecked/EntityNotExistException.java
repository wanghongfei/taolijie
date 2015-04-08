package com.fh.taolijie.exception.unchecked;

/**
 * 当查找实体返回null时扔此异常
 * Created by wanghongfei on 15-4-7.
 */
public class EntityNotExistException extends BaseUncheckedException {
    public EntityNotExistException(String msg) {
        super(msg);
    }
}
