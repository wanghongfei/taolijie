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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public @ResponseBody String allJobList(@PathVariable int page,HttpServletResponse response){
        /*允许跨域,便于调试*/
//        response.setHeader("Access-Control-Allow-Origin", "*");

        int capcity = Constants.PAGE_CAPACITY;
        List<JobPostDto> list = jobPostService.getAllJobPostList(page - 1, capcity, new ObjWrapper());
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

        List<ResumeDto> list = resumeService.getAllResumeList(page - 1, capcity, totalPage);

        return JSON.toJSONString(list);
    }
    /**
     * 查询一条简历
     *
     */
    @RequestMapping(value = "item/resume/{id}",method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public @ResponseBody String resumeItem(@PathVariable int id,HttpSession session){
        ResumeDto resume = resumeService.findResume(id);
        if(resume == null){
            return new JsonWrapper(false,"没有找到id").getAjaxMessage();
        }
        return JSON.toJSONString(resume);
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
     * 查询所有二手
     */
    @RequestMapping(value = {"list/sh/{page}"},method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String shList(@PathVariable int page){
        int capcity = Constants.PAGE_CAPACITY;
        List<SecondHandPostDto> list = shPostService.getAllPostList(page - 1, capcity, new ObjWrapper());
        return JSON.toJSONString(list);
    }


    /**
     * 获取兼职详情页 Ajax GET
     *
     * @param jobId   页码数
     * @param session 用户的信息
     * @param model 绑定的模型
     * @return
     */
    @RequestMapping(value = "post/job/{jobId}", method = RequestMethod.GET)
    public
    @ResponseBody
    String jobDetail(@PathVariable int jobId, HttpSession session,Model model) {
        JobPostDto job = jobPostService.findJobPost(jobId);
        if(job==null){
            return "redirect:/404";
        }
        model.addAttribute("job",job);
        return "";
    }

    /**
     * 获取二手详情页 Ajax GET
     * @param id  页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "post/sh/{id}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String shDetail(@PathVariable int id,HttpSession session){
        return "";
    }


}
