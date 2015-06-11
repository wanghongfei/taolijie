package com.fh.taolijie.utils.json;

import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wynfrith on 15-5-22.
 * 封装好的json对象
 */
public class JsonObj {

    private String apiVersion = Constants.API_VERSION;
    private int code; //状态码 0为ok,1为error
    private Map<String,String> msg; //错误消息
    private Object data; //数据


    public Object getData() {
        return data;
    }

    /**
     * 传入错误对象列表
     * @param code  状态码 0为ok,1为error
     * @param list  List<ObjectError> list
     */
    public JsonObj(int code,List<ObjectError> list) {
        this.code = code;
        this.msg = new HashMap<>();
        String key = null;
        String value = null;
        for(ObjectError err : list){
            if (err instanceof FieldError) {
                FieldError fErr = (FieldError)err;
                key = fErr.getField();
                value = fErr.getDefaultMessage();
            } else {
                key = err.getObjectName();
                value = err.getDefaultMessage();
            }
            this.msg.put(key,value);
        }

    }

    /**
     * 传入任意参数列表，参数必须为2的倍数
     * @param code 状态码 0为ok,1为error
     * @param args 参数
     */
    public JsonObj(int code,String... args){
        this.code =code;
        int len = args.length;
        if(len <=0 || len % 2 != 0){
            throw new RuntimeException("可变参数必须为2的倍数");
        }

        String key,value;
        this.msg = new HashMap<>();
        for(int i=0; i<len; i+=2){
            key = args[i];
            value = args[i+1];
            this.msg.put(key,value);
        }
    }

    public JsonObj(Object data) {
        this.code = 0;
        this.data = data;
    }

    public JsonObj() {
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, String> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, String> msg) {
        this.msg = msg;
    }
}



