package com.fh.taolijie.controller.admin;

import com.fh.taolijie.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wynfrith on 15-6-11.
 */

@Controller
@RequestMapping("manage/note")
public class ANotifyController {

    @Autowired
    NotificationService noteService;

    @RequestMapping(value = "send", method = RequestMethod.GET)
    public String send(){

        return "pc/admin/sendnote";
    }
}
