package com.fh.taolijie.controller.restful.admin;

import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.NotiType;
import com.fh.taolijie.domain.noti.PrivateNotificationModel;
import com.fh.taolijie.domain.noti.SysNotificationModel;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * 创建、修改通知控制器。
 * 只有管理员能操作.
 *
 * <p>{@code /api/manage/noti}
 */
@RestController
@RequestMapping("/api/manage/noti")
public class RestNotificationAdminController {
    @Autowired
    NotificationService noService;


    /**
     * 管理员向用户发送个人通知
     * <p>{@code POST /pri}
     *
     * @param model
     */
    @RequestMapping(value = "/pri", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText addPriNotification(@Valid PrivateNotificationModel model,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseText(ErrorCode.EMPTY_FIELD);
        }

        model.setTime(new Date());
        model.setNotiType(NotiType.ADMIN.getCode());
        noService.addNotification(model);

        return ResponseText.getSuccessResponseText();
    }

    /**
     * 查询所有个人通知
     * <p>{@code GET /pri/list}
     *
     */
    @RequestMapping(value = "/pri/list", produces = Constants.Produce.JSON)
    public ResponseText findAllPriNotification(@RequestParam(defaultValue = "0") int pageNumber,
                                               @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {

        pageNumber = PageUtils.getFirstResult(pageNumber, pageSize);
        ListResult<PrivateNotificationModel> listResult = noService.getAllPriNotification(pageNumber, pageSize);
        return new ResponseText(listResult);

    }

    /**
     * 创建系统通知
     *
     * <p>{@code POST /sys}
     *
     * @return
     */
    @RequestMapping(value = "/sys", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public ResponseText addSysNotification(@Valid SysNotificationModel model,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseText(ErrorCode.EMPTY_FIELD);
        }

        model.setTime(new Date());
        noService.addNotification(model);


        return ResponseText.getSuccessResponseText();
    }

    /**
     * 查询所有系统通知
     * <p>{@code GET /sys/list}
     *
     */
    @RequestMapping(value = "/sys/list", produces = Constants.Produce.JSON)
    public ResponseText findAllSysNotification(@RequestParam(defaultValue = "0") int pageNumber,
                                               @RequestParam(defaultValue = Constants.PAGE_CAPACITY + "") int pageSize) {

        ListResult<SysNotificationModel> listResult = noService.getAllSysNotification(pageNumber, pageSize);
        return new ResponseText(listResult);

    }
}
