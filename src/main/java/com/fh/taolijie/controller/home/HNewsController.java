package com.fh.taolijie.controller.home;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {

        page = PageUtils.getFirstResult(page, pageSize);
        ListResult<NewsModel> newsList = newsService.getNewsList(page, pageSize);

        int pageStatus = 1;
        if (newsList.getList().size() == 0) {
            pageStatus = 0;
        } else if (newsList.getList().size() == pageSize) {
            pageStatus = 2;
        }
        model.addAttribute("pageStatus",pageStatus);
        model.addAttribute("page",page);
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
