package com.fh.taolijie.controller.restful.quest;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.QuestCoModel;
import com.fh.taolijie.exception.checked.quest.QuestNotFoundException;
import com.fh.taolijie.service.collect.QuestCoService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whf on 10/3/15.
 */
@RestController
@RequestMapping("/api/user/co")
public class RestCollectionCtr {
    @Autowired
    private QuestCoService coService;


    /**
     * 添加收藏
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText collect(@RequestParam Integer questId,
                                HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        try {
            coService.collect(credential.getId(), questId);
        } catch (QuestNotFoundException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);

        }

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 取消收藏
     * @return
     */
    @RequestMapping(value = "/quest/{questId}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText unCollect(@PathVariable("questId") Integer questId,
                                HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        coService.unCollect(credential.getId(), questId);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 查询收藏列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText unCollect(@RequestParam(defaultValue = "0") int pn,
                                  @RequestParam(defaultValue = Constants.PAGE_CAP) int ps,
                                  HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);

        pn = PageUtils.getFirstResult(pn, ps);

        QuestCoModel example = new QuestCoModel(pn, ps);
        example.setMemberId(credential.getId());

        ListResult<QuestCoModel> lr = coService.findBy(example);

        return new ResponseText(lr);
    }

    /**
     * 查询是否已经收藏
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText collected(@RequestParam Integer questId,
                                HttpServletRequest req) {

        Credential credential = SessionUtils.getCredential(req);
        boolean res = coService.collected(credential.getId(), questId);

        return new ResponseText(res);
    }
}
