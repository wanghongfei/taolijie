package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.MemberRoleRel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanghongfei on 15-5-30.
 */
@Repository
public interface MemberMapper {
    MemberModel getMemberById(Integer id);
    MemberModel getMemberByName(String username);

    List<MemberModel> getMemberList(int pageNumber, int pageSize);

    void saveMember(MemberModel mem);

    //void updateBySelective(MemberModel mem);

    void assignRole(Integer memberId, Integer roleId);

    /**
     * 取消与role的关联关系
     * @param rel
     */
    void removeRole(MemberRoleRel rel);

}
