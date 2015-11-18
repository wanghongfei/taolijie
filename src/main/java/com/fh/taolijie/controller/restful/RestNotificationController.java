package com.fh.taolijie.controller.restful;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.noti.PrivateNotificationModel;
import com.fh.taolijie.domain.noti.SysNotificationModel;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.SessionUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by whf on 7/5/15.
 */
@RestController
@RequestMapping(value = "/api/noti")
public class RestNotificationController {
    @Autowired
    NotificationService notiService;

    @Autowired
    AccountService accService;


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
     * 查询当前用户未读个人通知
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
     * 查询当前未读系统通知
     * @return
     */
    @RequestMapping(value = "/sys/unread", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    public ResponseText getUnReadSysNotification(@RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                                                 HttpServletRequest req) {
        // login check
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 先查出当前用户已读的系统通知id
        MemberModel mem = accService.findMember(credential.getId());
        String readStringList = mem.getReadSysNotificationIds();

        List<Integer> readList = new ArrayList<>(1);
        if (true == StringUtils.checkNotEmpty(readStringList)) {
            // 分隔id, 并转换成List对象
            String[] readStrings = readStringList.split(Constants.DELIMITER);
            if (null != readStringList && 0 != readStringList.length()) {
                try {
                    readList = Stream.of(readStrings)
                            .filter(i-> !"".equals(i))
                            .map(id -> Integer.valueOf(id) )
                            .collect(Collectors.toList());

                } catch (NumberFormatException ex) {
                    return new ResponseText(ErrorCode.BAD_NUMBER);
                }
            }

        }


        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);

        ListResult<SysNotificationModel> list = null;
        if (readList.isEmpty()) {
            list = notiService.getSysNotification(
                    credential.getId(),
                    Arrays.asList(credential.getRoleList().get(0),
                            Constants.NotificationRange.GLOBAL.toString()),
                    pageNumber,
                    pageSize);
        } else {
            list = notiService.getUnreadSysNotification(
                    readList,
                    Arrays.asList(
                            Constants.NotificationRange.GLOBAL.toString(),
                            credential.getRoleList().get(0)),
                    pageNumber, pageSize);
        }

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

        ListResult<SysNotificationModel> list = notiService.getSysNotification(
                memberId,
                Arrays.asList(roleName,
                        Constants.NotificationRange.GLOBAL.toString()),
                pageNumber,
                pageSize);
        return new ResponseText(list);
    }


    /**
     * 标记个人通知为已读
     * @return
     */
    @RequestMapping(value = "/pri/mark", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText markPriAsRead(@RequestParam("notiId") String notiIds,
                                      HttpServletRequest req) {
        // 登陆检查
        Credential credential = SessionUtils.getCredential(req);
        if (null == credential) {
            return new ResponseText(ErrorCode.NOT_LOGGED_IN);
        }

        // 分离id
        String[] ids = StringUtils.splitIds(notiIds);
        // 参数合法性检查
        if (null == ids || 0 == ids.length) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // String数组转换成Integer List
        List<Integer> idList = null;
        try {
            idList = Stream.of(ids)
                    .map( id -> Integer.valueOf(id))
                    .collect(Collectors.toList());
        } catch (NumberFormatException ex) {
            // 转换成整数失败
            // 说明参数非法
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        // 查出这些id代表的通知
        List<PrivateNotificationModel> notiList = notiService.findPriByIdInBatch(idList);
        if (notiList.isEmpty()) {
            return new ResponseText(ErrorCode.NOT_FOUND);
        }

        // 检查这些通知是不是都是发给自己的
        Integer memId = credential.getId();
        Optional<PrivateNotificationModel> opt = notiList.stream()
                .filter(noti -> !noti.getToMemberId().equals(memId))
                .findAny();
        // 如果存在，则返回错误
        if (opt.isPresent()) {
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }

        // 批量标记已读
        notiService.markPriAsReadInBatch(idList);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 标记系统通知为已读, 支持批量
     * @return
     */
    @RequestMapping(value = "/sys/mark", method = RequestMethod.PUT, produces = Constants.Produce.JSON)
    public ResponseText markSysAsRead(@RequestParam("notiId") String notiIdString,
                                      HttpServletRequest req) throws GeneralCheckedException {
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

        List<Integer> idList = Arrays.stream(idStrs)
                .map( id -> Integer.valueOf(id) )
                .collect(Collectors.toList());

        // 批量标记为已读
        notiService.markSysAsReadInBatch(credential.getId(), idList);

        return ResponseText.getSuccessResponseText();
    }
}
