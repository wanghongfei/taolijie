package com.fh.taolijie.controller.restful.quest;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.QuestCategoryModel;
import com.fh.taolijie.service.quest.QuestCateService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
