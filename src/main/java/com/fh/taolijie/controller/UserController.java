package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.credential.DefaultCredential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.*;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.*;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.DefaultAvatarGenerator;
import com.fh.taolijie.utils.ResponseUtils;
import com.fh.taolijie.utils.TaolijieCredential;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by wynfrith on 15-3-5.
 */
@Controller
public class UserController {

    @Autowired
    AccountService accountService;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    SHPostService shPostService;


    /*日志*/
    private static  final Logger logger = LoggerFactory.getLogger(UserController.class);

    /*针对date类型的绑定*/
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }

    /**
     * 个人中心
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String user(HttpSession session,Model model,HttpServletRequest req){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }
        GeneralMemberDto memberDto = accountService.findMember(credential.getUsername(),new GeneralMemberDto[0],false);
        model.addAttribute("user", memberDto);


        return "mobile/user/user";

    }




    /**
     * 消息列表
     * @param mem
     * @param notification
     * @param session
     * @return
     */

    @RequestMapping(value = "/user/messages", method = RequestMethod.GET)
    public String messages(GeneralMemberDto mem,
                           NotificationDto notification,
                           HttpSession session,
                           HttpServletRequest req){
        return ResponseUtils.determinePage(req,"messages");
    }




    /**
     * 我的发布(简历,兼职,二手)
     * @param job
     * @param req
     * @return
     */
    @RequestMapping(value = "user/posts", method = RequestMethod.GET)
    public String posts(JobPostDto job,
                        ResumeDto resume,
                        SecondHandPostCategoryDto secondHand,
                        HttpSession session,
                        HttpServletRequest req){
       String username = null;

        /*通过session获取当前的用户*/
        username = CredentialUtils.getCredential(session).getUsername();

        /*查询各种表*/

        /*绑定model*/
        return ResponseUtils.determinePage(req,"user/posts");
    }

    /**
     * 我的收藏
     */

    @RequestMapping(value = "user/fav",method = RequestMethod.GET)
    public String fav(JobPostDto job,
                      ResumeDto resume,
                      SecondHandPostCategoryDto secondHand,
                      HttpSession session,
                      HttpServletRequest req){

        /*通过session获取当前的用户*/
        String username = CredentialUtils.getCredential(session).getUsername();

         /*绑定model*/
        return ResponseUtils.determinePage(req,"user/fav");

    }


    /**
     * 发布兼职
     * @param
     * @param req
     * @return
     */
    @RequestMapping(value = "user/job" ,method = RequestMethod.GET)
    public String job(HttpSession session,
                      HttpServletRequest req){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }
        return ResponseUtils.determinePage(req,"user/jobpost");
    }


    /**
     * 发布简历
     * @param resume
     * @param req
     * @return
     */
    @RequestMapping(value = "user/resume" ,method = RequestMethod.GET)
    public String job(ResumeDto resume,
                      HttpSession session,
                      HttpServletRequest req){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }
        return ResponseUtils.determinePage(req,"user/resume");
    }

    /**
     * 发布二手
     * @param secondhand
     * @param session
     * @param req
     * @return
     */
    @RequestMapping(value = "user/secondhand" ,method = RequestMethod.GET)
    public String job(SecondHandPostDto secondhand,
                      HttpSession session,
                      HttpServletRequest req){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }
        return ResponseUtils.determinePage(req,"user/secondhand");
    }

    /**
     * 个人资料
     * @param req
     * @return
     */
    @RequestMapping(value = "user/setting/profile", method = RequestMethod.GET)
    public String profile(HttpServletRequest req){
        return ResponseUtils.determinePage(req,"user/profile");
    }


    /**
     * 安全信息(密码)
     * @param req
     * @return
     */
    @RequestMapping(value = "user/setting/security", method = RequestMethod.GET)
    public String security(HttpServletRequest req){
        return ResponseUtils.determinePage(req,"user/security");
    }



    //==============post==============

    /**
     * 用户注销
     */

    @RequestMapping(value = "user/logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index";
    }

    /**
     * 修改个人资料
     * @param session
     * @param
     * @return
     */
    @RequestMapping(value = "user/setting/profile", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String profile(GeneralMemberDto gmem, HttpSession session){
        GeneralMemberDto mem = null;

        String username = CredentialUtils.getCredential(session).getUsername();

        mem = accountService.findMember(username,new GeneralMemberDto[0],false);

        mem.setAge(gmem.getAge());
        mem.setGender(gmem.getGender());
        mem.setName(gmem.getName());
        mem.setProfilePhotoPath(gmem.getProfilePhotoPath());
        mem.setQq(gmem.getQq());
        mem.setPhone(gmem.getPhone());

        if(!accountService.updateMember(mem)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 修改密码
     * @param session
     * @return
     */
    @RequestMapping(value = "user/setting/security",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String security(@Valid ChangePasswordDto dto,
                                         BindingResult result,
                                         HttpSession session){

        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }

        GeneralMemberDto mem = null;

        String username = CredentialUtils.getCredential(session).getUsername();


        mem = accountService.findMember(username,new GeneralMemberDto[0],false);



        if(!mem.getPassword().equals(CredentialUtils.sha(dto.getOldPassword()))){
            System.out.println("用户的密码:"+mem.getPassword());
            System.out.println("输入的原密码:"+CredentialUtils.sha(dto.getOldPassword()));
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }else if(!dto.getNewPassword().equals(dto.getRePassword())){
            return  new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }

        mem.setPassword(dto.getNewPassword());

        System.out.println("更新后的密码"+mem.getPassword());
        System.out.println(CredentialUtils.sha(mem.getPassword()));
        if(!accountService.updateMember(mem)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 发布二手信息
     * @param sechand
     * @param session
     * @return
     */
    @RequestMapping(value = "user/post/sechand", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String job(@Valid SecondHandPostDto sechand,
                                    BindingResult result,
                                    HttpSession session){
        GeneralMemberDto mem = null;

        if(result.hasErrors())
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();

        String username = CredentialUtils.getCredential(session).getUsername();
        mem = accountService.findMember(username,new GeneralMemberDto[0],false);



        /*创建二手信息*/


        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 发布简历信息
     * @param resume
     * @param session
     * @return
     */
    @RequestMapping(value = "user/post/resume", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String job(ResumeDto resume,
                                    HttpSession session){
        GeneralMemberDto mem = null;
        String username = CredentialUtils.getCredential(session).getUsername();
        mem = accountService.findMember(username,new GeneralMemberDto[0],false);

        /*创建二手信息*/


        return new JsonWrapper(true,"success").getAjaxMessage();
    }


    /**
     * 消息提醒,留言或回复会触发该方法
     * @param session
     * @param acceptor 接受人
     * @param content 回复的内容
     * @return
     */
    @RequestMapping(value = "/user/messages", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String messages(HttpSession session,@RequestParam String acceptor,@RequestParam String content){

        /*获取接收人的dto实体*/
        String username = CredentialUtils.getCredential(session).getUsername();
        GeneralMemberDto acceptorDto = accountService.findMember(acceptor,new GeneralMemberDto[0],false);

        /*发送消息*/

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }




}
