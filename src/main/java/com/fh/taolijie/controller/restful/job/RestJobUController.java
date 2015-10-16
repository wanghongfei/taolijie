package com.fh.taolijie.controller.restful.job;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.exception.checked.JobNotFoundException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.UserService;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.service.job.JobPostCateService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghongfei on 15-6-19.
 */
@RestController
@RequestMapping(value = "/api/u/job")
public class RestJobUController {
    @Autowired
    JobPostService jobService;

    @Autowired
    JobPostCateService cateService;

    @Autowired
    AccountService accountService;

    @Autowired
    private IntervalCheckService icService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private UserService userService;

    /**
     * 是否已赞
     * @return
     */
    @RequestMapping(value = "/like/{jobId}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText isFav(@PathVariable("jobId") Integer jobId,
                              HttpServletRequest req) {
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        boolean result = jobService.isPostFavorite(credential.getId(), jobId);

        return new ResponseText(result);
    }

    /**
     * 查询用户收藏的所有兼职
     * @param req
     * @return
     */
    @RequestMapping(value = "/favlist", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText favJobList(HttpServletRequest req,
                                   @RequestParam(defaultValue = "0") int pageNumber,
                                   @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }


        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<JobPostModel> lr = jobService.getFavoritePost(credential.getId(), pageNumber, pageSize);

        return new ResponseText(lr);
    }



    // ******* 移植接口 **************



    /**
     * 发布兼职信息
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText post(@Valid JobPostModel job,
                                     BindingResult result,
                                     HttpServletRequest req) {

        if (result.hasErrors()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        Credential credential = SessionUtils.getCredential(req);
        String username = credential.getUsername();
        MemberModel mem = accountService.findMember(username, false);


        // 检查发送时间间隔
        if (false == icService.checkInterval(mem.getId(), mem.getLastJobDate(), 1, TimeUnit.MINUTES)) {
            return new ResponseText(ErrorCode.TOO_FREQUENT);
        }

        /*创建兼职信息*/
        job.setMemberId(credential.getId());
        job.setPostTime(new Date());

        jobPostService.addJobPost(job);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 删除兼职
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText delPost(@PathVariable int id,
                                @RequestParam(required = false) String ids,
                                HttpServletRequest req) {

        int uid = SessionUtils.getCredential(req).getId();

        String[] delIds = { String.valueOf(id) };

        //id=0视为多条删除
        if( id == 0 ){
            delIds = ids.split(Constants.DELIMITER);
        }


        for(String currId:delIds){
            // 根据id查找兼职
            JobPostModel job =jobPostService.findJobPost(Integer.parseInt(currId));
            if(job == null){
                return new ResponseText(ErrorCode.NOT_FOUND);
            }

            // 判断是不是当前用户发布的
            if(job.getMember().getId()!=uid){
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

            //删除兼职
            jobPostService.deleteJobPost(Integer.parseInt(currId));
        }


        return ResponseText.getSuccessResponseText();
    }


    /**
     * 收藏
     */
    @RequestMapping(value = "/fav/{id}",method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText fav(@PathVariable int id,
                            @RequestParam(required = false) String ids,
                            HttpServletRequest req) {
        Credential credential = SessionUtils.getCredential(req);
        if (credential == null) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }


        String[] delIds = { String.valueOf(id) };
        //id=0视为多条删除
        if(id == 0){
            delIds = ids.split(Constants.DELIMITER);
        }


        for (String currId:delIds) {
            Integer jobId = Integer.valueOf(currId);

            if(jobPostService.findJobPost(jobId) == null) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

            //遍历用户的收藏列表
            //如果没有这条兼职则添加,反之删除
            boolean isFav = jobPostService.isPostFavorite(credential.getId(), jobId);

            if(isFav) { //找到,删除收藏
                jobPostService.unfavoritePost(credential.getId(),Integer.parseInt(currId));
            } else { //没有找到,则添加收藏
                jobPostService.favoritePost(credential.getId(),Integer.parseInt(currId));
            }
        }

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 赞
     * @return
     */
    @RequestMapping(value = "/like/{id}", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText likeJob(@PathVariable("id") Integer jobId,
                                HttpServletRequest req) {

        // 判断兼职是否存在
        if (!jobPostService.checkExist(jobId)) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }


        // 判断是否重复赞
        Credential cre = SessionUtils.getCredential(req);
        Integer userId = cre.getId();
        boolean liked = userService.isJobPostAlreadyLiked(userId, jobId);
        if (liked) {
            return new ResponseText(ErrorCode.ALREADY_DONE);
        }

        userService.likeJobPost(userId, jobId);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 更新兼职信息
     * @return
     */
    @RequestMapping(value = "/{jobId}",method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText change(@PathVariable("jobId") Integer jobId,
                               JobPostModel jobPostModel,
                               HttpServletRequest req,
                               HttpServletResponse resp){

        Credential credential = SessionUtils.getCredential(req);


        // 检查是不是本用户发布的信息
        jobPostModel.setId(jobId);
        JobPostModel job = jobPostService.findJobPost(jobPostModel.getId());
        if (null == job || false == job.getMemberId().equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 更新信息
        boolean operationResult = jobPostService.updateJobPost(jobPostModel.getId(), jobPostModel);
        if (false == operationResult) {
            return new ResponseText(ErrorCode.FAILED);
        }

        return ResponseText.getSuccessResponseText();
    }
}
