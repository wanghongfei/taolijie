package com.fh.taolijie.controller.home;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.domain.job.JobPostCategoryModel;
import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.domain.sh.SHPostCategoryModel;
import com.fh.taolijie.domain.sh.SHPostModel;
import com.fh.taolijie.service.*;
import com.fh.taolijie.service.job.JobPostCateService;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.service.sh.ShPostCategoryService;
import com.fh.taolijie.service.sh.ShPostService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    JobPostCateService jobCateService;
    @Autowired
    ShPostService shPostService;
    @Autowired
    ShPostCategoryService shCateService;

    @Autowired
    BannerPicService banService;

    /**
     * 显示主页
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(Model model){
        //Credential credential = CredentialUtils.getCredential(session);

        // 放入兼职二手信息
        List<JobPostModel> jobs = jobPostService.getAllJobPostList(0, 6)
                .getList();

        List<SHPostModel> shs = shPostService.getAllPostList(0, 3)
                .getList();

        // 放入banner
        ListResult<BannerPicModel> banResult = banService.getBannerList(0, Integer.MAX_VALUE);

        model.addAttribute("jobs", jobs);
        model.addAttribute("shs", shs);
        //model.addAttribute("mem", credential);

        model.addAttribute("bannerList", banResult.getList());
        model.addAttribute("bannerCount", banResult.getResultCount());

        return "pc/index";
    }


    /**
     * 搜索
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(defaultValue = "") String content,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "job") String type,
                         @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                         Model model) {


        page = PageUtils.getFirstResult(page, pageSize);

        if(type.equals("job")){
            JobPostModel jobCommand = new JobPostModel();

            // 根据标题或内容匹配
            jobCommand.setTitle(content);
            jobCommand.setJobDetail(content);

            // 检查关键字是不是分类名
            JobPostCategoryModel cate = jobCateService.findByName(content);
            if (null != cate) {
                jobCommand.setJobPostCategoryId(cate.getId());
            }

            List<JobPostModel> list = jobPostService.runSearch(jobCommand, page, pageSize)
                    .getList();

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
        } else {
            SHPostModel shCommand = new SHPostModel();
            // 根据标题或内容匹配
            shCommand.setTitle(content);
            shCommand.setDescription(content);

            // 查询关键字是不是分类名
            SHPostCategoryModel cate = shCateService.findByName(content);
            if (null != cate) {
                shCommand.setSecondHandPostCategoryId(cate.getId());
            }

            ListResult<SHPostModel> list =shPostService.runSearch(shCommand);

            int pageStatus = 1;
            if(list.getList().size() == 0){
                pageStatus = 0;
            }else if(list.getList().size() == pageSize){
                pageStatus = 2;
            }
            model.addAttribute("pageStatus",pageStatus);

            model.addAttribute("shs", list.getList());
            model.addAttribute("page", page);
            model.addAttribute("fromSearch", "true");
            return "pc/shlist";
        }

    }




}
