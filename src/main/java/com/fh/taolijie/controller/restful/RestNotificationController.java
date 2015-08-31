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
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
     * 查询单条个人通知
     * @param req
     * @return
     */
    @RequestMapping(value = "/pri/{notiId}", produces = Constants.Produce.JSON)
    public ResponseText queryPriNotiById(@PathVariable("notiId") Integer notiId,
                                               HttpServletRequest req) {
        PrivateNotificationModel pri = notiService.findPriById(notiId);
        if (null == pri) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        Integer toMemberId = pri.getToMemberId();

        // 检查toMember是否当前用户
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential || false == toMemberId.equals(credential.getId())) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        return new ResponseText(pri);
    }

    /**
     * 查询单条系统通知
     * @param notiId
     * @param req
     * @return
     */
    @RequestMapping(value = "/sys/{notiId}", produces = Constants.Produce.JSON)
    public ResponseText querySysNotiById(@PathVariable("notiId") Integer notiId,
                                         HttpServletRequest req) {
        SysNotificationModel sys = notiService.findSysById(notiId);
        if (null == sys) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        String role = sys.getAccessRange();

        // 检查当前用户的role能否接收这个通知
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential || false == role.equals(credential.getRoleList().get(0))) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        return new ResponseText(sys);
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
     * 标记系统通知为已读, 支持批量
     * @return
     */
    @RequestMapping(value = "/sys/mark", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText markSysAsRead(@RequestParam("notiId") String notiIdString,
                                      HttpServletRequest req) {
        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        // 分隔id
        String[] idStrs = StringUtils.splitIds(notiIdString);
        // 合法性检查
        if (null == idStrs || 0 == idStrs.length) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        try {
            List<Integer> idList = new ArrayList<>(10);

            for (String idStr : idStrs) {
                Integer id = Integer.valueOf(idStr);

                // 检查通知是否存在
                SysNotificationModel noti = notiService.findSysById(id);
                if (null == noti) {
                    return new ResponseText(ErrorCode.NOT_FOUND);
                }
                // 检查是不是发给自己的
                if (false == noti.getAccessRange().equals(credential.getRoleList().get(0))) {
                    return new ResponseText(ErrorCode.PERMISSION_ERROR);
                }

                idList.add(id);
            }

            // 批量标记为已读
            notiService.markSysAsReadInBatch(credential.getId(), idList);

        } catch (NumberFormatException ex) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        return ResponseText.getSuccessResponseText();
    }
}
