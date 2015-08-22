package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.RecommendedPostModel;
import com.fh.taolijie.service.RecommendService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 8/22/15.
 */
@RestController
@RequestMapping("/api/u/recommend")
public class RestCommendUController {
    @Autowired
    RecommendService recoService;


    /**
     * 创建推荐信息
     * <p>{@code POST /}
     *
     * @param model
     */
    @RequestMapping(method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText add(RecommendedPostModel model) {
        model.setValidation(false);
        Integer id = recoService.add(model);

        return new ResponseText(id);
    }
}
