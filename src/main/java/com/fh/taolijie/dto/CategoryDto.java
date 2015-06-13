package com.fh.taolijie.dto;

/**
 * Created by wynfrith on 15-6-12.
 * 分类dto不区分兼职分类还是二手分类
 */
public class CategoryDto {
    private String name;
    private String themeColor;
    private String memo;
    private String level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
