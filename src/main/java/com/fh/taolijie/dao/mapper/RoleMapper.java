package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.domain.RoleModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanghongfei on 15-5-30.
 */
@Repository
public interface RoleMapper {
    void addRole(RoleModel role);
    void deleteByName(String name);

    RoleModel findRoleById(Integer id);
    RoleModel findRoleByName(String roleName);

    List<RoleModel> findAllRole(RoleModel model);
}
