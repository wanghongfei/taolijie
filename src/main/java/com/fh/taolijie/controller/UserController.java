package com.fh.taolijie.controller;

import com.fh.taolijie.controller.dto.*;
import com.fh.taolijie.utils.ResponseUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Created by wynfrith on 15-3-5.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /*日志*/
    private static  final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 注册,返回一个ajax
     * @return
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String register(){
        return "";
    }

    /**
     * 登陆 返回ajax
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public @ResponseBody String login(GeneralMemberDto mem,
                                      BindingResult result,
                                      HttpSession session,
                                      HttpServletResponse res){


        /*验证用户信息*/

        /*如果自动登陆为true ,返回cookie*/
        return "json";

    }



    /**
     * 消息中心
     * @param mem
     * @param notification
     * @param session
     * @return
     */
    @RequestMapping(value = "user/messages", method = RequestMethod.GET)
    public String messages(GeneralMemberDto mem,
                           NotificationDto notification,
                           HttpSession session){
        return "pc/user/messages";
    }


    /**
     * 我的发布(简历,兼职,二手)
     * @param job
     * @param session
     * @param req
     * @return
     */
    @RequestMapping(value = "user/posts", method = RequestMethod.GET)
    public String posts(JobPostDto job,
                        ResumeDto resume,
                        SecondHandPostCategoryDto secondHand,
                        HttpSession session,
                        HttpServletRequest req){
        return ResponseUtils.determinePage(req,"user/posts");
    }

    /**
     * 发布兼职
     * @param job
     * @param session
     * @param req
     * @return
     */
    @RequestMapping(value = "user/job" ,method = RequestMethod.GET)
    public String job(JobPostDto job,
                        HttpSession session,
                        HttpServletRequest req){
        return ResponseUtils.determinePage(req,"user/job");
    }

    /**
     * 发布简历
     * @param resume
     * @param session
     * @param req
     * @return
     */
    @RequestMapping(value = "user/resume" ,method = RequestMethod.GET)
    public String job(ResumeDto resume,
                      HttpSession session,
                      HttpServletRequest req){
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
        return ResponseUtils.determinePage(req,"user/secondhand");
    }

    /**
     * 个人资料
     * @param mem
     * @param session
     * @param req
     * @return
     */
    @RequestMapping(value = "user/profile", method = RequestMethod.GET)
    public String profile(MemberRoleDto mem,
                          HttpSession session,
                          HttpServletRequest req){
        return ResponseUtils.determinePage(req,"user/profile");
    }

    /**
     * 修改个人资料
     * @param mem
     * @param session
     * @return
     */
    @RequestMapping(value = "user/profile", method = RequestMethod.POST)
    public @ResponseBody String profile(MemberRoleDto mem,
                          HttpSession session){
        return new JsonWrapper(false,"ok").getAjaxMessage();
    }

}
