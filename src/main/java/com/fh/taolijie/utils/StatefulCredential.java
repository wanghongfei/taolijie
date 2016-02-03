package com.fh.taolijie.utils;

import cn.fh.security.credential.DefaultCredential;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whf on 2/3/16.
 */
public class StatefulCredential extends DefaultCredential {
    /**
     * 自定义参数表
     */
    protected Map<String, String> customParameter;

    /**
     * 用来记录customParameter有没有发生变化
     */
    protected boolean dirty = false;

    public StatefulCredential(Integer id, String username) {
        super(id, username);

    }

    /**
     * 添加自定义参数
     * @param key
     * @param val
     */
    public void setParameter(String key, String val) {
        getCustomParameter().put(key, val);

        this.dirty = true;
    }

    /**
     * 获取自定义参数
     * @param key
     * @return
     */
    public String getParameter(String key) {
        return getCustomParameter().get(key);
    }

    public Map<String, String> getAllParameters() {
        return getCustomParameter();
    }

    /**
     * 判断自定义参数是否有变化
     * @return
     */
    public boolean isDirty() {
        return this.dirty;
    }

    public void resetDirty() {
        this.dirty = false;
    }

    private Map<String, String> getCustomParameter() {
        if (null == this.customParameter) {
            this.customParameter = new HashMap<>();
        }

        return this.customParameter;
    }

}
