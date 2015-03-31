package com.fh.taolijie.utils;

/**
 * Created by wanghongfei on 15-3-31.
 */
public class ObjWrapper {
    private Object obj;

    public ObjWrapper() {

    }

    public ObjWrapper(Object o) {
        this.obj = o;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
