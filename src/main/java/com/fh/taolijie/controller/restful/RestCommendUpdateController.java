package com.fh.taolijie.controller.restful;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.RecommendedPostModel;
import com.fh.taolijie.service.RecommendService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by whf on 7/11/15.
 */

@RestController
@RequestMapping("/api/recommend/u")
public class RestCommendUpdateController {
    @Autowired
    RecommendService recoService;

    /**
     * 创建推荐信息
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText add(RecommendedPostModel model) {
        model.setValidation(false);
        Integer id = recoService.add(model);

        return new ResponseText(id);
    }


    /**
     * 删除一条推荐信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText delete(@PathVariable("id") Integer id) {
        recoService.deleteById(id);

        return new ResponseText();
    }

    /**
     * 更新一条推荐信息
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText update(RecommendedPostModel model) {
        if (null == model.getIsJob()) {
            return new ResponseText(ErrorCode.EMPTY_FIELD);
        }

        recoService.updateByIdSelective(model);

        return new ResponseText();
    }


    /**
     * 查询推荐申请
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/request", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText findRequest(@RequestParam(value = "validation", defaultValue = "false") boolean validation,
                                    @RequestParam(defaultValue = "0") int pageNumber,
                                    @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<RecommendedPostModel> result = recoService.findNewAppliedRequest(validation, pageNumber, pageSize);

        return new ResponseText(result);
    }
}
