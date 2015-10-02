package com.fh.taolijie.controller.restful.quest;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.domain.TljAuditModel;
import com.fh.taolijie.service.quest.TljAuditService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whf on 10/2/15.
 */
@RestController
@RequestMapping("/api/manage/audit")
public class RestAuditAdminCtr {
    @Autowired
    private TljAuditService auditService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText auditList(@RequestParam(required = false) Integer questId,
                                  @RequestParam(defaultValue = "0") int pn,
                                  @RequestParam(defaultValue = Constants.PAGE_CAP) int ps,
                                  HttpServletRequest req) {

        TljAuditModel cmd = new TljAuditModel(pn, ps);
        cmd.setQuestId(questId);

        ListResult<TljAuditModel> lr = auditService.findBy(cmd);
        return new ResponseText(lr);

    }
}
