package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.ResponseUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 兼职控制器
 * 存放用户操作兼职的路由
 *
 * 权限：学生、商家、管理员可以发布兼职（学生发布需要审核）
 *
 * Created by wynfrith on 15-4-1.
 */

@Controller
@RequestMapping("/user/job")
public class JobController {

    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public @ResponseBody String test(){
        return "hello";
    }



    /**
     * 发布兼职 get
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String post(HttpSession session) {
        Credential credential = CredentialUtils.getCredential(session);
        if (credential == null) {
            return "redirect:/user/login";
        }
        return "";
    }

    /**
     * 发布兼职信息 post ajax
     *
     * @param job
     * @param session
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String post(@Valid JobPostDto job,
                BindingResult result,
                HttpSession session) {
        GeneralMemberDto mem = null;

        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }
        System.out.println("session"+session);
        String username = CredentialUtils.getCredential(session).getUsername();
        mem = accountService.findMember(username, new GeneralMemberDto[0], false);

        /*创建兼职信息*/
        job.setMemberId(mem.getId());
        job.setPostTime(new Date());

        if(job.getCategoryId()!=null){
            jobPostService.addJobPost(job);
        }else {
            return new JsonWrapper(false,Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }



        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 我的兼职列表 GET
     *
     * @param session 用户的角色
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpSession session) {
        return "";
    }


    /**
     * 获取发布的兼职列表 Ajax GET
     *
     * @param page    页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "list/{page}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    String list(@PathVariable int page, HttpSession session) {
        int capcity = Constants.PAGE_CAPACITY;
        int start = (page - 1) * capcity;
        Credential credential = CredentialUtils.getCredential(session);

        GeneralMemberDto mem = accountService.findMember(credential.getUsername(), new GeneralMemberDto[0], false);

        List<JobPostDto> list = jobPostService.getJobPostListByMember(mem.getId(), start, capcity, new ObjWrapper());
        return JSON.toJSONString(list);
    }




    /**
     * 我收藏的兼职 get
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "fav", method = RequestMethod.GET)
    public String fav(HttpSession session) {
        return "";
    }

    /**
     * 我收藏的兼职 ajax
     *
     * @param page
     * @param session
     * @return
     */
    @RequestMapping(value = "fav/{page}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    String fav(@PathVariable int page, HttpSession session) {
        int capcity = Constants.PAGE_CAPACITY;
        int start = capcity * (page - 1);
        Credential credential = CredentialUtils.getCredential(session);


        List<JobPostDto> list = null;
        /*实现收藏*/
        /*
        *
        * 暂时没有方法
        * */
        return JSON.toJSONString(list);
    }

    /**
     * 获取已删除的兼职列表 Ajax GET
     *
     * @param page    页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "dellist/{page}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    String delList(@PathVariable int page, HttpSession session) {
        return "";
    }


    /**
     * 获取带审核的兼职列表 Ajax GET
     *
     * @param page    页码数
     * @param session 用户的信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "auditlist/{page}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String auditList(@PathVariable int page, HttpSession session) {

        return "";
    }


    /**
     * 删除一条兼职
     *
     * @param id      兼职的id
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "del/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    String del(@PathVariable int id, HttpSession session) {
        Credential credential = CredentialUtils.getCredential(session);
        JobPostDto job = jobPostService.findJobPost(id);

        /*判断兼职信息是否由当前用户发布*/
        if (job.getMemberId() != credential.getId()) {
            System.out.println(job.getMemberId());
            System.out.println(credential.getId());
            return new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        /*删除*/

        if (!jobPostService.deleteJobPost(id)) {
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 修改兼职页面
     * 与发布兼职用同一个页面，只不过修改该兼职会填充好之前的字段
     *
     * @param id 传入要修改的job的id
     * @param session    用户的信息
     * @return
     */
    @RequestMapping(value = "change/{id}", method = RequestMethod.GET)
    public String change(@PathVariable int id, HttpSession session) {
        /**
         * 如果该job不是用户发送的,则返回404
         */
        Credential credential = CredentialUtils.getCredential(session);
        JobPostDto job = jobPostService.findJobPost(id);
        if(job == null){
            return "/404";
        }
        if (job.getMemberId() != credential.getId()) {
            System.out.println(job.getMemberId());
            System.out.println(credential.getId());
            return  "/404";
        }
        return "mobile/jobdetail";
    }

    /**
     * 修改一条兼职 ajax
     *
     * @param job      兼职的id
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "change", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    String change(@Valid JobPostDto job,BindingResult result, HttpSession session) {
        /**
         * 如果该job不是用户发送的,则错误json
         */
        Credential credential = CredentialUtils.getCredential(session);

        if (job.getMemberId() != credential.getId()) {
            System.out.println(job.getMemberId());
            System.out.println(credential.getId());
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        if(!jobPostService.updateJobPost(job.getId(),job)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 刷新兼职数据 ajax
     * 更新一下posttime
     *
     * @param id      兼职的id
     * @param session 用户的信息
     */
    @RequestMapping(value = "refresh/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    String refresh(@PathVariable int id, HttpSession session) {
        /**
         * 如果该job不是用户发送的,则错误json
         */
        Credential credential = CredentialUtils.getCredential(session);
        JobPostDto job = jobPostService.findJobPost(id);
        if(job == null){
            return new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();
        }
        if (job.getMemberId() != credential.getId()) {
            System.out.println(job.getMemberId());
            System.out.println(credential.getId());
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        job.setPostTime(new Date());
        if(!jobPostService.updateJobPost(job.getId(),job)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


}
