package com.fh.taolijie.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by whf on 10/3/15.
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LoginRespDto {
    private Integer id;

    private String appToken;

    private String sid;

    public LoginRespDto() {}

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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}
