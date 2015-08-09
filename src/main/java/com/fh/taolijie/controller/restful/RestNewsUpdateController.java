package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.NewsModel;
import com.fh.taolijie.service.NewsService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by whf on 8/9/15.
 */
@RestController
@RequestMapping("/api/manage/news")
public class RestNewsUpdateController {
    @Autowired
    NewsService newsService;

    /**
     * 删除一条新闻
     * @param newsId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText getList(@PathVariable("id") Integer newsId) {
        newsService.deleteNews(newsId);

        return new ResponseText();
    }

    /**
     * 添加一条新闻
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText addNews(@Valid NewsModel model, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseText("invalid request");
        }

        model.setTime(new Date());
        newsService.addNews(model);

        return new ResponseText();
    }

    /**
     * 修改新闻
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText updateNews(NewsModel model) {
        if (null == model.getId()) {
            return new ResponseText("id cannot be null");
        }

        newsService.updateNews(model);

        return new ResponseText();
    }
}
