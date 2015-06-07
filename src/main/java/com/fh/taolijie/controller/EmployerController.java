package com.fh.taolijie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 商家用户特有的功能
 *
 * 权限 ： 商家 管理员
 * Created by wynfrith on 15-4-1.
 */

@Controller
@RequestMapping("/user/employer")
public class EmployerController {

    /**
     * 收藏的简历列表 get
     * @param session
     * @return
     */
    @RequestMapping(value = "fav",method = RequestMethod.GET)
    public String fav(HttpSession session){
        return  "";
    }

    /**
     * 收藏的简历列表 ajax
     * @param page
     * @param session
     * @return
     */
    @RequestMapping(value = "fav/{page}",method = RequestMethod.GET,produces = "application/json;chraset=utf-8")
    public @ResponseBody String fav(@PathVariable int page,HttpSession session){
        return  "";
    }


}
