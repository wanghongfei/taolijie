package com.fh.taolijie.component;

import com.fh.taolijie.utils.Constants;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-19.
 */
public class ResponseText {
    private String message;
    private boolean isOk;

    private Object data;

    public ResponseText(boolean ok, String message, Object data) {
        this.isOk = ok;
        this.message = message;

        this.data = data;
    }

    /**
     * 该构造函数默认处理结果为成功, 带List数据
     */
    public <T extends List> ResponseText(T data) {
        this.isOk = true;
        this.message = Constants.StatusMessage.SUCCESS;

        this.data = data;
    }

    /**
     * 该构造函数默认处理结果为成功, 带单条数据
     * @param data
     */
    public ResponseText(Object data) {
        this.isOk = true;
        this.message = Constants.StatusMessage.SUCCESS;

        this.data = data;
    }

    /**
     * 默认处理结果为成功,无附带数据
     */
    public ResponseText() {
        this.isOk = true;
        this.message = Constants.StatusMessage.SUCCESS;
    }


    /**
     * 该构造函数默认处理结果为失败
     * @param message
     */
    public ResponseText(String message) {
        this.isOk = false;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isOk() {
        return isOk;
    }

    public void setIsOk(boolean isOk) {
        this.isOk = isOk;
    }

}
