package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.domain.acc.RoleModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.PVService;
import com.fh.taolijie.service.job.JobPostCateService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private PVService pvService;


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
    public String jobList(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "0") int cate,
                          @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                          Model model) {
        ListResult<JobPostModel> jobs;

        page = PageUtils.getFirstResult(page, pageSize);

        if (cate > 0) {
            jobs = jobPostService.getJobPostListByCategory(cate, page, pageSize);
        } else {
            jobs = jobPostService.getAllJobPostList(page, pageSize);
        }

//        int totalPage = (Integer) objWrapper.getObj();
        int pageStatus = 1;
        if(jobs.getList().size() == 0){
            pageStatus = 0;
        }else if(jobs.getList().size() == pageSize){
            pageStatus = 2;
        }

        pvService.pvMatch(jobs.getList());

        model.addAttribute("pageStatus",pageStatus);
        model.addAttribute("jobs", jobs);
        model.addAttribute("page", page);
//      model.addAttribute("totalPage", totalPage);
        return "pc/joblist";
    }
    //endregion


    /**
     * 查询一条兼职
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "item/job/{id}", method = RequestMethod.GET)
    //region 查询一条兼职 jobItem
    public String jobItem(@PathVariable int id,
                          HttpServletRequest req,
                          //HttpSession session,
                          @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                          @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_CAPACITY + "") Integer pageSize,
                          Model model) {
        JobPostModel job = jobPostService.findJobPostWithPV(id);

        if (job == null) {
            return "redirect:/404";
        }

        MemberModel poster = accountService.findMember(job.getMemberId());
        RoleModel role = poster.getRoleList().iterator().next();

        //收藏的显示状态
        boolean status = false; //不显示
        //Credential credential = CredentialUtils.getCredential(session);
        Credential credential = SessionUtils.getCredential(req);
        if (credential == null)
            status = false;
        else { //查找有没有收藏
            status = jobPostService.isPostFavorite(credential.getId(),id);
        }

        // 查询评论
        // 计算页数
        pageNumber = pageNumber.intValue() * pageSize.intValue();
        ReviewModel reviewCommand = new ReviewModel(pageNumber, pageSize);
        reviewCommand.setJobPostId(id);
        ListResult<ReviewModel> reviewResult = reviewService.getReviewList(reviewCommand);
        List<ReviewModel> reviewList = reviewResult.getList();
        long reviewCount = reviewResult.getResultCount();


        model.addAttribute("job", job);
        model.addAttribute("poster", poster);
        model.addAttribute("posterRole", role);
        model.addAttribute("favStatus", status);
        model.addAttribute("reviews", reviewList);
        model.addAttribute("reviewCount", reviewCount);
        return "pc/jobdetail";
    }
    //endregion






}
