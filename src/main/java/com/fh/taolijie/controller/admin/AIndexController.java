package com.fh.taolijie.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Contended;

/**
 * Created by wynfrith on 15-6-11.
 */

@Controller
@RequestMapping(value = "admin")
public class AIndexController {

    @RequestMapping(value = {""},method = RequestMethod.GET)
    public String index(){
        return "pc/admin/index";
    }
}
