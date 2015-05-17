package com.fh.taolijie.utils;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.dto.SecondHandPostDto;
import com.fh.taolijie.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 控制器的重复方法
 * Created by wynfrith on 15-4-5.
 */


public class ControllerHelper {
    /**
     * 获得RoleName
     * @param credential
     * @return
     */
    @Deprecated
    public static String getRoleName(Credential credential){
        String roleName = null;
        for(String r:credential.getRoleList()){
            roleName = r;
        }
        return roleName;
    }
    /**
     * 验证是否是本用户操作(管理员除外)
     */
    public static boolean isCurrentUser(Credential credential,JobPostDto job){
        String roleName = getRoleName(credential);
        if (!job.getMemberId().equals(credential.getId())){
            if(!roleName.equals(Constants.RoleType.ADMIN.toString())){
                return false;
            }
        }
        return true;
    }
    public static boolean isCurrentUser(Credential credential,SecondHandPostDto sh){
        String roleName = getRoleName(credential);
        for(String r:credential.getRoleList()){
            roleName = r;
        }
        if (!sh.getMemberId().equals(credential.getId())){
            if(!roleName.equals(Constants.RoleType.ADMIN.toString())){
                return false;
            }
        }
        return true;
    }
    public static boolean isCurrentUser(Credential credential,ResumeDto resume){
        String roleName = getRoleName(credential);
        for(String r:credential.getRoleList()){
            roleName = r;
        }
        if (!resume.getMemberId().equals(credential.getId())){
            if(!roleName.equals(Constants.RoleType.ADMIN.toString())){
                return false;
            }
        }
        return true;
    }

    /**
     * 通过RoleName获得对应的roleId
     * 如果找不到返回-1
     */
    public static int getRoleId(String roleName,AccountService service){
        for(RoleDto role : service.getAllRole()){
            if(role.getRolename().equals(roleName)){
                return role.getRid();
            }
        }
        return -1;
    }

    /**
     * 获取三种Role的引用
     */
    @Deprecated
    public static void getRole(RoleDto studentRole,RoleDto employerRole,RoleDto adminRole,AccountService accountService){
          /*查询所有role*/
        for(RoleDto r : accountService.getAllRole()){
            if(r.getRolename().equals(Constants.RoleType.STUDENT.toString())) {
                studentRole = r;
            }else if(r.getRolename().equals(Constants.RoleType.EMPLOYER.toString())){
                employerRole = r;
            }else if(r.getRolename().equals(Constants.RoleType.ADMIN.toString())){
                adminRole = r;
            }
        }


         /*如果没有role,创建*/
        if(studentRole == null){
            RoleDto r = new RoleDto();
            r.setRolename(Constants.RoleType.STUDENT.toString());
            r.setMemo("学生");
            accountService.addRole(r);
            studentRole = r;
        }
        if(employerRole==null){
            RoleDto r = new RoleDto();
            r.setRolename(Constants.RoleType.EMPLOYER.toString());
            r.setMemo("商家");
            accountService.addRole(r);
            employerRole = r;
        }
        if(adminRole==null){
            RoleDto r = new RoleDto();
            r.setRolename(Constants.RoleType.ADMIN.toString());
            r.setMemo("管理员");
            accountService.addRole(r);
            employerRole = r;
        }
        System.out.println("getRole:"+studentRole.getRolename());
    }

}
