package com.fh.taolijie.controller.admin;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.RoleModel;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by wynfrith on 15-6-12.
 */
@Controller
@RequestMapping("manage/user")
public class AUserController {

    @Autowired
    AccountService accountService;

    /**
     * 用户列表页面
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String list(Model model){
        int page = 1;
        int pageSize = 9999;
        List<MemberModel> users = accountService.getMemberList(page-1, pageSize, null).getList();
        model.addAttribute("users",users);
        return "pc/admin/users";
    }


    /**
     * 添加或修改用户页面
     */
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String addUser(Model model,MemberModel dto){
        boolean isChange = false;
        if(dto != null){
            isChange = true;
        }
        model.addAttribute("isChange",isChange);
        model.addAttribute("user",dto);
        return "pc/admin/adduser";
    }

    /**
     * 修改用户页面
     */
    @RequestMapping(value = "edit/{id}",method = RequestMethod.GET)
    public String editUser(Model model,@PathVariable int id){
        MemberModel user = accountService.findMember(id);

        model.addAttribute("isEdit",true);
        model.addAttribute("member",user);
        return "pc/admin/adduser";
    }


    /**
     * 添加用户
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String  addUser(MemberModel dto,@RequestParam String roleName){

        if(null != accountService.findMember(dto.getUsername(), false)){
            return new JsonWrapper(false, Constants.ErrorType.USERNAME_EXISTS).getAjaxMessage();
        }
        System.out.println("添加用户的role:" + roleName);

        RoleModel role = accountService.findRoleByName(roleName);
        if(role == null){
            role = new RoleModel();
            role.setRolename(roleName);
            role.setMemo(roleName);

            accountService.addRole(role);
        }

        dto.setRoleList(Arrays.asList(role));
        dto.setPassword(CredentialUtils.sha(dto.getPassword()));
        dto.setCreatedTime(new Date());
        dto.setValid(true);
        dto.setComplaint(0);

        try {
            accountService.register(dto);
        } catch (DuplicatedUsernameException e) {
            return new JsonWrapper(false,Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /***
     * 修改账户(权限)
     */
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String updateUser(@PathVariable int id,MemberModel member,@RequestParam String roleName){

        RoleModel role = accountService.findRoleByName(roleName);
        member.setId(id);
        member.setRoleList(Arrays.asList(role));

        accountService.updateMember(member);

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 封号
     */
    @RequestMapping(value = "/ban",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String banUser(MemberModel member){
        member.setValid(false);

        accountService.updateMember(member);

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 解封账户
     */
    @RequestMapping(value = "/resume",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String resumeUser(MemberModel member){
        member.setValid(true);

        accountService.updateMember(member);

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     *删除用户
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String deleterUser(@PathVariable int id, HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(id == credential.getId()){
            return new JsonWrapper(false,Constants.ErrorType.CANT_DELETE_CURRENT_USER).getAjaxMessage();
        }

        MemberModel user = accountService.findMember(id);
        if(user.getValid()){
            user.setValid(false);
        }else{
            user.setValid(true);
        }

        accountService.updateMember(user);
        return new JsonWrapper(true,Constants.ErrorType.SUCCESS).getAjaxMessage();
    }



    /**
     * 获取一个用户的信息
     */
    @RequestMapping(value="/find/{id}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String getOneUser(@PathVariable int id){
        MemberModel member = accountService.findMember(id);
        if(member == null){
            return new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();
        }

        return JSON.toJSONString(member);
    }




}
