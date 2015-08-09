package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 8/9/15.
 */
@RestController
@RequestMapping("/api/news")
public class RestNewsController {
    @Autowired
    NewsService newsService;

    /**
     * 查询所有新闻
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", produces = Constants.Produce.JSON)
    public ResponseText getList(@RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<NewsModel> lr = newsService.getNewsList(pageNumber, pageSize);

        return new ResponseText(lr);
    }

    /**
     * 查询一条新闻
     * @param newsId
     * @return
     */
    @RequestMapping(value = "/{id}", produces = Constants.Produce.JSON)
    public ResponseText getList(@PathVariable("id") Integer newsId) {
        NewsModel news = newsService.findNews(newsId);

        return new ResponseText(news);
    }
}
