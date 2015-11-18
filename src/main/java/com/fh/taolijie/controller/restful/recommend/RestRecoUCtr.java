package com.fh.taolijie.controller.restful.recommend;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RecoType;
import com.fh.taolijie.domain.RecoPostModel;
import com.fh.taolijie.exception.checked.*;
import com.fh.taolijie.exception.checked.acc.BalanceNotEnoughException;
import com.fh.taolijie.exception.checked.acc.CashAccNotExistsException;
import com.fh.taolijie.exception.checked.acc.OrderNotFoundException;
import com.fh.taolijie.service.RecoService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by whf on 10/15/15.
 */
@RestController
@RequestMapping("/api/user/re")
public class RestRecoUCtr {
    @Autowired
    private RecoService reService;

    /**
     * 申请一条置顶推荐
     * @return
     */
    @RequestMapping(value = "/top", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText addTop(@RequestParam int type,
                               @RequestParam Integer postId,
                               @RequestParam int days,
                               @RequestParam(required = false) Integer index,
                               @RequestParam(required = false) Integer orderId,
                            HttpServletRequest req) throws GeneralCheckedException {

        RecoType rt = RecoType.fromCode(type);
        if (null == rt) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        Credential credential = SessionUtils.getCredential(req);

        // 创建Model
        RecoPostModel model = new RecoPostModel();
        model.setType(rt.code());
        model.setPostId(postId);
        model.setMemberId(credential.getId());
        model.setHours(days);
        if (null != index) {
            model.setOrderIndex(index);
        }

        // 计算过期时间
        Date expire = TimeUtil.calculateDate(new Date(), Calendar.DAY_OF_MONTH, days);
        model.setExpiredTime(expire);

        reService.add(model, orderId);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 申请一条tag推荐
     * @return
     */
    @RequestMapping(value = "/tag", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText addTag(@RequestParam Integer type,
                               @RequestParam Integer postId,
                               @RequestParam int days,
                               @RequestParam(required = false) Integer index,
                               @RequestParam(required = false) Integer orderId,
                               HttpServletRequest req) throws GeneralCheckedException {

        // 检查type参数合法性
        RecoType rt = RecoType.fromCode(type);
        if (null == rt || rt != RecoType.QUEST) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        reService.addTag(postId, rt, days, orderId);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 查询我的申请
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText myApply(HttpServletRequest req,
                                @RequestParam(required = false) Integer type) {

        Credential credential = SessionUtils.getCredential(req);

        RecoPostModel cmd = new RecoPostModel();
        cmd.setMemberId(credential.getId());
        if (null != type) {
            RecoType rt = RecoType.fromCode(type);
            if (null == rt) {
                return new ResponseText(ErrorCode.INVALID_PARAMETER);
            }

            cmd.setType(rt.code());
        }
        cmd.setOrderByIndex(false);
        cmd.setOrderByTime(true);

        ListResult<RecoPostModel> lr = reService.findBy(cmd);
        return new ResponseText(lr);
    }
}
