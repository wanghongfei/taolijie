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
import com.fh.taolijie.utils.ControllerHelper;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.ResponseUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * 简历控制器
 * 存放用户操作简历的路由
 *
 * 权限：学生、管理员可以创建简历
 *
 * Created by wynfrith on 15-4-1.
 */

@Controller
@RequestMapping("/user/resume")
public class ResumeController{

    @Autowired
    ResumeService resumeService;
    @Autowired
    AccountService accountService;
    @Autowired
    JobPostCateService jobPostCateService;

    /**
     * 创建简历 get
     * 如果简历不存在,创建简历
     * 简历以存在,则跳转到简历预览界面,自己的简历简历界面可以修改和删除
     * @param session
     * @return
     */
    @RequestMapping(value = "/create" ,method = RequestMethod.GET)
    public String create(HttpSession session,Model model){
        Credential credential = CredentialUtils.getCredential(session);
        List<ResumeDto> resumeDtoList = resumeService.getResumeList(credential.getId(), 0, 0, new ObjWrapper());
        if(resumeDtoList.size()>0){
            return "redirect:/user/resume/view";
        }

        List<JobPostCategoryDto> jobCateList = jobPostCateService.getCategoryList(0,9999,new ObjWrapper());
        model.addAttribute("cates",jobCateList);
        return "pc/user/myresume";
    }

    /**
     * 创建简历 ajax
     * @param resume 简历提交的表单
     * @param result 表单的错误信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    //region 创建简历
    public @ResponseBody String create(@Valid ResumeDto resume,
               BindingResult result,
               HttpSession session){
        GeneralMemberDto mem = null;

        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }
        String username = CredentialUtils.getCredential(session).getUsername();
        mem = accountService.findMember(username, new GeneralMemberDto[0], false);

        /*创建信息*/
        resume.setMemberId(mem.getId());
        resume.setCreatedTime(new Date());

        resumeService.addResume(resume);


        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion


    /**
     * 查看我的简历 GET
     * @param session 用户的角色
     * @return
     */

    @RequestMapping(value = "/view",method = RequestMethod.GET)
    public String view(HttpSession session,Model model)  {
        Credential credential = CredentialUtils.getCredential(session);
        List<ResumeDto> list = resumeService.getResumeList(credential.getId(), 0, 0, new ObjWrapper());

        /*因为简历只有一张,所以直接用遍历得到*/

        /*如果没有简历,跳转到创建简历页面*/
        if(list.size()==0)
        {
            return "redirect:/user/resume/create";
        }
        ResumeDto resume = list.get(0);
        model.addAttribute("resume",resume);
        return  "pc/resumedetail";

    }


    /**
     * 获取简历投递的兼职列表 Ajax GET
     * 我都投给了谁
     *
     * @param page  页码数
     * @param session 用户的信息
     * @return */

    @Deprecated
     @RequestMapping(value = "list/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String list(@PathVariable int page,HttpSession session){
        return "";

    }



    /**
     * 删除简历
     * 先判断有木有简历
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "del",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String del(HttpSession session,HttpServletResponse response) throws IOException {
        Credential credential = CredentialUtils.getCredential(session);
        GeneralMemberDto mem = accountService.findMember(credential.getUsername(), new GeneralMemberDto[0], false);

        ObjWrapper maxPage = new ObjWrapper();
        List<ResumeDto> list = resumeService.getResumeList(mem.getId(), 0, 0, maxPage);
        /*因为简历只有一张,所以直接用遍历得到*/
        ResumeDto resume = null;
        for(ResumeDto r : list){
            resume = r;
        }
        /*如果没有简历,跳转到创建简历页面*/
        if(resume == null)
        {
            response.sendRedirect("/user/resume/create");
            return null;
        }
        if(!resumeService.deleteResume(resume.getId())){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }
        return  new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();

    }


    /**
     * 修改简历
     * 与创建简历同一个页面，只不过修改简历会填充好之前的字段
     * @param session 用户的信息
     * @param model
     * @return
     */
    @RequestMapping(value = "change",method = RequestMethod.GET)
    public String changeJob(HttpSession session,Model model) {
        /**
         * 先得到用户的简历
         */
        Credential credential = CredentialUtils.getCredential(session);
        GeneralMemberDto mem = accountService.findMember(credential.getUsername(), new GeneralMemberDto[0], false);

        ObjWrapper maxPage = new ObjWrapper();
        List<ResumeDto> list = resumeService.getResumeList(mem.getId(), 0, 0, maxPage);
        /*因为简历只有一张,所以直接用遍历得到*/
        ResumeDto resume = null;
        for(ResumeDto r : list){
            resume = r;
        }
        if(resume == null|| !ControllerHelper.isCurrentUser(credential, resume)){
            return "redirect:/user/resume/create";
        }

        model.addAttribute("resume",resume);
        return "mobile/resumedetail";
    }

    /**
     * 修改简历 ajax
     * 之后考虑时候可以把创建简历和修改合并
     * @param resume
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "change",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String change(@Valid ResumeDto resume,BindingResult result,HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        ResumeDto oldResume= resumeService.findResume(resume.getId());
        resume.setMemberId(oldResume.getMemberId());

        /*因为简历只有一张,所以直接用遍历得到*/


        if(resume == null|| !ControllerHelper.isCurrentUser(credential,resume)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        if(result.hasErrors()){
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }

        if(!resumeService.updateResume(resume.getId(), resume)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 刷新简历数据 ajax
     * 更新一下posttime
     * @param session  用户的信息
     */
    @RequestMapping(value = "refresh",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String refresh(HttpSession session){
        /**
         * 如果该job不是用户发送的,则错误json
         */
        Credential credential = CredentialUtils.getCredential(session);
        GeneralMemberDto mem = accountService.findMember(credential.getUsername(), new GeneralMemberDto[0], false);

        ObjWrapper maxPage = new ObjWrapper();
        List<ResumeDto> list = resumeService.getResumeList(mem.getId(), 0, 0, maxPage);
        /*因为简历只有一张,所以直接用遍历得到*/
        ResumeDto resume = null;
        for(ResumeDto r : list){
            resume = r;
        }

        if(resume == null || !ControllerHelper.isCurrentUser(credential,resume)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        resume.setCreatedTime(new Date());
        if(!resumeService.updateResume(resume.getId(), resume)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
}

