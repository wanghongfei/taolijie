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
import com.fh.taolijie.service.impl.Mail;
import com.fh.taolijie.utils.*;
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
@RequestMapping(value = "/user")
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
    @Autowired
    NotificationService notificationService;
    @Autowired
    Mail mail;

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
    @RequestMapping(value = {"","/"},method = RequestMethod.GET)
    public String user(HttpSession session,Model model,HttpServletRequest req){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/login";
        }
        GeneralMemberDto memberDto = accountService.findMember(credential.getUsername(),new GeneralMemberDto[0],false);
        model.addAttribute("user", memberDto);
        long notifaicationNum = notificationService.getNotificationAmount(credential.getId(),false);
        model.addAttribute("notificationNum",notifaicationNum);
        model.addAttribute("role",credential.getRoleList().get(0));

        return "pc/user/profile";

    }




    /**
     * 消息列表
     * 分页是传给前台总页数
     * @return
     */

    @RequestMapping(value = "/msglist", method = RequestMethod.GET)
    public String msglist(){
        return "";
    }


    /**
     * 消息详情页
     * @param msgId 消息的id
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "msg/{msgId}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public String msg(@PathVariable int msgId,HttpSession session,Model model){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }
       NotificationDto notificationDto = notificationService.findNotification(msgId);
        model.addAttribute("notification",notificationDto);
        return "";
    }

    /**
     * 获取消息列表 ajax get
     * @param page 页码数
     * @param session 获取用户名
     * @param model 绑定一个消息的list
     * @return
     */
    @RequestMapping(value = "/msglist", method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String msgList(@PathVariable int page,HttpSession session,Model model){
        Credential credential = CredentialUtils.getCredential(session);
        int capcity = Constants.PAGE_CAPACITY;
        String roleName = null;
        for(String r : credential.getRoleList()){
            roleName =r;
        }
        List<NotificationDto> list =notificationService.getNotificationList(credential.getId(),roleName,page-1,capcity,new ObjWrapper());
        return  JSON.toJSONString(list);
    }

    /**
     * 消息提醒,留言或回复会触发该方法
     * @param session
     * @param acceptor 接受人
     * @param content 回复的内容
     * @return
     */
    @RequestMapping(value = "/messages", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String messages(HttpSession session,@RequestParam String acceptor,@RequestParam String content){

        /*获取接收人的dto实体*/
        GeneralMemberDto acceptorDto = accountService.findMember(acceptor,new GeneralMemberDto[0],false);

        /*发送消息*/
        NotificationDto notification = new NotificationDto();
        notification.setMemberId(acceptorDto.getId());
        notification.setAccessRange(Constants.NotificationRange.PRIVATE.toString());
        notification.setContent(content);
        notification.setIsRead(0); //0未读 1已读
        notificationService.addNotification(notification);

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }




    /**
     * 个人资料
     * @param req
     * @return
     */
    @RequestMapping(value = "/setting/profile", method = RequestMethod.GET)
    public String profile(HttpServletRequest req){
        return ResponseUtils.determinePage(req, "user/profile");
    }

    /**
     * 修改个人资料
     * @param session
     * @param gmem
     * @return
     */
    @RequestMapping(value = "/setting/profile", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
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
     * 安全信息(密码)
     * @param req
     * @return
     */
    @RequestMapping(value = "/setting/security", method = RequestMethod.GET)
    public String security(HttpServletRequest req){
        return "pc/user/security";
    }

    /**
     * 修改密码
     * @param session
     * @return
     */
    @RequestMapping(value = "/setting/security",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
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
            return new JsonWrapper(false, Constants.ErrorType.PASSWORD_ERROR).getAjaxMessage();
        }else if(!dto.getNewPassword().equals(dto.getRePassword())){
            return  new JsonWrapper(false, Constants.ErrorType.REPASSWORD_ERROR).getAjaxMessage();
        }

        //加密
        mem.setPassword(CredentialUtils.sha(dto.getNewPassword()));

        System.out.println("更新后的密码"+mem.getPassword());
        System.out.println(CredentialUtils.sha(mem.getPassword()));
        if(!accountService.updateMember(mem)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 用户注销 post
     */

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 投诉 ajax post
     * 前端打算写成一个弹出框，不需要新页面
     * 投诉的是改物品,而不是人,但是用户可以记录总被投诉数
     * 投诉后会发送消息给管理员
     * @param id  被投诉的兼职或者二手物品的id 0兼职 1二手
     * @param session
     * @param content 投诉的内容
     */
    @RequestMapping(value = "complaint/{id}",method =RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String complaint(@RequestParam int id,@RequestParam int type,@RequestParam String content,HttpSession session){

        Credential credential = CredentialUtils.getCredential(session);

        /*被投诉人*/
        GeneralMemberDto mem ;

        /**/
        if(type == 0){
            JobPostDto job = jobPostService.findJobPost(id);
            job.setComplaint(job.getComplaint() + 1);
            jobPostService.updateJobPost(id,job);

        }else if(type == 1){
             SecondHandPostDto sh =shPostService.findPost(id);
            sh.setComplaint(sh.getComplaint()+1);
            shPostService.updatePost(id, sh);
        }else{
            return new JsonWrapper(false,Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 意见反馈页面 get
     */
    @RequestMapping(value = "feedback",method = RequestMethod.GET)
    public String feedback(){
        return "/pc/user/feedback";
    }

    /**
     * 意见反馈页面 post ajax
     * @param session
     * @return
     */
    @RequestMapping(value = "feedback",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String feedback(@RequestParam String content,@RequestParam String  email, HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        mail.sendMailAsync("反馈人:  "+credential.getUsername()+"/n"
                +"用户类型:  "+credential.getRoleList()+"/n"
                +"反馈内容:  "+content+"/n"
                +"用户邮箱:"+email+"/n"
                +"时间:  "+new Date(), Constants.MailType.FEEDBACK,"wfc5582563@126.com");

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

}
