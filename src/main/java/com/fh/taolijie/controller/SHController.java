package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.SecondHandPostDto;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ResponseUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by wynfrith on 15-4-1.
 */
@Controller
@RequestMapping("/user/sh")
public class SHController{

    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    AccountService accountService;

    /**
     * 发布二手
     * @param
     * @param req
     * @return
     */
    @RequestMapping(value = "/post" ,method = RequestMethod.GET)
    public String postSH(HttpSession session,
                      HttpServletRequest req){
      return "";
    }

    /**
     * 发布二手信息
     * @param shDto
     * @param session
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String job(@Valid SecondHandPostDto shDto,
               BindingResult result,
               HttpSession session){

        return "";
    }


    /**
     * 我的二手列表 GET
     * @param session 用户的角色
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String mySHList(HttpSession session){
        return "";
    }


    /**
     * 获取发布的二手列表 Ajax GET
     * @param page  页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "list/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String mySHList(@PathVariable int page,HttpSession session){
        return "";
    }

    /**
     * 获取详情页 Ajax GET
     * @param id  页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "post/{id}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String myJob(@PathVariable int id,HttpSession session){
        return "";
    }


    /**
     * 获取已删除的二手列表 Ajax GET
     * @param page 页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "dellist/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String delList(@PathVariable int page,HttpSession session){
        return "";
    }


    /**
     * 获取带审核的二手列表 Ajax GET
     * @param page 页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "auditlist/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String auditList(@PathVariable int page,HttpSession session){
        return "";
    }


    /**
     * 删除一条二手信息
     * @param id 兼职的id
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String del(@PathVariable int id,HttpSession session){
        return  "";
    }


    /**
     * 修改二手页面
     * @param jobPostDto 传入一个二手所有信息
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "change",method = RequestMethod.POST)
    public String change(JobPostDto jobPostDto,HttpSession session){
        return  "";
    }

    /**
     * 修改一条兼职 ajax
     * @param id 兼职的id
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "change/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String change(@PathVariable int id,HttpSession session){
        return  "";
    }

    /**
     * 刷新兼职数据 ajax
     * 更新一下posttime
     * @param id 兼职的id
     * @param session  用户的信息
     */
    @RequestMapping(value = "refresh/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String refresh(@PathVariable int id,HttpSession session){
        return  "";
    }
}