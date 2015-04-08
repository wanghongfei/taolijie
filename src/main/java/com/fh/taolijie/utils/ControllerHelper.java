package com.fh.taolijie.utils;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.controller.dto.SecondHandPostDto;

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
}
