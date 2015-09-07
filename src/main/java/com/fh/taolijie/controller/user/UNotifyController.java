package com.fh.taolijie.controller.user;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import com.fh.taolijie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by wynfrith on 15-6-11.
 */

@Controller
@RequestMapping("/user/notify")
public class UNotifyController {

    @Autowired
    NotificationService noteService;
    @Autowired
    AccountService accountService;


    /**
     * 获取个人消息
     * @param page
     * @param pageSize
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "pri", method = RequestMethod.GET)
    public String priNotes(@RequestParam(defaultValue = "0") int page,
                              @RequestParam (defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                              HttpSession session, Model model){

        Credential credential = CredentialUtils.getCredential(session);
        MemberModel mem = accountService.findMember(credential.getId());
        RoleModel role = mem.getRoleList().iterator().next();
        List<String> range = new ArrayList<>();
        if(role.getRolename().equals(Constants.RoleType.EMPLOYER.toString())){
            range.add(Constants.NotificationRange.EMPLOYER.toString());
        }
        else if(role.getRolename().equals(Constants.RoleType.STUDENT.toString())){
            range.add(Constants.NotificationRange.STUDENT.toString());
        }else{
            range.add(Constants.NotificationRange.EMPLOYER.toString());
            range.add(Constants.NotificationRange.STUDENT.toString());
        }

        if(page < 0) page = 0;
//
        ListResult<PrivateNotificationModel> notes = noteService
                .getPriNotification(credential.getId(), PageUtils.getFirstResult(page, pageSize), pageSize);

        /*获得个人未读数目*/
       long priUnreadCount  = noteService.
                getUnreadPriNotification(credential.getId(), PageUtils.getFirstResult(page, pageSize), pageSize).getResultCount();

        /*获取系统功能未读数目*/
        // 先查出当前用户已读的系统通知id
        String readStringList = mem.getReadSysNotificationIds();
        List<Integer> readList = new ArrayList<>(1);
        if (true == StringUtils.checkNotEmpty(readStringList)) {
            // 分隔id, 并转换成List对象
            String[] readStrings = readStringList.split(Constants.DELIMITER);
            if (null != readStringList && 0 != readStringList.length()) {
                try {
                    readList = Stream.of(readStrings)
                            .filter(i-> !"".equals(i))
                            .map( id -> Integer.valueOf(id) )
                            .collect(Collectors.toList());

                } catch (NumberFormatException ex) {
                    return "redirect:/404";
                }
            }

        }

       long sysUnreadCount = 0;
        if(readList.isEmpty()){
            sysUnreadCount = noteService.getSysNotification(credential.getId(), range,page, pageSize ).getResultCount();
        }else{
            sysUnreadCount = noteService.getUnreadSysNotification(readList, range, page, pageSize).getResultCount();
        }

        model.addAttribute("notes",notes.getList());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize",pageSize); //每一页的条数
        model.addAttribute("noteCounts",notes.getList().size()); //这页一共多少条数
        model.addAttribute("resultCount",notes.getResultCount());//所有的条数
        model.addAttribute("priUnreadCount", priUnreadCount);
        model.addAttribute("sysUnreadCount", sysUnreadCount);

        //判断有没有下一页
        // notes.size()!=pageSize && note.size()+(page+1)*pageSize != notes.

        return "pc/user/notify";
    }

    /*
    获取系统消息
     */
    @RequestMapping(value = "sys", method = RequestMethod.GET)
    public String sysNotes(@RequestParam(defaultValue = "0") int page,
                              @RequestParam (defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                              HttpSession session, Model model){

        Credential credential = CredentialUtils.getCredential(session);

        if(page < 0) page = 0;

        MemberModel mem = accountService.findMember(credential.getId());
        RoleModel role = mem.getRoleList().iterator().next();

        List<String> range = new ArrayList<>();
        if(role.getRolename().equals(Constants.RoleType.EMPLOYER.toString())){
            range.add(Constants.NotificationRange.EMPLOYER.toString());
        }
        else if(role.getRolename().equals(Constants.RoleType.STUDENT.toString())){
            range.add(Constants.NotificationRange.STUDENT.toString());
        }else{
            range.add(Constants.NotificationRange.EMPLOYER.toString());
            range.add(Constants.NotificationRange.STUDENT.toString());
        }

        long priCounts= noteService.
                getUnreadPriNotification(credential.getId(), PageUtils.getFirstResult(page, pageSize), pageSize).getResultCount();


        String readStringList = mem.getReadSysNotificationIds();
        List<Integer> readList = new ArrayList<>(1);
        if (true == StringUtils.checkNotEmpty(readStringList)) {
            // 分隔id, 并转换成List对象
            String[] readStrings = readStringList.split(Constants.DELIMITER);
            if (null != readStringList && 0 != readStringList.length()) {
                try {
                    readList = Stream.of(readStrings)
                            .filter(i -> !"".equals(i))
                            .map(id -> Integer.valueOf(id))
                            .collect(Collectors.toList());

                } catch (NumberFormatException ex) {
                    return "redirect:/404";
                }
            }
        }
        ListResult<SysNotificationModel> notes = noteService.getSysNotification(credential.getId(), range, PageUtils.getFirstResult(page, pageSize), pageSize);

        long sysCounts = 0;
        if(readList.isEmpty()){
            sysCounts = notes.getResultCount();
        }else{
             sysCounts= noteService
                .getUnreadSysNotification(readList,range, page, pageSize).getResultCount();
        }

        model.addAttribute("notes",notes.getList());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize",pageSize); //每一页的条数
        model.addAttribute("noteCounts",notes.getList().size()); //这页一共多少条数
        model.addAttribute("resultCount",notes.getResultCount());//所有的条数
        model.addAttribute("priUnreadCount",priCounts);
        model.addAttribute("sysUnreadCount",sysCounts);

        return "pc/user/sysnotify";
    }
}
