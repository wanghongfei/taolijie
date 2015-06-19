package com.fh.taolijie.controller.restful;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-19.
 */
@RestController
@RequestMapping(value = "/api/job")
public class RestJobController {
    @Autowired
    JobPostService jobService;

    /**
     * 得到所有兼职信息, 最新的在前
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/get/all", produces = Constants.Produce.JSON)
    public String getAllPost(@RequestParam(defaultValue = "0") int pageNumber,
                           @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        List<JobPostModel> jobList = jobService.getAllJobPostList(pageNumber, pageNumber, null);
        ResponseText rt = new ResponseText(jobList);

        return JSON.toJSONString(jobList);
    }

    /**
     * 得到指定用户发布的兼职信息
     * @param memberId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/get/byMember", produces = Constants.Produce.JSON)
    public String getPostByMember(@RequestParam Integer memberId,
                                  @RequestParam(defaultValue = "0") int pageNumber,
                                  @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        if (null == memberId) {
            ResponseText rt = new ResponseText("memberId cannot be null", HttpStatus.BAD_REQUEST);
            return JSON.toJSONString(rt);
        }

        List<JobPostModel> list = jobService.getJobPostListByMember(memberId, pageNumber, pageSize, null);
        ResponseText rt = new ResponseText(list);

        return JSON.toJSONString(rt);
    }
}
