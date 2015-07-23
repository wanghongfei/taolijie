package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.RecommendedPostModel;
import com.fh.taolijie.service.RecommendService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 7/11/15.
 */
@RestController
@RequestMapping("/api/recommend")
public class RestCommendController {
    @Autowired
    RecommendService recoService;

    /**
     * 查询通过审核的推荐列表
     * @param type
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", produces = Constants.Produce.JSON)
    public ResponseText getRecommendList(@RequestParam String type,
                                         @RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {
        if (null == type) {
            return new ResponseText("invalid request");
        }

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<RecommendedPostModel> result = null;
        if ("job".equals(type)) {
            result = recoService.getJobList(pageNumber, pageSize);
        } else if ("sh".equals(type)) {
            result = recoService.getShList(pageNumber, pageSize);
        } else if ("resume".equals(type)) {
            result = recoService.getResumeList(pageNumber, pageSize);
        } else {
            return new ResponseText("invalid request");
        }

        return new ResponseText(result);
    }

    /**
     * 查询单条推荐信息
     * @return
     */
    @RequestMapping(value = "/{id}", produces = Constants.Produce.JSON)
    public ResponseText getRecommendById(@PathVariable("id") Integer id) {
        RecommendedPostModel result = recoService.findById(id);

        return new ResponseText(result);
    }
}
