package com.fh.taolijie.interceptor;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.job.JobPostCategoryModel;
import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.domain.sh.SHPostCategoryModel;
import com.fh.taolijie.service.job.JobPostCateService;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.sh.ShPostCategoryService;
import com.fh.taolijie.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wynfrith on 15-5-18.
 */

@Component
public class CommonInterceptor extends HandlerInterceptorAdapter{

    public static Logger infoLogger = LoggerFactory.getLogger(CommonInterceptor.class);

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


        return super.preHandle(request, response, handler);
    }

}
