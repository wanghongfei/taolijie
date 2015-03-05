package com.fh.taolijie.domain;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "role", schema = "", catalog = "taolijie")
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

    @Basic
    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleEntity that = (RoleEntity) o;

        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;
        if (rid != null ? !rid.equals(that.rid) : that.rid != null) return false;
        if (rolename != null ? !rolename.equals(that.rolename) : that.rolename != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rid != null ? rid.hashCode() : 0;
        result = 31 * result + (rolename != null ? rolename.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
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
