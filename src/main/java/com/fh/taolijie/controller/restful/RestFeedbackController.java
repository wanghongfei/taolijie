package com.fh.taolijie.controller.restful;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.FeedbackModel;
import com.fh.taolijie.service.FeedbackService;
import com.fh.taolijie.service.impl.Mail;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by whf on 7/9/15.
 */
@RestController
@RequestMapping("/api/feedback")
public class RestFeedbackController {
    @Autowired
    Mail mail;

    @Autowired
    FeedbackService fdService;

    /**
     * 创建反馈
     * @param content
     * @param email
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText sendFeedback(@RequestParam String content,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(defaultValue = "0") int send, // 是否发邮件. 0为不发, 1为发
                                     @RequestParam String email,
                                     HttpServletRequest req) {

        if (false == StringUtils.checkNotEmpty(content)) {
            return new ResponseText(ErrorCode.EMPTY_FIELD);
        }

        // send mail
        if (0 != send) {
            mail.sendMailAsync(content, Constants.MailType.FEEDBACK, email);
        }

        // insert data
        FeedbackModel fd = new FeedbackModel();
        fd.setContent(content);
        fd.setTitle(title);
        fd.setCreatedTime(new Date());
        // 如果用户已经登陆，则自动关联当前用户
        //Credential credential = CredentialUtils.getCredential(session);
        Credential credential = SessionUtils.getCredential(req);
        if (null != credential) {
            fd.setMemberId(credential.getId());
            fd.setUsername(credential.getUsername());
        }

        fdService.addFeedback(fd);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 查询所有反馈
     * @return
     */
    @RequestMapping(value = "/manager/list", produces = Constants.Produce.JSON)
    public ResponseText queryFeedback(@RequestParam(defaultValue = "0") int pageNumber,
                                      @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<FeedbackModel> lr = fdService.getAll(pageNumber, pageSize);

        return new ResponseText(lr);
    }

    /**
     * 查询单条反馈
     * @return
     */
    @RequestMapping(value = "/manager/{fdId}", produces = Constants.Produce.JSON)
    public ResponseText queryFeedback(@PathVariable Integer fdId) {
        FeedbackModel fd = fdService.findById(fdId);

        return new ResponseText(fd);
    }

    /**
     * 删除一条反馈
     * @param fdId
     * @return
     */
    @RequestMapping(value = "/manager/{fdId}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText deleteFeedback(@PathVariable("fdId") Integer fdId) {
        fdService.deleteById(fdId);

        return ResponseText.getSuccessResponseText();
    }


    /**
     * 批量删除
     * @param idsStr
     * @return
     */
    @RequestMapping(value = "/manager/del", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText deleteFeedback(@RequestParam("ids") String idsStr) {
        // 验证
        if (false == StringUtils.checkNotEmpty(idsStr)) {
            return new ResponseText(ErrorCode.EMPTY_FIELD);
        }

        String[] idsStrs = idsStr.split(Constants.DELIMITER);
        if (null == idsStrs || 0 == idsStrs.length) {
            return new ResponseText(ErrorCode.EMPTY_FIELD);
        }

        // 转换成整数表
        List<Integer> idList = null;
        try {
            idList = Arrays.stream(idsStrs)
                    .map( idStr -> Integer.valueOf(idStr) )
                    .collect(Collectors.toList());
        } catch (NumberFormatException ex) {
            // 转换失败说明有非数字字符
            return new ResponseText(ErrorCode.BAD_NUMBER);
        }

        fdService.deleteInBatch(idList);

        return ResponseText.getSuccessResponseText();
    }

}
