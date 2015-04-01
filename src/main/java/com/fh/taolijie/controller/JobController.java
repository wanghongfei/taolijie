package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostDto;
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

    /**
     * 发布兼职
     * @param
     * @param req
     * @return
     */
    @RequestMapping(value = "/post" ,method = RequestMethod.GET)
    public String post(HttpSession session,
                      HttpServletRequest req){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/user/login";
        }
        return ResponseUtils.determinePage(req, "user/jobpost");
    }

    /**
     * 发布兼职信息
     * @param job
     * @param session
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String post(@Valid JobPostDto job,
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


    /**
     * 我的兼职列表 GET
     * @param session 用户的角色
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String list(HttpSession session){
        return "";
    }


    /**
     * 获取发布的兼职列表 Ajax GET
     * @param page  页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "list/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String list(@PathVariable int page,HttpSession session){
        return "";
    }

    /**
     * 获取详情页 Ajax GET
     * @param jobId  页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "post/{jobId}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String myJob(@PathVariable int jobId,HttpSession session){
        return "";
    }


    /**
     * 获取已删除的兼职列表 Ajax GET
     * @param page 页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "dellist/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String delList(@PathVariable int page,HttpSession session){
        return "";
    }


    /**
     * 获取带审核的兼职列表 Ajax GET
     * @param page 页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "auditlist/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String auditList(@PathVariable int page,HttpSession session){
        return "";
    }


    /**
     * 删除一条兼职
     * @param id 兼职的id
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String del(@PathVariable int id,HttpSession session){
        return  "";
    }


    /**
     * 修改兼职页面
     * 与发布兼职用同一个页面，只不过修改该兼职会填充好之前的字段
     * @param jobPostDto 传入一个兼职所有信息
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "change",method = RequestMethod.GET)
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
