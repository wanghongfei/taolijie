package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.SHPostModel;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(HttpSession session, Model model){
        Credential credential = CredentialUtils.getCredential(session);

        if (credential != null) {

        }
        List<JobPostModel> jobs = jobPostService.getAllJobPostList(0, 6, new ObjWrapper());
        List<SHPostModel> shs = shPostService.getAllPostList(0, 3, new ObjWrapper());

        model.addAttribute("jobs", jobs);
        model.addAttribute("shs", shs);
        model.addAttribute("mem", credential);

        return "pc/index";
    }


    /**
     * 搜索一条兼职
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(defaultValue = "") String content,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "job") String type,
                         @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                         Model model) {


        if(type.equals("job")){
            JobPostModel jobPostModel = new JobPostModel();
            jobPostModel.setTitle(content);
            List<JobPostModel> list = jobPostService.runSearch(jobPostModel, (page - 1)*pageSize, pageSize,new ObjWrapper());

            int pageStatus = 1;
            if(list.size() == 0){
                pageStatus = 0;
            }else if(list.size() == pageSize){
                pageStatus = 2;
            }
            model.addAttribute("pageStatus",pageStatus);
            model.addAttribute("jobs", list);
            model.addAttribute("page", page);
            return "pc/joblist";
        } else{
            SHPostModel shPostModel = new SHPostModel();
            List<SHPostModel> list =shPostService.runSearch(shPostModel,new ObjWrapper());

            int pageStatus = 1;
            if(list.size() == 0){
                pageStatus = 0;
            }else if(list.size() == pageSize){
                pageStatus = 2;
            }
            model.addAttribute("pageStatus",pageStatus);

            model.addAttribute("shs", list);
            model.addAttribute("page", page);
            return "pc/joblist";
        }

    }




}
