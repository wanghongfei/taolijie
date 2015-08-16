package com.fh.taolijie.interceptor;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.JobPostCategoryModel;
import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.domain.SHPostCategoryModel;
import com.fh.taolijie.service.JobPostCateService;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.ShPostCategoryService;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wynfrith on 15-5-18.
 */

@Component
public class CommonInterceptor extends HandlerInterceptorAdapter{
    @Autowired
    NewsService newsService;
    @Autowired
    JobPostCateService jobPostCateService;
    @Autowired
    ShPostCategoryService shPostCategoryService;
    @Autowired
    ResumeService resumeService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //首页新闻
        ListResult<NewsModel> list = newsService.getNewsList(0,3);
        //首页兼职二手分类(简历和兼职分类相同)
        ListResult<JobPostCategoryModel> jobs = jobPostCateService.getCategoryList(0, Integer.MAX_VALUE);
        ListResult<SHPostCategoryModel> shs = shPostCategoryService.getCategoryList(0, Integer.MAX_VALUE);

        request.setAttribute("titles",list.getList());
        request.setAttribute("sideJobCate",jobs.getList());
        request.setAttribute("sideSHCate",shs.getList());
        request.setAttribute("sideResumeCate",jobs.getList());
        return  true;
    }

}
