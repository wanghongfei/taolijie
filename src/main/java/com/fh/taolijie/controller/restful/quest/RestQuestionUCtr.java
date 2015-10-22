package com.fh.taolijie.controller.restful.quest;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.quest.EmpQuestStatus;
import com.fh.taolijie.domain.QuestionModel;
import com.fh.taolijie.domain.QuestionOptModel;
import com.fh.taolijie.domain.quest.QuestModel;
import com.fh.taolijie.dto.ExamDto;
import com.fh.taolijie.exception.checked.InvalidNumberStringException;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.service.quest.QuestionService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by whf on 10/22/15.
 */
@RestController
@RequestMapping("/api/user/question")
public class RestQuestionUCtr {
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = Constants.Produce.JSON, consumes = Constants.Produce.JSON)
    public ResponseText publishQuestion(@RequestBody @Valid ExamDto dto,
                                        BindingResult br,
                                        HttpServletRequest req) {
        // 参数合法性检查
        if (br.hasErrors()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }
        if (!validateDto(dto)) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 将id string 转换成List<Integer>
        QuestModel model = dto.getQuest();
        try {
            List<Integer> coList = StringUtils.splitIntendIds(dto.getCollegeIds());
            List<Integer> schList = StringUtils.splitIntendIds(dto.getSchoolIds());
            model.setCollegeIdList(coList);
            model.setSchoolIdList(schList);

        } catch (InvalidNumberStringException e) {
            return new ResponseText(ErrorCode.BAD_NUMBER);
        }

        Credential credential = SessionUtils.getCredential(req);
        dto.getQuest().setMemberId(credential.getId());

        try {
            questionService.publishQuestions(dto.getQuest(), dto.getQuestions());

        } catch (CashAccNotExistsException e) {
            return new ResponseText(ErrorCode.CASH_ACC_NOT_EXIST);

        } catch (BalanceNotEnoughException e) {
            return new ResponseText(ErrorCode.BALANCE_NOT_ENOUGH);
        }

        return ResponseText.getSuccessResponseText();
    }

    private boolean validateDto(ExamDto dto) {
        // 验证Quest对象
        QuestModel quest = dto.getQuest();
        if (!QuestModel.validate(quest)) {
            return false;
        }

        // 验证Question对象
        for (QuestionModel q : dto.getQuestions()) {
            if (!QuestionModel.validate(q)) {
                return false;
            }
            // 验证QuestionOpt对象
            for (QuestionOptModel opt : q.getOpts()) {
                if (!QuestionOptModel.validate(opt)) {
                    return false;
                }
            }
        }

        return true;
    }
}
