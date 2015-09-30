package com.fh.taolijie.domain;

import java.util.Date;
import java.util.List;

/**
 * 通讯协议
 * Created by whf on 9/30/15.
 */
public class MsgProtocol {
    /**
     * 消息的类型
     */
    private int type;

    /**
     * job的spring bean名
     */
    private String beanName;

    /**
     * crontab表达式
     */
    private String cronExp;

    private Date exeAt;

    /**
     * 参数
     */
    private List<Object> parmList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getCronExp() {
        return cronExp;
    }

    public void setCronExp(String cronExp) {
        this.cronExp = cronExp;
    }

    public Date getExeAt() {
        return exeAt;
    }

    public List<Object> getParmList() {
        return parmList;
    }

    public void setParmList(List<Object> parmList) {
        this.parmList = parmList;
    }

    public void setExeAt(Date exeAt) {
        this.exeAt = exeAt;
    }
}
