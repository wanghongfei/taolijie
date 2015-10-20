package com.fh.taolijie.controller.restful.recommend;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.domain.RecommendedPostModel;
import com.fh.taolijie.domain.resume.ResumeModel;
import com.fh.taolijie.domain.sh.SHPostModel;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.service.RecommendService;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.sh.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whf on 8/22/15.
 */
@RestController
@RequestMapping("/api/u/recommend")
public class RestCommendUController {
    @Autowired
    RecommendService recoService;

    @Autowired
    ShPostService shService;

    @Autowired
    JobPostService jobService;

    @Autowired
    ResumeService resumeService;


    /**
     * 创建推荐信息
     * <p>{@code POST /}
     *
     * @param model
     */
    @RequestMapping(method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText add(RecommendedPostModel model,
                            HttpServletRequest req) {
        // 三个id不能同时为空
        if (null == model.getShId() && null == model.getJobId() && null == model.getResumeId()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        // 验证申请的帖子是不是自己发的
        Integer memId = credential.getId();
        if (null != model.getShId()) {
            SHPostModel sh = shService.findPost(model.getShId());
            if (null == sh) {
                return new ResponseText(ErrorCode.NOT_FOUND);
            }

            if (false == sh.getMemberId().equals(memId)) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

            model.setPostTitle(sh.getTitle());
        } else if (null != model.getJobId()) {
            JobPostModel job = jobService.findJobPost(model.getJobId());

            if (null == job) {
                return new ResponseText(ErrorCode.NOT_FOUND);
            }

            if (false == job.getMemberId().equals(memId)) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

            model.setPostTitle(job.getTitle());
        } else if (null != model.getResumeId()) {
            ResumeModel resume = resumeService.findResume(model.getResumeId());

            if (null == resume) {
                return new ResponseText(ErrorCode.NOT_FOUND);
            }

            if (false == resume.getMemberId().equals(memId)) {
                return new ResponseText(ErrorCode.PERMISSION_ERROR);
            }

            //model.setPostTitle(resume.getTitle());
        } else {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }


        model.setMemberId(memId);
        model.setValidation(false);

        // 检查申请是否已经存在
        boolean exist = recoService.checkRecommendExist(model);
        if (exist) {
            return new ResponseText(ErrorCode.EXISTS);
        }

        Integer id = recoService.add(model);

        return new ResponseText(id);
    }
}
