package com.fh.taolijie.controller.dto;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class RoleDto {
    private Integer id;
    private String rolename;
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
