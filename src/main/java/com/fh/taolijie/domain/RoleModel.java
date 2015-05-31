package com.fh.taolijie.domain;

import java.util.Collection;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class RoleModel {
    private Integer rid;
    private String rolename;
    private String memo;
    private Collection<MemberRoleModel> memberRoleCollection;

    public RoleModel() {

    }

    public RoleModel(String roleName, String memo) {
        this.memo = memo;
        this.rolename = roleName;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (false == o instanceof RoleModel) {
            return false;
        }

        RoleModel that = (RoleModel) o;

        return this.rid.equals(that.rid) && this.rolename.equals(that.rolename);

        //if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;
        //if (rid != null ? !rid.equals(that.rid) : that.rid != null) return false;
        //if (rolename != null ? !rolename.equals(that.rolename) : that.rolename != null) return false;

        //return true;
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + this.rid;
        result = 31 * result + this.rolename.hashCode();
/*        result = 31 * result + (rolename != null ? rolename.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);*/
        return result;
    }

    public Collection<MemberRoleModel> getMemberRoleCollection() {
        return memberRoleCollection;
    }

    public void setMemberRoleCollection(Collection<MemberRoleModel> memberRoleCollection) {
        this.memberRoleCollection = memberRoleCollection;
    }
}
