package com.fh.taolijie.controller.restful;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.dao.mapper.JobPostModelMapper;
import com.fh.taolijie.domain.PrivateNotificationModel;
import com.fh.taolijie.domain.SysNotificationModel;
import com.fh.taolijie.service.JobPostService;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * Created by whf on 7/5/15.
 */
@RestController
@RequestMapping(value = "/api/noti")
public class RestNotificationController {
    @Autowired
    NotificationService notiService;


    /**
     * 查询全部个人通知
     * @return
     */
    @RequestMapping(value = "/pri", produces = Constants.Produce.JSON)
    public ResponseText getPrivateNotification(@RequestParam("memberId") Integer memberId,
                                           @RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                           HttpServletRequest req) {
        // 检查memberId是否是当前用户
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential || false == memberId.equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }


        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<PrivateNotificationModel> list = notiService.getPriNotification(memberId, pageNumber, pageSize);

        return new ResponseText(list);
    }

    /**
     * 查询当前用户未读通知
     */
    @RequestMapping(value = "/pri/unread", produces = Constants.Produce.JSON)
    public ResponseText getUnReadPrivateNotification(@RequestParam("memberId") Integer memberId,
                                               @RequestParam(defaultValue = "0") int pageNumber,
                                               @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                               HttpServletRequest req) {
        // 检查memberId是否是当前用户
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential || false == memberId.equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<PrivateNotificationModel> list = notiService.getUnreadPriNotification(memberId, pageNumber, pageSize);

        return new ResponseText(list);
    }

    /**
     * 查询当前用户的系统通知
     * @return
     */
    @RequestMapping(value = "/sys", produces = Constants.Produce.JSON)
    public ResponseText getSysNotification(@RequestParam("memberId") Integer memberId,
                                           @RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                           HttpServletRequest req
                                           ) {
        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);

        // 得到当前用户的role
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        String roleName = credential.getRoleList().get(0);

        ListResult<SysNotificationModel> list = notiService.getSysNotification(memberId, Arrays.asList(roleName), pageNumber, pageSize);
        return new ResponseText(list);
    }


    /**
     * 标记个人通知为已读
     * @return
     */
    @RequestMapping(value = "/pri/mark", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText markPriAsRead(@RequestParam("notiId") Integer notiId,
                                      HttpServletRequest req) {
        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        // 检查通知是不是发给自己的
        PrivateNotificationModel noti = notiService.findPriById(notiId);
        if (null == noti || false == noti.getToMemberId().equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // mark notification as read
        notiService.markPriAsRead(notiId);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 标记系统通知为已读
     * @return
     */
    @RequestMapping(value = "/sys/mark", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText markSysAsRead(@RequestParam("notiId") Integer notiId,
                                      HttpServletRequest req) {
        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        // 检查通知是否存在
        SysNotificationModel noti = notiService.findSysById(notiId);
        if (null == noti) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }
        // 检查是不是发给自己的
        if (false == noti.getAccessRange().equals(credential.getRoleList().get(0))) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 标记已读
        notiService.markSysAsRead(credential.getId(), notiId);

        return ResponseText.getSuccessResponseText();
    }
}
