package com.fh.taolijie.controller.restful.job;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 12/23/15.
 */
@RestController
@RequestMapping("/api/manage/job")
public class RestJobAdminCtr {
    @Autowired
    private JobPostService postService;

    /**
     * 删除帖子
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText delPost(@PathVariable("id") Integer postId) {
        return postService.deleteJobPost(postId) ? ResponseText.getSuccessResponseText() : new ResponseText(ErrorCode.NOT_FOUND);
    }

    /**
     * 取消删除帖子
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText undelPost(@PathVariable("id") Integer postId) {
        return postService.undeleteJobPost(postId) ? ResponseText.getSuccessResponseText() : new ResponseText(ErrorCode.NOT_FOUND);
    }
}
