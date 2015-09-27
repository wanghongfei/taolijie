package com.fh.taolijie.controller.restful.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.QuestModel;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 轻兼职查询控制器
 * Created by whf on 9/27/15.
 */
@RestController
@RequestMapping("/api/quest")
public class RestQuestQueryCtr {
    @Autowired
    private QuestService questService;

    /**
     * 条件查询
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText query(@RequestParam(required = false) Integer cateId,
                                           @RequestParam(required = false, defaultValue = "0") int rangeQuery,
                                           @RequestParam(required = false) BigDecimal min,
                                           @RequestParam(required = false) BigDecimal max,

                                           @RequestParam(required = false, defaultValue = "0") int pn,
                                           @RequestParam(required = false, defaultValue = Constants.PAGE_CAP) int ps,
                                           HttpServletRequest req) {

        pn = PageUtils.getFirstResult(pn, ps);

        ListResult<QuestModel> lr = questService.findByCate(cateId, min, max, pn, ps);

        return new ResponseText(lr);
    }
}
