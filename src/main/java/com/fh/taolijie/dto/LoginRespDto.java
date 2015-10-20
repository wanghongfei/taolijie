package com.fh.taolijie.dto;

/**
 * Created by whf on 10/3/15.
 */
public class LoginRespDto {
    private Integer id;

    private String appToken;

    public LoginRespDto(Integer id, String appToken) {
        this.id = id;
        this.appToken = appToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}
