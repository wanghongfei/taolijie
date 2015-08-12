package com.fh.taolijie.controller.admin;

import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.ResumeModel;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
@RequestMapping("manage/resume")
public class AResumeController {

    @Autowired
    ResumeService resumeService;

    /**
     * 简历列表页面
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String jobs(Model model){
        int page = 0;
        int pageSize = Integer.MAX_VALUE;

        List<ResumeModel> resumes = resumeService.getAllResumeList(page, pageSize);

        model.addAttribute("resumes", resumes);
        return "pc/admin/resumes";
    }

    /**
     * 删除用户的简历
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String deleteInfo(@PathVariable int id){
        ResumeModel resume=resumeService.findResume(id);

        if(!resumeService.deleteResume(id)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();

    }

}
