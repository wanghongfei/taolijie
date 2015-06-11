package com.fh.taolijie.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wynfrith on 15-6-11.
 */
@RequestMapping(value = "/user")
@Controller
public class UIndexController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String index(){
        return "user page";
    }
}
