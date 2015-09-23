package com.fh.taolijie.controller.restful.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.QuestCategoryModel;
import com.fh.taolijie.service.quest.QuestCateService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 任务分类的查询操作
 * Created by whf on 9/23/15.
 */
@RestController
@RequestMapping("/api/quest/cate")
public class RestQuestCateController {
    @Autowired
    private QuestCateService cateService;

    /**
     * 查询所有任务分类
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText getAll(@RequestParam(value = "pn", defaultValue = "0") int pageNumber,
                               @RequestParam(value = "ps", defaultValue = Constants.PAGE_CAP) int pageSize) {

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);

        ListResult<QuestCategoryModel> lr = cateService.findAll(pageNumber, pageSize);

        return new ResponseText(lr);
    }

    /**
     * 根据id查询分类信息
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText findById(@PathVariable("id") Integer cateId) {
        QuestCategoryModel cate = cateService.find(cateId);

        return new ResponseText(cate);
    }

    /**
     * 根据分类名查询分类信息
     * @return
     */
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText findById(@PathVariable("name") String name) {
        QuestCategoryModel cate = cateService.find(name);

        return new ResponseText(cate);
    }
}
