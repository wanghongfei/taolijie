package com.fh.taolijie.component;

import com.fh.taolijie.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-19.
 */
public class ResponseText {
    private String message;
    private int status; // HTTP状态码
    private boolean isOk;

    private List<Object> dataList;

    public ResponseText(boolean ok, String message, HttpStatus status, List dataList) {
        this.isOk = ok;
        this.message = message;
        this.status = status.value();

        this.dataList = dataList;
    }

    /**
     * 该构造函数默认处理结果为成功, 状态码200
     * @param dataList
     */
    public ResponseText(List dataList) {
        this.isOk = true;
        this.message = Constants.StatusMessage.SUCCESS;
        this.status = HttpStatus.OK.value();

        this.dataList = dataList;
    }

    /**
     * 该构造函数默认处理结果为失败
     * @param message
     * @param status
     */
    public ResponseText(String message, HttpStatus status) {
        this.isOk = false;
        this.message = message;
        this.status = status.value();
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setIsOk(boolean isOk) {
        this.isOk = isOk;
    }

}
