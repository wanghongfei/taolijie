package com.fh.taolijie.domain;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "role")
public class RoleEntity {
    private Integer rid;
    private String rolename;
    private String memo;
    private Collection<MemberRoleEntity> memberRoleCollection;

    public RoleEntity() {

    }

    public RoleEntity(String roleName, String memo) {
        this.memo = memo;
        this.rolename = roleName;
    }

    @Id
    @GeneratedValue
    @Column(name = "rid")
    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    @Basic
    @Column(name = "rolename")
    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Column(name = "memo", columnDefinition = "TEXT")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (false == o instanceof RoleEntity) {
            return false;
        }

        RoleEntity that = (RoleEntity) o;

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

    @OneToMany(mappedBy = "role")
    public Collection<MemberRoleEntity> getMemberRoleCollection() {
        return memberRoleCollection;
    }

    public void setMemberRoleCollection(Collection<MemberRoleEntity> memberRoleCollection) {
        this.memberRoleCollection = memberRoleCollection;
    }
}
