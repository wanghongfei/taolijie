package com.fh.taolijie.controller.restful.quest;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.QuestCategoryModel;
import com.fh.taolijie.exception.checked.CategoryNotEmptyException;
import com.fh.taolijie.service.quest.QuestCateService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by whf on 9/24/15.
 */
@RestController
@RequestMapping("/api/manage/quest/cate")
public class RestQuestCateAdminController {
    @Autowired
    private QuestCateService cateService;

    /**
     * 添加一个分类
     * @param model
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText addCate(@Valid QuestCategoryModel model,
                                BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        int id = cateService.add(model);

        return new ResponseText(id);
    }

    /**
     * 修改一个分类
     * @param model
     * @return
     */
    @RequestMapping(value = "/{cateId}", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText updateCate(@PathVariable Integer cateId,
                                   QuestCategoryModel model) {
        if (!notAllNull(model.getName(), model.getLevel(), model.getMemo(), model.getThemeColor())) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        model.setId(cateId);
        int rows = cateService.update(model);
        if (rows <= 0) {
            return new ResponseText(ErrorCode.FAILED);
        }

        return new ResponseText();
    }

    /**
     * 删除分类
     * @param cateId
     * @return
     */
    @RequestMapping(value = "/{cateId}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText updateCate(@PathVariable Integer cateId) {
        try {
            cateService.delete(cateId);
        } catch (CategoryNotEmptyException e) {
            return new ResponseText(ErrorCode.CATE_NOT_EMPTY);
        }

        return new ResponseText();
    }

    /**
     * 检查所有参数是否是不是全部为空
     * @param objs
     * @return 不是全部为空返回true
     */
    private boolean notAllNull(Object... objs) {
        for (Object o : objs) {
            if (null != o) {
                return true;
            }
        }

        return false;
    }
}
