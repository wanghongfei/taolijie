package com.fh.taolijie.controller.restful.admin;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
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
 * 与新闻相关的管理操作
 * <p>{@code /api/manage/news}
 */
@RestController
@RequestMapping("/api/manage/news")
public class RestNewsAdminController {
    @Autowired
    NewsService newsService;

    /**
     * 删除一条新闻
     * <p>{@code DELETE /{id}}
     *
     * @param id 要删除的新闻id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText getList(@PathVariable("id") Integer newsId) {
        newsService.deleteNews(newsId);

        return new ResponseText();
    }

    /**
     * 添加一条新闻
     * <p>{@code POST /}
     *
     */
    @RequestMapping(method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText addNews(@Valid NewsModel model, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        model.setTime(new Date());
        newsService.addNews(model);

        return new ResponseText();
    }

    /**
     * 修改新闻.
     * <p>{@code PUT /}
     * <p>根据{@link NewsModel#id}字段定位被修改的新闻.
     *
     */
    @RequestMapping(method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText updateNews(NewsModel model) {
        if (null == model.getId()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        newsService.updateNews(model);

        return new ResponseText();
    }
}
