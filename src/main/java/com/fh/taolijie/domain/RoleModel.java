package com.fh.taolijie.domain;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class RoleModel extends Pageable {
    private Integer id;
    private String name;
    private String memo;

    public RoleModel() {

    }

    public RoleModel(String roleName, String memo) {
        this.memo = memo;
        this.name = roleName;
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
}
