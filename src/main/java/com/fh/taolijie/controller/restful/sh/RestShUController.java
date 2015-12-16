package com.fh.taolijie.controller.restful.sh;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.sh.SHPostModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.service.sh.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by whf on 8/26/15.
 */
@RestController
@RequestMapping("/api/u/sh")
public class RestShUController {
    private static Logger log = LoggerFactory.getLogger(RestShUController.class);

    @Autowired
    ShPostService shService;

    @Autowired
    private IntervalCheckService icService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ShPostService shPostService;

    @Autowired
    private UserService userService;

    /**
     * 是否赞美
     * @return
     */
    @RequestMapping(value = "/like/{shId}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText isFav(@PathVariable("shId") Integer shId,
                              HttpServletRequest req) {
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        boolean result = shService.isPostFavorite(credential.getId(), shId);

        return new ResponseText(result);
    }

    /**
     * 查询收藏列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/favlist", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText favList(@RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                HttpServletRequest req) {
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<SHPostModel> lr = shService.getFavoritePost(credential.getId(), pageNumber, pageSize);

        return new ResponseText(lr);
    }

    /**
     * 更新图片URL
     * @return
     */
    @RequestMapping(value = "/{shId}/pic", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText updatePic(@PathVariable("shId") Integer shId,
                                  @RequestParam String picAddr,
                                  HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        // 判断帖子是不是自己发的
        SHPostModel sh = shService.findPost(shId);
        if (!sh.getMemberId().equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 更新URL
        SHPostModel example = new SHPostModel();
        example.setId(shId);
        example.setPicturePath(picAddr);
        shService.updatePost(shId, example);

        return new ResponseText();
    }


    // ******* 移植接口 **************

    /**
     * 发布二手信息
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST,produces = Constants.Produce.JSON)
    public ResponseText postSh(@RequestParam String picIds,
                               @Valid SHPostModel shDto,
                               BindingResult result,
                               HttpServletRequest req) {
        MemberModel mem = null;

        // 参数检查
        if (result.hasErrors()) {
            log.warn("[validation failed]:", result.getAllErrors());
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        String username = SessionUtils.getCredential(req).getUsername();
        mem = accountService.findMember(username, false);


        // 查检发布时间间隔
        if (false == icService.checkInterval(mem.getLastShDate(), 1, TimeUnit.MINUTES)) {
            return new ResponseText(ErrorCode.TOO_FREQUENT);
        }

        /*创建二手信息*/
        shDto.setMemberId(mem.getId());
        shDto.setPostTime(new Date());
        //图片列表  用分号隔开
        shDto.setPicturePath(picIds);

        // 如果没有填写过期时间
        // 则设过期时间为1year
        Date expiredTime = shDto.getExpiredTime();
        if (null == expiredTime) {
            expiredTime = TimeUtil.calculateDate(new Date(), Calendar.YEAR, 1);
            shDto.setExpiredTime(expiredTime);
        }

        shPostService.addPost(shDto);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 更新二手
     * @return
     */
    @RequestMapping(value = "/{shId}",method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText update(SHPostModel shPostModel,
                               @RequestParam(value = "picIds", required = false) String picIds,
                               @PathVariable("shId") Integer shId,
                               HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        // 检查是不是本用户发布的信息
        SHPostModel sh = shPostService.findPost(shId);
        if (null == sh || false == sh.getMemberId().equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }


        shPostModel.setId(shId);
        if (null != picIds) {
            shPostModel.setPicturePath(picIds);
        }

        // 更新信息
        boolean operationResult = shPostService.updatePost(shPostModel.getId(), shPostModel);
        if (false == operationResult) {
            return new ResponseText(ErrorCode.FAILED);
        }

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 删除二手
     *
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText delPost(@PathVariable int id,
                                @RequestParam(required = false) String ids,
                                HttpServletRequest req) {

        Integer uid = SessionUtils.getCredential(req).getId();

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
                return new ResponseText(ErrorCode.NOT_FOUND);
            }

            if(false == sh.getMember().getId().equals(uid)) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

            //删除兼职
            shPostService.deletePost(shId);
        }


        return ResponseText.getSuccessResponseText();
    }

    /**
     * 收藏
     */
    @RequestMapping(value = "/fav/{id}",method = RequestMethod.POST,produces = Constants.Produce.JSON)
    public ResponseText fav(@PathVariable int id,
                            @RequestParam(required = false) String ids,
                            HttpServletRequest req){

        Credential credential = SessionUtils.getCredential(req);
        if (credential == null) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        String [] delIds = { String.valueOf(id) };
        //id=0视为多条删除
        if(id == 0) {
            delIds = ids.split(Constants.DELIMITER);
        }

        for(String currId:delIds) {
            Integer shId = Integer.valueOf(currId);

            //遍历用户的收藏列表
            //如果没有这条简历则添加,反之删除
            if (shPostService.findPost(Integer.parseInt(currId)) == null) {
                return new ResponseText(ErrorCode.NOT_FOUND);
            }

            boolean isFav = shPostService.isPostFavorite(credential.getId(), shId);
            if (isFav) { //找到,删除收藏
                shPostService.unfavoritePost(credential.getId(), shId);
            } else { //没有找到,则添加收藏
                shPostService.favoritePost(credential.getId(), shId);
            }
        }



        return ResponseText.getSuccessResponseText();
    }

    /**
     * 点赞
     * @return
     */
    @RequestMapping(value = "/like/{id}",method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText like(@PathVariable Integer id,
                             HttpServletRequest req) {

        if (!shService.checkExist(id)) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        // 判断是否重复喜欢
        Credential credential = SessionUtils.getCredential(req);
        boolean liked = userService.isSHPostAlreadyLiked(credential.getId(), id);
        if (liked) {
            return new ResponseText(ErrorCode.ALREADY_DONE);
        }

        // like +1
        userService.likeSHPost(credential.getId(), id);


        return ResponseText.getSuccessResponseText();
    }
}
