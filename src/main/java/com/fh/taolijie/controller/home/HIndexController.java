package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.SHPostModel;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.ShPostService;
import com.fh.taolijie.utils.ObjWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */
@RequestMapping(value = "/")
@Controller
public class HIndexController {

    @Autowired
    JobPostService jobPostService;
    @Autowired
    ShPostService shPostService;

    private static final Logger logger = LoggerFactory.getLogger(HIndexController.class);


    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(HttpSession session, Model model){
        Credential credential = CredentialUtils.getCredential(session);

        if (credential != null) {

        }
        // 二手物品需要加入一个发布人
        List<JobPostModel> jobs = jobPostService.getAllJobPostList(0, 6, new ObjWrapper());
        List<SHPostModel> shs = shPostService.getAllPostList(0, 3, new ObjWrapper());

        model.addAttribute("jobs", jobs);
        model.addAttribute("shs", shs);
        model.addAttribute("mem", credential);

        return "pc/index";
    }

}
