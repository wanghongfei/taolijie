package com.fh.taolijie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wynfrith on 15-3-31.
 */
@RequestMapping("/images")
@Controller
public class StaticController {


    @RequestMapping("/getpic")
    public String getPic(){
        return "assets/images/pic.jpg";
    }

    @RequestMapping("save")
    public @ResponseBody String save(){
        return "savepic";
    }


}
