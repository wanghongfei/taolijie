package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.*;
import com.fh.taolijie.service.*;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import net.sf.ehcache.util.FindBugsSuppressWarnings;
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
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    AccountService accountService;
    @Autowired
    SHPostService shPostService;
    @Autowired
    SHPostCategoryService shPostCategoryService;




    /**
     * 查询所有兼职分类
     * @return
     */
    @RequestMapping(value = "/cate/job",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String jobCategory(){
        List<JobPostCategoryDto> list = jobPostCateService.getCategoryList(0, Integer.MAX_VALUE, new ObjWrapper());
        return JSON.toJSONString(list);
    }

    /**
     * 查询所有兼职
     */
    @RequestMapping(value = "/list/job/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String allJobList(@PathVariable int page){
        int capcity = Constants.PAGE_CAPACITY;
        List<JobPostDto> list = jobPostService.getAllJobPostList(page-1,capcity, new ObjWrapper());
        return JSON.toJSONString(list);
    }

    /**
     * 查询所有简历
     * 筛选条件  分类 时间顺序
     */
    @RequestMapping(value = "list/resume/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String resumelist(@PathVariable int page){
        int capcity = Constants.PAGE_CAPACITY;
        ObjWrapper totalPage = new ObjWrapper();

        List<ResumeDto> list = resumeService.getAllResumeList(page-1,capcity,totalPage);

        return JSON.toJSONString(list);
    }

    /**
     * 查询所有二手分类
     * @return
     */
    @RequestMapping(value = "cate/sh",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String shCate(){
        List<SecondHandPostCategoryDto> list = shPostCategoryService.getCategoryList(0, Integer.MAX_VALUE, new ObjWrapper());
        return JSON.toJSONString(list);
    }

    /**
     * 查询所有兼职
     */
    @RequestMapping(value = {"list/sh/{page}"},method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String shList(@PathVariable int page){
        int capcity = Constants.PAGE_CAPACITY;
        List<SecondHandPostDto> list = shPostService.getAllPostList(page - 1, capcity, new ObjWrapper());
        return JSON.toJSONString(list);
    }


}
