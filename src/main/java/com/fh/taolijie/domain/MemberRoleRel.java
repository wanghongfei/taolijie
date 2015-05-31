package com.fh.taolijie.domain;

/**
 * Created by wanghongfei on 15-5-30.
 */
public class MemberRoleRel {
    private Integer memberId;
    private Integer roleId;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
