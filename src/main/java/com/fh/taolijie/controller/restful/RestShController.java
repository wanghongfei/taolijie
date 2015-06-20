package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.SHPostModel;
import com.fh.taolijie.service.ShPostService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wanghongfei on 15-6-20.
 */
@RestController
@RequestMapping("/api/sh")
public class RestShController {
    @Autowired
    ShPostService shService;

    @RequestMapping(value = "/list", produces = Constants.Produce.JSON)
    public ResponseText getAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                               @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") Integer pageSize) {

        List<SHPostModel> shList = shService.getAllPostList(pageNumber, pageSize, null);

        return new ResponseText(shList);
    }

    /**
     * 根据分类查询sh
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/category/{categoryId}", produces = Constants.Produce.JSON)
    public ResponseText getByCategory(@PathVariable("categoryId") Integer categoryId,
                                      @RequestParam(defaultValue = "0") Integer pageNumber,
                                      @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") Integer pageSize) {

        List<SHPostModel> shList = shService.getPostList(categoryId, pageNumber, pageSize, null);

        return new ResponseText(shList);
    }

    /**
     * 根据分类查询。可指定是否根据pageView排序
     * @param categoryId
     * @param pageView true表示 ORDER BY pageView DESC
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText getByCategory(@PathVariable("categoryId") Integer categoryId,
                                      @RequestParam(defaultValue = "true") Boolean pageView,
                                      @RequestParam(defaultValue = "0") Integer pageNumber,
                                      @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") Integer pageSize) {

        List<SHPostModel> shList = shService.getAndFilter(categoryId, pageView, pageNumber, pageSize, null);
        return new ResponseText(shList);
    }


    /**
     * 根据用户查询sh
     * @param userId
     * @param filter
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/user/{userId}", produces = Constants.Produce.JSON)
    public ResponseText getByMember(@PathVariable("userId") Integer userId,
                                    @RequestParam(defaultValue = "true") Boolean filter,
                                      @RequestParam(defaultValue = "0") Integer pageNumber,
                                      @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") Integer pageSize) {

        List<SHPostModel> shList = shService.getPostList(userId, filter, pageNumber, pageSize, null);
        return new ResponseText(shList);
    }

    /**
     * 搜索二手信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText search(SHPostModel model) {

        List<SHPostModel> shList = shService.runSearch(model, null);
        return new ResponseText(shList);
    }

}
