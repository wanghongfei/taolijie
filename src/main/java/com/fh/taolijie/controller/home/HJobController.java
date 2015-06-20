package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.JobPostModel;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.domain.RoleModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.ReviewService;
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
public class HJobController {

    @Autowired
    JobPostService jobPostService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    AccountService accountService;


    /**
     * 兼职二级页面
     * @param page 页码
     * @param cate 分类id,如果查找全部默认id = 0
     * @param pageSize 分页数
     * @param model
     * @return
     */
    @RequestMapping(value = {"list/job"}, method = RequestMethod.GET)
    //region 兼职二级页面 jobList(
    public String jobList(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "0") int cate,
                          @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                          Model model) {
        ObjWrapper objWrapper = new ObjWrapper();
        List<JobPostModel> jobs;
        if (cate > 0) {
            jobs = jobPostService.getJobPostListByCategory(cate, page - 1, pageSize, objWrapper);
        } else {
            jobs = jobPostService.getAllJobPostList(page - 1, pageSize, objWrapper);
        }

//        int totalPage = (Integer) objWrapper.getObj();

        model.addAttribute("jobs", jobs);
        model.addAttribute("page", page);
//      model.addAttribute("totalPage", totalPage);
        return "pc/joblist";
    }
    //endregion


    /**
     * 查询一条兼职
     * @param id
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "item/job/{id}", method = RequestMethod.GET)
    //region 查询一条兼职 jobItem
    public String jobItem(@PathVariable int id, HttpSession session, Model model) {
        JobPostModel job = jobPostService.findJobPost(id);

        if (job == null) {
            return "redirect:/404";
        }

        MemberModel poster = accountService.findMember(job.getMemberId());
        RoleModel role = poster.getRoleList().iterator().next();

        //收藏的显示状态
        boolean status = false; //不显示
        Credential credential = CredentialUtils.getCredential(session);
        if (credential == null)
            status = false;
        else { //查找有没有收藏
            status = jobPostService.isPostFavorite(credential.getId(),id);
        }

        model.addAttribute("job", job);
        model.addAttribute("poster", poster);
        model.addAttribute("posterRole", role);
        model.addAttribute("favStatus", status);
        return "pc/jobdetail";
    }
    //endregion






}
