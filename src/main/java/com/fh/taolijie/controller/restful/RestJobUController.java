package com.fh.taolijie.controller.restful;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.service.job.JobPostCateService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 是否已赞
     * @return
     */
    @RequestMapping(value = "/fav/{jobId}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
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
     * @param jobId
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

}
