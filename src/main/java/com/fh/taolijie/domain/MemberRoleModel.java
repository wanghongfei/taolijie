package com.fh.taolijie.domain;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class MemberRoleModel {
    private Integer id;
    private MemberModel member;
    private RoleModel role;

    public MemberRoleModel() {}
    public MemberRoleModel(MemberModel member, RoleModel role) {
        this.member = member;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberRoleModel that = (MemberRoleModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }
}
