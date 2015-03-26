package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.NewsDto;
import com.fh.taolijie.controller.dto.ResumeDto;
import com.fh.taolijie.controller.dto.SecondHandPostDto;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.service.ResumeService;
import com.fh.taolijie.service.SHPostService;
import com.fh.taolijie.utils.ResponseUtils;
import com.sun.javafx.sg.prism.NGShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Controller
public class HomeController {

    @Autowired
    NewsService newsService;
    @Autowired
    JobPostService jobPostService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    SHPostService shPostService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * 主页
	 */
	@RequestMapping(value = {"index","/"}, method = {RequestMethod.GET,RequestMethod.HEAD})
	public String home(HttpSession session,
                       Model model,
                       HttpServletRequest req) {
        System.out.println("session"+session);
        Credential credential =  CredentialUtils.getCredential(session);
        String username = "个人中心";
        List<NewsDto> list = null;

        if(credential!=null){
            username = credential.getUsername();
        }

        list = newsService.getNewsList(0,3);

        System.out.println(username);
        model.addAttribute("username", username);
        model.addAttribute("newsList",list);


        return ResponseUtils.determinePage(req,"/index");
	}


    /**
     * 兼职列表
     */
    @RequestMapping(value = {"joblist"},method = RequestMethod.GET)
    public String joblist(Model model){
        List<JobPostDto> list = null;
       /*list = jobPostService.getJobPostList()*/
        model.addAttribute("joblist",list);
        return "mobile/joblist";
    }

    /**
     * 简历列表
     */
    @RequestMapping(value = {"resumelist"},method = RequestMethod.GET)
    public String resumeList(Model model){
        List<ResumeDto> list = null;
        model.addAttribute("resumeList",list);
        return "mobile/resumelist";
    }

    /**
     * 二手列表
     */
    @RequestMapping(value = {"shlist"},method = RequestMethod.GET)
    public String shList(Model model){
        List<SecondHandPostDto> list = null;
        model.addAttribute("shlist",list);
        return "mobile/shlist";
    }

    /**
     * 新闻列表
     */
    @RequestMapping(value = {"newslist"},method = RequestMethod.GET)
    public String newslist(Model model){
        List<NewsDto> list = null;
        /*暂时未分页*/
        newsService.getNewsList(0,0);
        model.addAttribute("newslist",list);
        return "mobile/newslist";
    }

    /**
     * 新闻页面
     */
    @RequestMapping(value = {"news"},method = RequestMethod.GET)
    public String news(@PathVariable int nid,Model model) {
        NewsDto news = newsService.findNews(nid);
        model.addAttribute("news", news);
        return "mobile/new";
    }

    /**
     * 服务宗旨
     */
    @RequestMapping(value = {"tenet"},method = RequestMethod.GET)
    public String tenet(){
        return "mobile/tenet";
    }

    /**
     * 成员介绍
     */
    @RequestMapping(value = {"member"},method =RequestMethod.GET)
    public String member(){
        return "mobile/member";
    }

    /**
     * 加入我们
     */
    @RequestMapping(value = "join",method = RequestMethod.GET)
    public String joinus(){
        return "mobile/join";
    }


}
