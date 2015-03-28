package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.MemberRoleDto;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wynfrith on 15-3-27.
 */


/**
 * api控制器
 * 提供各种接口
 */
@Controller
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    AccountService accountService;



    /**
     * 查询兼职分类
     * @return
     */
    @RequestMapping(value = "jobcate",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String jobCategory(){
        List<JobPostCategoryDto> list = jobPostCateService.getCategoryList(0, 0);
        return JSON.toJSONString(list);
    }

    /**
     * 查询所有兼职
     */
    @RequestMapping(value = {"joblist"},method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String joblist(){
        List<JobPostDto> list = jobPostService.getAllJobPostList(0,0);
        return JSON.toJSONString(list);
    }

    /**
     * 查询用户发布的兼职
     */
    @RequestMapping(value="/joblistbymember",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String joblistbymember(HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }

        GeneralMemberDto mem = accountService.findMember(credential.getUsername(), new GeneralMemberDto[0], false);

        List<JobPostDto> list = jobPostService.getJobPostListByMember(mem.getId(), 0, 0);
        return JSON.toJSONString(list);
    }

    /**
     * 查询用户收藏的兼职
     */
    @RequestMapping(value = "/joblistbyfav",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String joblistbyfav(HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }

        List<JobPostDto> list = null;

        /*实现收藏*/
        return JSON.toJSONString(list);
    }

    /**
     * 删除兼职
     */
    @RequestMapping(value = "/deleteJob/{jobId}",method = RequestMethod.POST)
    public @ResponseBody String job(@PathVariable("jobId") int jobId,
                                    HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }
        JobPostDto job = jobPostService.findJobPost(jobId);

        /*判断兼职信息是否有当前用户发布*/
        if(job.getMemberId()!=credential.getId()){
            System.out.println(job.getMemberId());
            System.out.println(credential.getId());
            return new JsonWrapper(false, Constants.ErrorType.USERNAME_ERROR).getAjaxMessage();
        }

        /*删除*/
        if(!jobPostService.deleteJobPost(jobId)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();

    }

    /**
     * 发布兼职信息
     * @param job
     * @param session
     * @return
     */
    @RequestMapping(value = "/post/job", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String job(@Valid JobPostDto  job,
                                    BindingResult result,
                                    HttpSession session){
        GeneralMemberDto mem = null;

        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }

        String username = CredentialUtils.getCredential(session).getUsername();
        mem = accountService.findMember(username,new GeneralMemberDto[0],false);

        /*创建兼职信息*/
        job.setMemberId(mem.getId());
        job.setPostTime(new Date());
        jobPostService.addJobPost(job);


        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

}
