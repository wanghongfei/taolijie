package com.fh.taolijie.controller.user;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.OperationType;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.*;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.utils.*;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    IntervalCheckService icService;

    /**
     * 我的发布
     * @return
     */
    @RequestMapping(value = "mypost", method = RequestMethod.GET)
    public String myPost(@RequestParam(defaultValue = "0") int page,
                         @RequestParam (defaultValue = Constants.PAGE_CAPACITY+"") int pageSize,
                         HttpSession session, Model model){
        Credential credential = CredentialUtils.getCredential(session);

        page = PageUtils.getFirstResult(page, pageSize);
        ListResult<SHPostModel> shs =shPostService.getPostList(credential.getId(), false, page, pageSize);


        int pageStatus = 1;
        if(shs.getList().size() == 0){
            pageStatus = 0;
        }else if(shs.getList().size() == pageSize){
            pageStatus = 2;
        }
        model.addAttribute("pageStatus",pageStatus);
        model.addAttribute("shs",shs.getList());
        model.addAttribute("page",page);
//        model.addAttribute("totalPage",totalPage);
        model.addAttribute("isFav",false);

        return "pc/user/shlist";
    }


    /**
     * 获取已收藏的列表
     * @return
     */
    @RequestMapping(value = "myfav" ,method = RequestMethod.GET)
    public String fav(@RequestParam (defaultValue = "1") int page,
                      @RequestParam (defaultValue = Constants.PAGE_CAPACITY+"") int pageSize,
                      HttpSession session, Model model){
        Credential credential = CredentialUtils.getCredential(session);

        List<SHPostModel> shs = shPostService.getFavoritePost(credential.getId())
                .getList();


        int pageStatus = 1;
        if(shs.size() == 0){
            pageStatus = 0;
        }else if(shs.size() == pageSize){
            pageStatus = 2;
        }
        model.addAttribute("pageStatus",pageStatus);
        model.addAttribute("shs",shs);
        model.addAttribute("page",page);
        model.addAttribute("isFav",true);

        return "pc/user/shlist";
    }

    /**
     * 发布二手页面 get
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String post(HttpSession session,Model model) {
        int page = 0;
        int pageSize = Integer.MAX_VALUE;

        Credential credential = CredentialUtils.getCredential(session);
        if (credential == null) {
            return "redirect:/login";
        }

        ListResult<SHPostCategoryModel> cateList = shPostCategoryService.getCategoryList(page, pageSize);
        model.addAttribute("cates", cateList.getList());

        return "pc/user/shpost";
    }


    /**
     * 修改二手页面
     * 与发布二手用同一个页面，只不过修改该二手会填充好之前的字段
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

        // 查询分类
        ListResult<SHPostCategoryModel> cateList = shPostCategoryService.getCategoryList(0, Integer.MAX_VALUE);

        model.addAttribute("sh", sh);
        model.addAttribute("cates", cateList.getList());
        return "pc/user/shpost";
    }
    //endregion

    /**
     * 发布二手信息 post ajax
     *
     * @param session
     * @return
     */
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


        // 查检发布时间间隔
        if (false == icService.checkInterval(mem.getId(), mem.getLastShDate(), 1, TimeUnit.MINUTES)) {
            return new JsonWrapper(false, ErrorCode.TOO_FREQUENT).getAjaxMessage();
        }

        /*创建二手信息*/
        shDto.setMemberId(mem.getId());
        shDto.setPostTime(new Date());
        //图片列表  用分号隔开
        shDto.setPicturePath(picIds);
        //shDto.setExpiredTime(TimeUtil.getMaxDate());

        shPostService.addPost(shDto);
        //userService.changeCredits(mem.getId(), OperationType.POST, mem.getCredits());

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }


    /**
     * 更新二手信息
     * @return
     */
    @RequestMapping(value = "/change/{shId}",method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public @ResponseBody String change(SHPostModel shPostModel,
                                       @RequestParam("picIds") String picIds,
                                       @PathVariable("shId") Integer shId,
                                       HttpSession session,
                                       HttpServletResponse resp ){
        Credential credential = CredentialUtils.getCredential(session);

        if (null == shPostModel) {
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            return new JsonWrapper(false, ErrorCode.USER_INVALID).getAjaxMessage();
        }

        // 检查是不是本用户发布的信息
        shPostModel.setId(shId);
        shPostModel.setPicturePath(picIds);
        SHPostModel sh = shPostService.findPost(shPostModel.getId());
        if (null == sh) {
            return new JsonWrapper(false, ErrorCode.USER_INVALID).getAjaxMessage();
        }

        // 更新信息
        boolean operationResult = shPostService.updatePost(shPostModel.getId(), shPostModel);
        if (false == operationResult) {
            return new JsonWrapper(false, ErrorCode.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }


    /**
     * 商品下架
     * @return
     */
    @RequestMapping(value = "/expire/{shId}",method = RequestMethod.POST, produces = Constants.Produce.JSON)
    @ResponseBody
    public String expirePost(@PathVariable("shId") Integer shId) {
        // 检查是不否是本用户发的信息
        SHPostModel sh = shPostService.findPost(shId);
        if (null == sh) {
            return new JsonWrapper(false, ErrorCode.USER_INVALID).getAjaxMessage();
        }

        SHPostModel cmd = new SHPostModel();
        cmd.setId(shId);
        cmd.setExpired(true);

        if (false == shPostService.updatePost(shId, cmd) ) {
            return new JsonWrapper(false, ErrorCode.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }

    /**
     * 删除二手 post ajax
     *
     * @param session
     * @return
     */
    //region 删除二手 ajax String post
    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody
    String delPost(@PathVariable  int id,
                   @RequestParam(required = false) String ids,
                   HttpSession session) {

        int uid= CredentialUtils.getCredential(session).getId();

        String [] delIds = { String.valueOf(id) };
        //id=0视为多条删除
        if(id==0){
            delIds = ids.split(Constants.DELIMITER);
        }


        for(String currId:delIds){
            Integer shId = Integer.valueOf(currId);

            //查找兼这条兼职是不是用户发布的
            SHPostModel sh =shPostService.findPost(shId);
            if(sh == null){
                return new JsonWrapper(false, ErrorCode.NOT_FOUND).getAjaxMessage();
            }
            if(sh.getMember().getId()!=uid){
                return new JsonWrapper(false, ErrorCode.PERMISSION_ERROR).getAjaxMessage();
            }
            //删除兼职
            shPostService.deletePost(shId);
        }


        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }



    /**
     * 收藏一条简历
     */
    @RequestMapping(value = "/fav/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    //region 收藏一条简历 fav
    public @ResponseBody String fav (@PathVariable int id,
                                     @RequestParam(required = false) String ids,
                                     HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null)
            return  new JsonWrapper(false, ErrorCode.PERMISSION_ERROR).getAjaxMessage();

        String [] delIds = { String.valueOf(id) };
        //id=0视为多条删除
        if(id==0){
            delIds = ids.split(Constants.DELIMITER);
        }

        String status ="1";
        for(String currId:delIds) {
            Integer shId = Integer.valueOf(currId);

            //遍历用户的收藏列表
            //如果没有这条简历则添加,反之删除
            if(shPostService.findPost(Integer.parseInt(currId)) == null)
                return  new JsonWrapper(false, ErrorCode.NOT_FOUND).getAjaxMessage();

            boolean isFav = shPostService.isPostFavorite(credential.getId(), shId);
            if(isFav){ //找到,删除收藏
                shPostService.unfavoritePost(credential.getId(), shId);
                status = "1";
            }else{ //没有找到,则添加收藏
                shPostService.favoritePost(credential.getId(), shId);
                status = "0";
            }
        }



        return new JsonWrapper(true, "status",status).getAjaxMessage();
    }
    //endregion


    /**
     * 举报一条简历
     */
    @RequestMapping(value = "/complaint/{id}", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    public @ResponseBody String complaint(HttpSession session,@PathVariable int id){
        //TODO:限定举报数目
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null){
            return new JsonWrapper(false, ErrorCode.NOT_LOGGED_IN).getAjaxMessage();
        }
        try{
            shPostService.complaint(id);
        }
        catch(Exception e){
            System.out.println(e);
            return new JsonWrapper(false, ErrorCode.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }


    /**
     * 刷新简历数据 ajax
     * 更新一下posttime
     *
     * @param id      简历的id
     * @param session 用户的信息
     */
    @RequestMapping(value = "refresh/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String refresh(@PathVariable int id, HttpSession session) {
        /**
         * 如果该sh不是用户发送的,则错误json
         */
        Credential credential = CredentialUtils.getCredential(session);
        SHPostModel sh = shPostService.findPost(id);
        if(sh == null) {
            return new JsonWrapper(false, ErrorCode.NOT_FOUND).getAjaxMessage();
        }

        if(!ControllerHelper.isCurrentUser(credential,sh)){
            return  new JsonWrapper(false, ErrorCode.PERMISSION_ERROR).getAjaxMessage();
        }

        sh.setPostTime(new Date());
        if(!shPostService.updatePost(sh.getId(), sh)){
            return new JsonWrapper(false, ErrorCode.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }



    /**
     * 评论
     * @param content
     * @param session
     * @return
     */
    @RequestMapping(value = "/{id}/review/post",method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    public @ResponseBody String review(@PathVariable int id,@RequestParam String content,HttpSession session){

        //获取评论内容,已经用户的的信息
        if(content.trim().equals("")){
            return new JsonWrapper(false, ErrorCode.EMPTY_FIELD).getAjaxMessage();
        }
        Credential credential = CredentialUtils.getCredential(session);
        if(credential == null)
            return new JsonWrapper(false, ErrorCode.NOT_LOGGED_IN).getAjaxMessage();
        int memId = credential.getId();

        //先查一下有没有这个帖子
        if(shPostService.findPost(id) == null){
            return new JsonWrapper(false, ErrorCode.NOT_FOUND).getAjaxMessage();
        }

        //为该id的帖子创建一条评论
        ReviewModel reviewDto = new ReviewModel();
        //reviewDto.setId(id);
        reviewDto.setShPostId(id);
        reviewDto.setContent(content);
        reviewDto.setMemberId(memId);
        reviewDto.setTime(new Date());
        reviewService.addReview(reviewDto);
        Integer newId = reviewDto.getId();



        // 发送被评论通知
        // 得到兼职的发送者
        SHPostModel job = shPostService.findPost(id);
        Integer toMemberId = job.getMemberId();
        // 创建通知实体
        PrivateNotificationModel priNoti = new PrivateNotificationModel();
        priNoti.setToMemberId(toMemberId);
        priNoti.setContent("有人评论了你的[" + job.getTitle() + "]");
        priNoti.setTime(new Date());
        // 保存到db
        notificationService.addNotification(priNoti);

        //返回帖子id
        return new JsonWrapper(true,"reviewId", newId.toString()).getAjaxMessage();
    }


    /**
     * 删除一条兼职评论
     * @param id
     * @param session
     * @return
     */

    @RequestMapping(value = "/{id}/review/delete/{reviewId}",method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    public @ResponseBody String reviewDelete(@PathVariable int id,
                                             @PathVariable int reviewId,
                                             HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        //先查看是否登陆,发偶泽返回错误信息
        if(credential == null)
            return new JsonWrapper(false, ErrorCode.NOT_LOGGED_IN).getAjaxMessage();

        //验证评论是否自己发布
        ReviewModel reviewDto = reviewService.getById(reviewId);
        if(reviewDto == null)
            return new JsonWrapper(false, ErrorCode.NOT_FOUND).getAjaxMessage();
        if(reviewDto.getMemberId() != credential.getId())
            return new JsonWrapper(false, ErrorCode.PERMISSION_ERROR).getAjaxMessage();

        //删除评论
        if(!reviewService.deleteReview(reviewId))
            return new JsonWrapper(false, ErrorCode.FAILED).getAjaxMessage();

        // 删除评论回复
        reviewService.deleteReplyByReview(reviewId);

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }

    /**
     * 点赞
     * @return
     */
    @RequestMapping(value = "/{id}/like",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String like(@PathVariable Integer id, HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        //先查看是否登陆,发偶泽返回错误信息
        if(credential == null)
            return new JsonWrapper(false, ErrorCode.NOT_LOGGED_IN).getAjaxMessage();

        // 判断是否重复喜欢
        boolean liked = userService.isSHPostAlreadyLiked(credential.getId(), id);
        if (liked) {
            return new JsonWrapper(false, ErrorCode.ALREADY_DONE).getAjaxMessage();
        }

        // like +1
        userService.likeSHPost(credential.getId(), id);
        // 加分
        //MemberModel mem = shPostService.findPost(id).getMember();
        //userService.changeCredits(mem.getId(), OperationType.LIKE, mem.getCredits());


        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }

    /**
     * 取消赞
     * @return
     */
    @RequestMapping(value = "/{id}/unlike", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    @ResponseBody
    public String unlikeSh(@PathVariable("id") Integer shId,
                            HttpSession session) {
        // 登陆判断
        Credential cre = CredentialUtils.getCredential(session);
        if (null == cre) {
            return new JsonWrapper(false, ErrorCode.NOT_LOGGED_IN).getAjaxMessage();
        }

        // 执行操作
        boolean opsResult = userService.unlikeShPost(cre.getId(), shId);
        // 返回false说明用户本来就没有点过赞
        if (false == opsResult) {
            return new JsonWrapper(false, ErrorCode.NOT_LOGGED_IN).getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();

    }

    /**
     * 检查是否已赞
     * @return
     */
    @RequestMapping(value = "/{id}/checklike", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    @ResponseBody
    public String checkLike(@PathVariable("id") Integer shId,
                            HttpSession session) {
        // 登陆判断
        Credential cre = CredentialUtils.getCredential(session);
        if (null == cre) {
            return new JsonWrapper(false, ErrorCode.NOT_LOGGED_IN).getAjaxMessage();
        }

        boolean liked = userService.isSHPostAlreadyLiked(cre.getId(), shId);

        return new JsonWrapper(true, Boolean.toString(liked)).getAjaxMessage();
    }
}
