package com.fh.taolijie.controller.dto;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class JobPostCategoryDto {
    private Integer id;
    private String name;
    private String memo;
    private Integer level;

    public JobPostCategoryDto (){}
    public JobPostCategoryDto(Integer id, String name, String memo, Integer level) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
