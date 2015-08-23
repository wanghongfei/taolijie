package com.fh.taolijie.controller.restful.admin;

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
 * 与帖子推荐相关的管理员操作。
 * <p>{@code /api/manage/recommend}
 */
@RestController
@RequestMapping("/api/manage/recommend")
public class RestCommendAdminController {
    @Autowired
    RecommendService recoService;


    /**
     * 删除一条推荐信息
     * <p>{@code DELETE /{id}}
     *
     * @param id 要删除的帖子的id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText delete(@PathVariable("id") Integer id) {
        recoService.deleteById(id);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 更新一条推荐信息
     * <p>{@code PUT /}
     *
     * @param model
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText update(@PathVariable Integer id,
                               RecommendedPostModel model) {
        model.setId(id);

        recoService.updateByIdSelective(model);

        return ResponseText.getSuccessResponseText();
    }


    /**
     * 查询推荐申请
     * <p>{@code GET /request}
     *
     * @param pageNumber 页数，从0开始
     * @param pageSize 每页的结果数
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
