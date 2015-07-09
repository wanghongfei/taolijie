package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.ApplicationIntendModel;
import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.ResumeModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */

@Controller
public class HResumeController {

    @Autowired
    ResumeService resumeService;
    @Autowired
    AccountService accountService;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;


    /**
     * 简历库列表
     *
     * @param page
     * @param cate
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping(value = {"list/resume"}, method = RequestMethod.GET)
    public String resumeList(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "0") int cate,
                             @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                             Model model,
                             HttpSession session) {
        ObjWrapper objWrapper = new ObjWrapper();
        List<ResumeModel> resumes = new ArrayList<>(10);
        if (cate > 0) {
            // 按求职意向查找
            resumes = resumeService.getResumeListByIntend(cate, (page - 1)*pageSize, pageSize);
        } else {
            // 查找所有简历
            Credential credential = CredentialUtils.getCredential(session);
            // 如果是商家
            if (null != credential && Constants.RoleType.EMPLOYER.toString().equals(credential.getRoleList().get(0))) {
                resumes = resumeService.findByAuthes(page, pageSize, Constants.AccessAuthority.ALL, Constants.AccessAuthority.EMPLOYER);
            } else {
                resumes = resumeService.findByAuthes(page, pageSize, Constants.AccessAuthority.ALL);
            }
        }

        int pageStatus = 1;
        if(resumes.size() == 0){
            pageStatus = 0;
        }else if(resumes.size() == pageSize){
            pageStatus = 2;
        }
        model.addAttribute("pageStatus",pageStatus);
        model.addAttribute("resumes", resumes);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);

        return "pc/resumelist";
    }

    /**
     * 简历详情页
     * <p/>
     *
     * @param id      简历的id号
     * @param model
     * @param session
     */
    @RequestMapping(value = "item/resume/{id}", method = RequestMethod.GET)
    public String resumeDetail(@PathVariable("id") int id, Model model, HttpSession session) {
        boolean contactDisplay = false;
        Credential credential = CredentialUtils.getCredential(session);
        if (credential != null) {
            MemberModel member = accountService.findMember(credential.getUsername(), true);
        }
        System.out.println("resume:" + id);
        ResumeModel resumeDto = resumeService.findResume(id);
        //查询求职意向
        List<ApplicationIntendModel> intends = resumeService.getIntendByResume(id);
        //查询发布人的用户名
        MemberModel user = accountService.findMember(resumeDto.getMemberId());

        //收藏的显示状态
        boolean status = false; //不显示
        if (credential == null)
            status = false;
        else { //查找有没有收藏
            status = resumeService.isAlreadyFavorite(credential.getId(), id);
        }
        model.addAttribute("resume", resumeDto);
        model.addAttribute("postUser", user);
        model.addAttribute("favStatus", status);
        model.addAttribute("isShow", true);//显示收藏
        model.addAttribute("intendJobs", intends);
//        model.addAttribute("contactDisplay",contactDisplay);
        return "pc/resumedetail";
    }

}
