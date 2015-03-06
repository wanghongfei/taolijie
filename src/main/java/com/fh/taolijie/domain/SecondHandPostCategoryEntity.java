package com.fh.taolijie.domain;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by wanghongfei on 15-3-4.
 */
@Entity
@Table(name = "second_hand_post_category")
public class SecondHandPostCategoryEntity {
    private Integer id;
    private String name;
    private String memo;
    private Integer level;
    private Collection<SecondHandPostEntity> secondHandPostsById;

    public SecondHandPostCategoryEntity() {}
    public SecondHandPostCategoryEntity(Integer level, String name, String memo) {
        this.level = level;
        this.name = name;
        this.memo = memo;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecondHandPostCategoryEntity that = (SecondHandPostCategoryEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "category")
    public Collection<SecondHandPostEntity> getSecondHandPostsById() {
        return secondHandPostsById;
    }

    public void setSecondHandPostsById(Collection<SecondHandPostEntity> secondHandPostsById) {
        this.secondHandPostsById = secondHandPostsById;
    }
}
