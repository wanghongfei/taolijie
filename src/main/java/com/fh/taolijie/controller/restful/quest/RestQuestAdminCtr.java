package com.fh.taolijie.controller.restful.quest;

import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.quest.EmpQuestStatus;
import com.fh.taolijie.exception.checked.quest.QuestNotFoundException;
import com.fh.taolijie.service.quest.QuestService;
import com.fh.taolijie.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whf on 10/31/15.
 */
@RestController
@RequestMapping("/api/manage/quest")
public class RestQuestAdminCtr {
    @Autowired
    private QuestService questService;

    /**
     * 修改任务状态
     * @return
     */
    @RequestMapping(value = "/{questId}", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText changeStatus(@PathVariable Integer questId,
                                     @RequestParam Integer status,
                                     HttpServletRequest req) {

        EmpQuestStatus empStatus = EmpQuestStatus.fromCode(status);
        if (null == empStatus) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        try {
            questService.changeEmpStatus(questId, empStatus);

        } catch (QuestNotFoundException e) {
            return new ResponseText(ErrorCode.NOT_FOUND);

        }

        return ResponseText.getSuccessResponseText();
    }
}
