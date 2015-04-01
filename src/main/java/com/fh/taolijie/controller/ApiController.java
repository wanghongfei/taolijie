package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by wynfrith on 15-3-27.
 */


/**
 * 提供各种增删改查接口
 */
@Controller
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    AccountService accountService;



    /**
     * 查询兼职分类
     * @return
     */
    @RequestMapping(value = "jobcate",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String jobCategory(){
        List<JobPostCategoryDto> list = jobPostCateService.getCategoryList(0, 0, new ObjWrapper());
        return JSON.toJSONString(list);
    }

    /**
     * 查询所有兼职
     */
    @RequestMapping(value = {"alljoblist"},method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String allJobList(){
        List<JobPostDto> list = jobPostService.getAllJobPostList(0, 0, new ObjWrapper());
        return JSON.toJSONString(list);
    }

    @RequestMapping(value = "joblist",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String jobList(@RequestParam(required=false) String category){
        List<JobPostDto> list = null;
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

        List<JobPostDto> list = jobPostService.getJobPostListByMember(mem.getId(), 0, 0, new ObjWrapper());
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
    @RequestMapping(value = "/deletejob/{jobId}",method = RequestMethod.POST)
    public @ResponseBody String deleteJob(@PathVariable("jobId") int jobId,
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





    /*******************************************/
    /********************简历********************/
    /*******************************************/

    /**
     * 查询简历分类
     * @return
     */
    @RequestMapping(value = {"resumecate"},method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String resumeCate(){
        return "";
    }
    /**
     * 查询所有简历
     * 筛选条件  分类 时间顺序
     */
    @RequestMapping(value = {"resumelist"},method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String resumelist(HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        ObjWrapper totalPage = new ObjWrapper();
        List<ResumeDto> list = null;

        if(credential.hasRole(Constants.RoleType.EMPLOYER.toString())) {
            list = resumeService.getAllResumeList(0,0,totalPage);
        }else {
            list = resumeService.getAllResumeList(0,0,totalPage);
        }
        return JSON.toJSONString(list);
    }

    /**
     * 查询用户发布的简历
     */
    @RequestMapping(value="/resumelistbymember",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String resumelistbymember(HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null) {
            return "redirect:/user/login";
        }

        GeneralMemberDto mem = accountService.findMember(credential.getUsername(), new GeneralMemberDto[0], false);


        ObjWrapper maxPage = new ObjWrapper();
        List<ResumeDto> list = resumeService.getResumeList(mem.getId(), 0, 0,maxPage);
        return JSON.toJSONString(list);
    }

    /**
     * 查询用户收藏的简历
     */
    @RequestMapping(value = "/resumelistbyfav",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String resumelistbyfav(HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }

        List<ResumeDto> list = null;
        /*实现收藏*/
        return JSON.toJSONString(list);
    }

    /**
     * 删除简历
     */
    @RequestMapping(value = "/deleteresume/{resumeId}",method = RequestMethod.POST)
    public @ResponseBody String deleteResume(@PathVariable("resumeId") int resumeId,
                                    HttpSession session){

        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }
        ResumeDto resume = resumeService.findResume(resumeId);

        /*判断兼职信息是否有当前用户发布*/
        if(resume.getMemberId()!=credential.getId()){
            System.out.println(resume.getMemberId());
            System.out.println(credential.getId());
            return new JsonWrapper(false, Constants.ErrorType.USERNAME_ERROR).getAjaxMessage();
        }

        /*删除*/
        if(!resumeService.deleteResume(resumeId)){
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();

    }
}
