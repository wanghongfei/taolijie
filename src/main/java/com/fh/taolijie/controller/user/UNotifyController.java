package com.fh.taolijie.controller.user;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.domain.*;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        if(page < 0) page = 0;
        ListResult<PrivateNotificationModel> notes = noteService
                .getUnreadPriNotification(credential.getId(), PageUtils.getFirstResult(page, pageSize), pageSize);
        model.addAttribute("notes",notes.getList());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize",pageSize); //每一页的条数
        model.addAttribute("noteCounts",notes.getList().size()); //这页一共多少条数
        model.addAttribute("resultCount",notes.getResultCount());//所有的条数

        //判断有没有下一页
        // notes.size()!=pageSize && note.size()+(page+1)*pageSize != notes.

        return "pc/user/notify";
    }

    @RequestMapping(value = "sys", method = RequestMethod.GET)
    public String sysNotes(@RequestParam(defaultValue = "0") int page,
                              @RequestParam (defaultValue = Constants.PAGE_CAPACITY + "") int pageSize,
                              HttpSession session, Model model){

        Credential credential = CredentialUtils.getCredential(session);

        if(page < 0) page = 0;
        page = PageUtils.getFirstResult(page, pageSize);

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
        List<SysNotificationModel> notes = noteService
                .getSysNotification(credential.getId(),range, page, pageSize).getList();

        int pageStatus = 1;
        if(notes.size() == 0){
            pageStatus = 0;
        }else if(notes.size() == pageSize){
            pageStatus = 2;
        }

        model.addAttribute("pageStatus",pageStatus);
        model.addAttribute("notes",notes);
        model.addAttribute("page",page);

        return "pc/user/sysnotify";
    }
}
