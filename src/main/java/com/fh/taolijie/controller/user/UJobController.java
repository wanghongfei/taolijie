package com.fh.taolijie.controller.user;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ControllerHelper;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
@RequestMapping("/user/job")
public class UJobController {

    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    AccountService accountService;

    /**
     * 我的发布 GET
     *
     * @param session 用户的角色
     * @return
     */
    @RequestMapping(value = "mypost", method = RequestMethod.GET)
    public String myPost(@RequestParam(defaultValue = "1") int page,
                         @RequestParam (defaultValue = Constants.PAGE_CAPACITY+"") int capacity,
                         HttpSession session, Model model){
        Credential credential = CredentialUtils.getCredential(session);
        ObjWrapper objWrapper = new ObjWrapper();
        int totalPage = 0;
        List<JobPostModel> jobs = jobPostService.getJobPostListByMember(credential.getId(),page-1,capacity,objWrapper);
//        totalPage = (Integer)objWrapper.getObj();

        model.addAttribute("jobs",jobs);
        model.addAttribute("page",page);
//        model.addAttribute("totalPage",totalPage);
        model.addAttribute("isFav",false);

        return "pc/user/joblist";
    }


    /**
     * 获取已收藏的列表
     * @param page
     * @param capacity
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "myfav" ,method = RequestMethod.GET)
    public String fav(@RequestParam (defaultValue = "1") int page,
                      @RequestParam (defaultValue = Constants.PAGE_CAPACITY+"") int capacity,
                      HttpSession session, Model model){
        Credential credential = CredentialUtils.getCredential(session);
        ObjWrapper objWrapper = new ObjWrapper();
        int totalPage = 0;

        List<JobPostModel> jobs = jobPostService.getFavoritePost(credential.getId());

        model.addAttribute("jobs",jobs);
        model.addAttribute("page",page);
        model.addAttribute("isFav",true);

        return "pc/user/joblist";
    }

    /**
     * 发布兼职页面 get
     * @param
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    //region 发布兼职 String post
    public String post(HttpSession session,Model model) {
        int page = 1;
        int pageSize = 9999;
        Credential credential = CredentialUtils.getCredential(session);
        if (credential == null) {
            return "redirect:/login";
        }
        List<JobPostCategoryModel> cateList= jobPostCateService.getCategoryList(page-1, pageSize, new ObjWrapper());
        model.addAttribute("cates",cateList);
        return "pc/user/jobpost";
    }
    //endregion


    /**
     * 修改兼职页面
     * 与发布兼职用同一个页面，只不过修改该兼职会填充好之前的字段
     *
     * @param id 传入要修改的job的id
     * @param session    用户的信息
     * @return
     */
    @RequestMapping(value = "change/{id}", method = RequestMethod.GET)
    //region 修改兼职页面 change
    public String change(@PathVariable int id, HttpSession session,Model model) {
        /**
         * 如果该job不是用户发送的,则返回404
         */
        Credential credential = CredentialUtils.getCredential(session);
        JobPostModel job = jobPostService.findJobPost(id);
        if(job == null|| !ControllerHelper.isCurrentUser(credential, job)){
            return "redirect:/404";
        }

        model.addAttribute("job",job);
        return "mobile/jobdetail";
    }
    //endregion

    /**
     * 发布兼职信息 post ajax
     *
     * @param job
     * @param session
     * @return
     */
    //region 发布兼职 ajax String post
    @RequestMapping(value = "/post", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody
    String post(@Valid JobPostModel job,
                BindingResult result,
                HttpSession session) {

        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }
        String username = CredentialUtils.getCredential(session).getUsername();
        MemberModel mem = accountService.findMember(username, false);

        /*创建兼职信息*/
        job.setMemberId(mem.getId());
        job.setPostTime(new Date());
        job.setLikes(0);
        job.setDislikes(0);
        job.setComplaint(0);
        job.setSalaryUnit("元");

        if(job.getJobPostCategoryId()!=null){
            jobPostService.addJobPost(job);
        }else {
            return new JsonWrapper(false,Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion



    /**
     * 收藏一条兼职
     */
    @RequestMapping(value = "/fav/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    //region 收藏一条兼职 fav
    public @ResponseBody String fav (@PathVariable int id,HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null)
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        if(jobPostService.findJobPost(id) == null)
            return  new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();

        //遍历用户的收藏列表
        //如果没有这条兼职则添加,反之删除
        MemberModel mem = accountService.findMember(credential.getId());
        boolean isFav = jobPostService.isPostFavorite(credential.getId(),id);
        String status;
        if(isFav){ //找到,删除收藏
            jobPostService.unfavoritePost(credential.getId(),id);
            status = "1";
        }else{ //没有找到,则添加收藏
            jobPostService.favoritePost(credential.getId(),id);
            status = "0";
        }

        return new JsonWrapper(true, "status",status).getAjaxMessage();
    }
    //endregion


    /**
     * 取消收藏一条兼职 或多条
     */
    @RequestMapping(value = "/fav/del",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String fav (HttpSession session,
                                     @RequestParam(required = false) String ids){
        Credential credential = CredentialUtils.getCredential(session);

        /*删除一个或多个*/
        try{
            for(String i : ids.split(";")){
                int currId = Integer.parseInt(i);
                jobPostService.unfavoritePost(credential.getId(), currId);
            }
        }catch (Exception e){
            System.out.println("mutideleteError");
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 举报一条兼职
     */
    @RequestMapping(value = "/complaint/{id}", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    public @ResponseBody String complaint(HttpSession session,@PathVariable int id){
        //TODO:限定举报数目
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null){
            return new JsonWrapper(false, Constants.ErrorType.NOT_LOGGED_IN).getAjaxMessage();
        }
        try{
            jobPostService.complaint(id);
        }
        catch(Exception e){
            System.out.println(e);
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }
        return new JsonWrapper(true,Constants.ErrorType.SUCCESS).getAjaxMessage();
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
        JobPostModel job = jobPostService.findJobPost(id);
        if(job == null) {
            return new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();
        }

        if(!ControllerHelper.isCurrentUser(credential,job)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        job.setPostTime(new Date());
        if(!jobPostService.updateJobPost(job.getId(),job)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }




}
