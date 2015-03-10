package com.fh.taolijie.controller.dto;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class AcademyDto {
    private Integer id;

    private String shortName;
    private String fullName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
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
}
