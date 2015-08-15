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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 查询个人通知
     * @return
     */
    @RequestMapping(value = "/pri", produces = Constants.Produce.JSON)
    public ResponseText getPrivateNotification(@RequestParam("memberId") Integer memberId,
                                           @RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                           HttpSession session) {
        // 检查memberId是否是当前用户
        if (false == memberId.equals(CredentialUtils.getCredential(session).getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        List<PrivateNotificationModel> list = notiService.getPriNotification(memberId, pageNumber, pageSize)
                .getList();

        return new ResponseText(list);
    }

    /**
     * 查询当前用户的系统通知
     * @return
     */
    public ResponseText getSysNotification(@RequestParam("memberId") Integer memberId,
                                           @RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                           HttpSession session
                                           ) {
        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        // 得到当前用户的role
        Credential credential = CredentialUtils.getCredential(session);
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
                                      HttpSession session) {
        // 检查通知是不是发给自己的
        Credential credential = CredentialUtils.getCredential(session);
        PrivateNotificationModel noti = notiService.findPriById(notiId);
        if (null == noti || false == noti.getToMemberId().equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // mark notification as read
        notiService.markPriAsRead(notiId);

        return new ResponseText();
    }

    /**
     * 标记系统通知为已读
     * @return
     */
    @RequestMapping(value = "/sys/mark", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText markSysAsRead(@RequestParam("notiId") Integer notiId,
                                      HttpSession session) {
        Credential credential = CredentialUtils.getCredential(session);

        // 检查通知是否存在
        if (null == notiService.findSysById(notiId)) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // mark notification as read
        notiService.markSysAsRead(credential.getId(), notiId);

        return new ResponseText();
    }
}
