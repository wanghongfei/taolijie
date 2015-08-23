package com.fh.taolijie.controller.restful;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.ReviewModel;
import com.fh.taolijie.service.ReviewService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by whf on 8/2/15.
 */
@RestController
@RequestMapping("/api/review/u")
public class RestReviewUpdateController {
    @Autowired
    ReviewService reService;

    /**
     * 回复评论
     * @return
     */
    @RequestMapping(value = "/reply", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText reply(ReviewModel model,
                              HttpSession session) {
        if (null == model.getRepliedReviewId()) {
            return new ResponseText(ErrorCode.EMPTY_FIELD);
        }

        if (false == StringUtils.checkNotEmpty(model.getContent())) {
            return new ResponseText(ErrorCode.EMPTY_FIELD);
        }

        Credential credential = CredentialUtils.getCredential(session);
        Integer memId = credential.getId();

        model.setMemberId(memId);
        model.setTime(new Date());
        reService.addComment(model);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 删除回复
     * @param reviewId
     * @return
     */
    @RequestMapping(value = "/reply/{reviewId}", method = RequestMethod.DELETE, produces = Constants.Produce.JSON)
    public ResponseText reply(@PathVariable("reviewId") Integer reviewId,
                              HttpSession session) {
        // 验证是不是自己发的评论
        Credential credential = CredentialUtils.getCredential(session);
        ReviewModel re = reService.getById(reviewId);
        if (false == re.getMemberId().equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }


        reService.deleteReview(reviewId);
        return ResponseText.getSuccessResponseText();
    }

}
