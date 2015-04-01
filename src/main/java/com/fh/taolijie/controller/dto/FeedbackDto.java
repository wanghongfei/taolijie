package com.fh.taolijie.controller.dto;

/**
 * Created by wanghongfei on 15-4-1.
 */
public class FeedbackDto {
    private String categoryName;

    private String content;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
