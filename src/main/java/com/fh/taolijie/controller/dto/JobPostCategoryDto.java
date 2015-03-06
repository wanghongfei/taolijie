package com.fh.taolijie.controller.dto;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class JobPostCategoryDto {
    private String name;
    private String memo;
    private Integer level;

    public JobPostCategoryDto (){}
    public JobPostCategoryDto(String name, String memo, Integer level) {
        this.name = name;
        this.memo = memo;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
