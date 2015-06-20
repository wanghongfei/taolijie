package com.fh.taolijie.controller.home;

import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by wynfrith on 15-6-11.
 */

@Controller
public class HNewsController {

    @Autowired
    NewsService newsService;

    /**
     * 新闻列表
     * @param model
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list/news", method = RequestMethod.GET)
    public String newsList(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = Constants.PAGE_CAPACITY+"") int pageSize) {

        ObjWrapper objWrapper = new ObjWrapper();
        List<NewsModel> newsList = newsService.getNewsList(page-1,pageSize,objWrapper);
//        int totalPage = (Integer)objWrapper.getObj();

        model.addAttribute("page",page);
//        model.addAttribute("totalPage",totalPage);
        model.addAttribute("newsList",newsList);

        return "pc/focus";
    }

    /**
     * 新闻详细页面
     */
    @RequestMapping(value = "detail/news/{nid}", method = RequestMethod.GET)
    public String news(@PathVariable int nid, Model model) {
        NewsModel news = newsService.findNews(nid);
        model.addAttribute("news", news);
        return "pc/focusdetail";
    }
}
