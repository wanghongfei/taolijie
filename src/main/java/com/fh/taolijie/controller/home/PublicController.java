package com.fh.taolijie.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wynfrith on 15-5-17.
 */
@Controller
@RequestMapping("/")
public class PublicController {

    @RequestMapping(value = "/", method = {RequestMethod.GET,
            RequestMethod.HEAD})
    public String home(){
        return "";
    }

}
