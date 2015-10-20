package com.fh.taolijie.controller.restful.recommend;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.RecoPostModel;
import com.fh.taolijie.service.RecoService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by whf on 10/17/15.
 */
@RestController
@RequestMapping("/api/manage/re")
public class RestRecoAdminCtr {

    @Autowired
    private RecoService reService;

    /**
     * 推荐审核
     * @param req
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText myApply(HttpServletRequest req,
                                @PathVariable("id") Integer reId,
                                @RequestParam int index,
                                @RequestParam(defaultValue = "1") int valid) { // 0: invalid, 1: valid

        RecoPostModel cmd = new RecoPostModel();
        cmd.setId(reId);
        cmd.setOrderIndex(index);
        cmd.setValid(valid == 1);
        cmd.setAuditTime(new Date());

        int rows = reService.update(cmd);
        if (rows <= 0) {
            return new ResponseText(ErrorCode.FAILED);
        }

        return ResponseText.getSuccessResponseText();

    }
}
