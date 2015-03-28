package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostCategoryDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.MemberRoleDto;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wynfrith on 15-3-27.
 */


/**
 * api控制器
 * 提供各种接口
 */
@Controller
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    JobPostService jobPostService;



    /**
     * 获取兼职分类
     * @return
     */
    @RequestMapping(value = "jobcate",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String jobCategory(){
        List<JobPostCategoryDto> list = jobPostCateService.getCategoryList(0, 0);
        return JSON.toJSONString(list);
    }

    /**
     * 所有兼职
     */
    @RequestMapping(value = {"joblist"},method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String joblist(){
        List<JobPostDto> list = jobPostService.getAllJobPostList(0,0);
        return JSON.toJSONString(list);
    }

}
