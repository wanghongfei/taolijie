package com.fh.taolijie.controller.dto;

import java.util.List;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class SchoolDto {
    private String shortName;
    private String fullName;
    private String province;
    private String type;

    private List<Integer> academyIdList;

    public String getShortName() {
        return shortName;
    }

    public List<Integer> getAcademyIdList() {
        return academyIdList;
    }

    public void setAcademyIdList(List<Integer> academyIdList) {
        this.academyIdList = academyIdList;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
