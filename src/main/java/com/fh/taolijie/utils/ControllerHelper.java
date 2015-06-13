package com.fh.taolijie.utils;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.ResumeModel;
import com.fh.taolijie.domain.SHPostModel;

/**
 * Created by wynfrith on 15-6-11.
 */
public class ControllerHelper {
    /**
     * 验证是否是本用户操作(管理员除外)
     */
    public static boolean isCurrentUser(Credential credential,JobPostModel job){
        String roleName = credential.getRoleList().iterator().next();
        if (!job.getMemberId().equals(credential.getId())){
            if(!roleName.equals(Constants.RoleType.ADMIN.toString())){
                return false;
            }
        }
        return true;
    }
    public static boolean isCurrentUser(Credential credential,SHPostModel sh){
        String roleName = credential.getRoleList().iterator().next();
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
    public static boolean isCurrentUser(Credential credential,ResumeModel resume){
        String roleName = credential.getRoleList().iterator().next();
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
