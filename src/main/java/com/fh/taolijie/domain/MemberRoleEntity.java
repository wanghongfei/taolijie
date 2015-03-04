package com.fh.taolijie.domain;

import javax.persistence.*;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "member_role", schema = "", catalog = "taolijie")
public class MemberRoleEntity {
    private Integer id;
    private MemberEntity member;
    private RoleEntity role;

    @Id
    @GeneratedValue
    @Column(name = "id")
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

        MemberRoleEntity that = (MemberRoleEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    @ManyToOne
    @JoinColumn(name = "role_rid", referencedColumnName = "rid")
    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}
