package com.fh.taolijie.controller.admin;

import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.service.job.JobPostCateService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 * 兼职管理
 */
@Controller
@RequestMapping("manage/job")
public class AJobController {

    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;



    /**
     * 兼职列表页面
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String jobs(Model model){
        int page = 0;
        int pageSize = Integer.MAX_VALUE;

        List<JobPostModel> jobs;
        jobs = jobPostService.getAllJobPostList(page, pageSize)
                .getList();

        model.addAttribute("jobs", jobs);
        return "pc/admin/jobs";
    }

    /**
     * 删除用户的兼职
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteInfo(@PathVariable int id){
        JobPostModel job = jobPostService.findJobPost(id);

        if(!jobPostService.deleteJobPost(id)){
            return new JsonWrapper(false, ErrorCode.FAILED).getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();

    }
}
