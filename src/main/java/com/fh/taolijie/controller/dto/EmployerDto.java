package com.fh.taolijie.controller.dto;

/**
 * 与商家相关的数据封装在此类中
 * Created by wanghongfei on 15-3-4.
 */
public class EmployerDto extends GeneralMemberDto {
    protected String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
