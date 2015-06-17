package com.fh.taolijie.controller.user;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.service.ShPostCategoryService;
import com.fh.taolijie.service.ShPostService;
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
import java.util.Date;
import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */
@RequestMapping("/user/sh")
@Controller
public class UShController {

    @Autowired
    ShPostService shPostService;
    @Autowired
    ShPostCategoryService shPostCategoryService;
    @Autowired
    AccountService accountService;
    @Autowired
    ReviewService reviewService;

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
        List<SHPostModel> shs =shPostService.getPostList(credential.getId(), page - 1, capacity, objWrapper);
//        totalPage = (Integer)objWrapper.getObj();

        model.addAttribute("shs",shs);
        model.addAttribute("page",page);
//        model.addAttribute("totalPage",totalPage);
        model.addAttribute("isFav",false);

        return "pc/user/shlist";
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

        List<SHPostModel> shs =shPostService.getFavoritePost(credential.getId());

        model.addAttribute("shs",shs);
        model.addAttribute("page",page);
        model.addAttribute("isFav",true);

        return "pc/user/shlist";
    }

    /**
     * 发布简历页面 get
     * @param
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    //region 发布简历 String post
    public String post(HttpSession session,Model model) {
        int page = 1;
        int pageSize = 9999;
        Credential credential = CredentialUtils.getCredential(session);
        if (credential == null) {
            return "redirect:/login";
        }
        List<SHPostCategoryModel> cateList=shPostCategoryService.getCategoryList(page - 1, pageSize, new ObjWrapper());
        model.addAttribute("cates",cateList);
        return "pc/user/shpost";
    }
    //endregion


    /**
     * 修改简历页面
     * 与发布简历用同一个页面，只不过修改该简历会填充好之前的字段
     *
     * @param id 传入要修改的sh的id
     * @param session    用户的信息
     * @return
     */
    @RequestMapping(value = "change/{id}", method = RequestMethod.GET)
    //region 修改简历页面 change
    public String change(@PathVariable int id, HttpSession session,Model model) {
        /**
         * 如果该sh不是用户发送的,则返回404
         */
        Credential credential = CredentialUtils.getCredential(session);
        SHPostModel sh =shPostService.findPost(id);
        if(sh == null|| !ControllerHelper.isCurrentUser(credential, sh)){
            return "redirect:/404";
        }

        model.addAttribute("sh",sh);
        return "pc/user/shpost";
    }
    //endregion

    /**
     * 发布简历信息 post ajax
     *
     * @param session
     * @return
     */
    //region 发布二手 ajax String post
    @RequestMapping(value = "/post", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String postSh(@RequestParam String picIds,
               @Valid SHPostModel shDto,
               BindingResult result,
               HttpSession session){
        MemberModel mem = null;

        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }
        String username = CredentialUtils.getCredential(session).getUsername();
        mem = accountService.findMember(username, false);

        /*创建二手信息*/
        shDto.setMemberId(mem.getId());
        shDto.setPostTime(new Date());
        //图片列表  用分号隔开
        shDto.setPicturePath(picIds);

        if(shDto.getSecondHandPostCategoryId()!=null){
            shPostService.addPost(shDto);
        }else {
            return new JsonWrapper(false,Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion



    /**
     * 收藏一条简历
     */
    @RequestMapping(value = "/fav/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    //region 收藏一条简历 fav
    public @ResponseBody String fav (@PathVariable int id,HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null)
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        if(shPostService.findPost(id) == null)
            return  new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();

        //遍历用户的收藏列表
        //如果没有这条简历则添加,反之删除
        MemberModel mem = accountService.findMember(credential.getId());
        boolean isFav = shPostService.isPostFavorite(credential.getId(),id);
        String status;
        if(isFav){ //找到,删除收藏
            shPostService.unfavoritePost(credential.getId(),id);
            status = "1";
        }else{ //没有找到,则添加收藏
            shPostService.favoritePost(credential.getId(),id);
            status = "0";
        }

        return new JsonWrapper(true, "status",status).getAjaxMessage();
    }
    //endregion


    /**
     * 取消收藏一条简历 或多条
     */
    @RequestMapping(value = "/fav/del",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String fav (HttpSession session,
                                     @RequestParam(required = false) String ids){
        Credential credential = CredentialUtils.getCredential(session);

        /*删除一个或多个*/
        try{
            for(String i : ids.split(";")){
                int currId = Integer.parseInt(i);
                shPostService.unfavoritePost(credential.getId(), currId);
            }
        }catch (Exception e){
            System.out.println("mutideleteError");
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 举报一条简历
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
            shPostService.complaint(id);
        }
        catch(Exception e){
            System.out.println(e);
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }
        return new JsonWrapper(true,Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 刷新简历数据 ajax
     * 更新一下posttime
     *
     * @param id      简历的id
     * @param session 用户的信息
     */
    @RequestMapping(value = "refresh/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public
    @ResponseBody
    String refresh(@PathVariable int id, HttpSession session) {
        /**
         * 如果该sh不是用户发送的,则错误json
         */
        Credential credential = CredentialUtils.getCredential(session);
        SHPostModel sh = shPostService.findPost(id);
        if(sh == null) {
            return new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();
        }

        if(!ControllerHelper.isCurrentUser(credential,sh)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        sh.setPostTime(new Date());
        if(!shPostService.updatePost(sh.getId(), sh)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }



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
        int page = 1;
        int capacity = 9999;
        //获取评论内容,已经用户的的信息
        if(content.trim().equals("")){
            return new JsonWrapper(false,Constants.ErrorType.NOT_EMPTY).getAjaxMessage();
        }
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null)
            return new JsonWrapper(false,Constants.ErrorType.NOT_LOGGED_IN).getAjaxMessage();
        int memId = credential.getId();

        //先查一下有没有这个帖子
        if(shPostService.findPost(id) == null){
            return new JsonWrapper(false,Constants.ErrorType.NOT_FOUND).getAjaxMessage();
        }

        //为该id的帖子创建一条评论
        ReviewModel reviewDto = new ReviewModel();
        reviewDto.setId(id);
        reviewDto.setContent(content);
        reviewDto.setMemberId(memId);
        reviewDto.setTime(new Date());
        if(!reviewService.addReview(reviewDto))
            return  new JsonWrapper(false,Constants.ErrorType.ERROR).getAjaxMessage();

        List<ReviewModel> list= reviewService.getReviewList(id,page-1,capacity,new ObjWrapper());
        for(int i = list.size()-1; i> 0; i--){
            ReviewModel r = list.get(i);
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
        List<ReviewModel> list= reviewService.getReviewList(id,0,9999,new ObjWrapper());
        ReviewModel reviewDto = null;
        for(ReviewModel r : list){
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
}
