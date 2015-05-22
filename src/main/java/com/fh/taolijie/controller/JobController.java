package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.ReviewDto;
import com.fh.taolijie.service.*;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ControllerHelper;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.ResponseUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import javafx.geometry.Pos;
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
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserService userService;

    /**
     * 评论
     * @param content
     * @param session
     * @return
     */
    @RequestMapping(value = "/{id}/review/post",method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    //region 评论 String review
    public @ResponseBody String review(@PathVariable int id,@RequestParam String content,HttpSession session){
        //获取评论内容,已经用户的的信息
        if(content.trim().equals("")){
            return new JsonWrapper(false,Constants.ErrorType.NOT_EMPTY).getAjaxMessage();
        }
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null)
            return new JsonWrapper(false,Constants.ErrorType.NOT_LOGGED_IN).getAjaxMessage();
        int memId = credential.getId();

        //先查一下有没有这个帖子
       if(jobPostService.findJobPost(id) == null){
           return new JsonWrapper(false,Constants.ErrorType.NOT_FOUND).getAjaxMessage();
       }

        //为该id的帖子创建一条评论
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setJobPostId(id);
        reviewDto.setContent(content);
        reviewDto.setMemberId(memId);
        reviewDto.setTime(new Date());
        if(!reviewService.addReview(reviewDto))
            return  new JsonWrapper(false,Constants.ErrorType.ERROR).getAjaxMessage();

        List<ReviewDto> list= reviewService.getReviewList(id,0,9999,new ObjWrapper());
        for(int i = list.size()-1; i> 0; i--){
            ReviewDto r = list.get(i);
            if(r.getContent().equals(content)){
                reviewDto = r;
                break;
            }
        }

        //返回帖子id
        return new JsonWrapper(true,"reviewId",reviewDto.getId().toString()).getAjaxMessage();
    }
    //endregion

    /**
     * 删除一条兼职评论
     * @param id
     * @param session
     * @return
     */

    @RequestMapping(value = "/{id}/review/delete/{reviewId}",method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    //region 删除一条兼职评论 @ResponseBody String reviewDelete
    public @ResponseBody String reviewDelete(@PathVariable int id,
                                             @PathVariable int reviewId,
                                             HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        //先查看是否登陆,发偶泽返回错误信息
        if(credential == null)
            return new JsonWrapper(false,Constants.ErrorType.NOT_LOGGED_IN).getAjaxMessage();

        //验证评论是否自己发布
        List<ReviewDto> list= reviewService.getReviewList(id,0,9999,new ObjWrapper());
        ReviewDto reviewDto = null;
        for(ReviewDto r : list){
            if(r.getId() == reviewId){
                reviewDto = r;
                break;
            }
        }
        if(reviewDto == null)
            return new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();
        if(reviewDto.getMemberId() != credential.getId())
            return new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();

        //删除评论
        if(!reviewService.deleteReview(reviewId))
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion

    /**
     * 喜欢一条兼职
     */
    @RequestMapping(value = "/{id}/like",method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    //region 喜欢一条兼职 ResponseBody String like
    public @ResponseBody String like(@PathVariable int id,HttpSession session){
        // 1.判断是否登陆,如果未登录弹出,请先登陆
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null){
            return new JsonWrapper(false, Constants.ErrorType.NOT_LOGGED_IN).getAjaxMessage();
        }
        // 2.查询一下id是否存在
        JobPostDto job = jobPostService.findJobPost(id);
        if(job == null)
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        // 3.判断是否已经喜欢过了
        // TODO : 需要在数据库层面记录喜欢的用户
        if(userService.isJobPostAlreadyLiked(credential.getId(),id))
            return  new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();

        // 4.set likes + 1
        if(!userService.likeJobPost(credential.getId(),id))
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();

        return new JsonWrapper(true,Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion


    /**
     * 不喜欢一条兼职
     */
    @RequestMapping(value = "/{id}/dislike",method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    //region 不喜欢一条兼职 ResponseBody String dislike
    public @ResponseBody String dislike(@PathVariable int id,HttpSession session){
        // 1.判断是否登陆,如果未登录弹出,请先登陆
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null){
            return new JsonWrapper(false, Constants.ErrorType.NOT_LOGGED_IN).getAjaxMessage();
        }
        // 2.查询一下id是否存在
        JobPostDto job = jobPostService.findJobPost(id);
        if(job == null)
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        // 3.判断是否已经喜欢过了

        // TODO : 需要在数据库层面记录喜欢的用户

        // 4.set likes + 1
        job.setDislikes(job.getDislikes()+1);
        if(!jobPostService.updateJobPost(id,job))
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();

        return new JsonWrapper(true,Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion


    /**
     * 发布兼职 get
     * @param
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    //region 发布兼职 String post
    public String post(HttpSession session,Model model) {
        Credential credential = CredentialUtils.getCredential(session);
        if (credential == null) {
            return "redirect:/login";
        }
        List<JobPostCategoryDto> cateList= jobPostCateService.getCategoryList(0,Integer.MAX_VALUE,new ObjWrapper());
        model.addAttribute("cates",cateList);
        return "pc/user/jobpost";
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
        job.setLikes(0);
        job.setDislikes(0);

        if(job.getCategoryId()!=null){
            jobPostService.addJobPost(job);
        }else {
            return new JsonWrapper(false,Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion


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
        Credential credential = CredentialUtils.getCredential(session);

        GeneralMemberDto mem = accountService.findMember(credential.getUsername(), new GeneralMemberDto[0], false);

        List<JobPostDto> list = jobPostService.getJobPostListByMember(mem.getId(),page-1, capcity, new ObjWrapper());
        return JSON.toJSONString(list);
    }


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
        GeneralMemberDto mem = accountService.findMember(credential.getId());
        String[] favIds = {};
        if(mem.getFavoriteJobIds() != null)
            favIds = mem.getFavoriteJobIds() .split(";");
        String favid = "";
        for(String fId : favIds){
            if(fId.equals(id+"")){
               favid = fId;
                break;
            }
        }

        String status;
        if(favid.equals("")){ //没有找到,则添加收藏
            jobPostService.favoritePost(credential.getId(),id);
            status = "0";
        }else{ //否则删除收藏
            jobPostService.unfavoritePost(credential.getId(),id);
            status = "1";
        }

        return new JsonWrapper(true, "status",status).getAjaxMessage();
    }
    //endregion




    /**
     * 我收藏的兼职 get
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "myfav", method = RequestMethod.GET)
    public String myfav(HttpSession session) {
        return "";
    }

    /**
     * 我收藏的兼职 ajax
     *
     * @param page
     * @param session
     * @return
     */
    @RequestMapping(value = "myfav/{page}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    String myfav(@PathVariable int page, HttpSession session) {
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null)
            return new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();

        int capcity = Constants.PAGE_CAPACITY;
        int start = capcity * (page - 1);


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
        int capcity = Constants.PAGE_CAPACITY;
        Credential credential = CredentialUtils.getCredential(session);

        GeneralMemberDto mem = accountService.findMember(credential.getUsername(), new GeneralMemberDto[0], false);
        List<JobPostDto> list = jobPostService.getJobPostListByMember(mem.getId(),page-1, capcity, new ObjWrapper());
        return JSON.toJSONString(list);
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
        if(!ControllerHelper.isCurrentUser(credential,job)){
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
    public String change(@PathVariable int id, HttpSession session,Model model) {
        /**
         * 如果该job不是用户发送的,则返回404
         */
        Credential credential = CredentialUtils.getCredential(session);
        JobPostDto job = jobPostService.findJobPost(id);
        if(job == null|| !ControllerHelper.isCurrentUser(credential,job)){
            return "redirect:/404";
        }

        model.addAttribute("job",job);
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


        if(job == null|| !ControllerHelper.isCurrentUser(credential,job)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        if(result.hasErrors()){
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
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
