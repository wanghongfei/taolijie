package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.credential.DefaultCredential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.utils.ResponseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用于测试一些不会用的功能
 * Created by wynfrith on 15-3-7.
 */
@Controller
public class TestController {

    /**
     * 用户登陆页面
     * @return
     */
    @RequestMapping("/test/login")
    public String login(){
        return "test/login";
    }

    /**
     * 登陆请求
     * @param mem
     * @param session
     * @return
     */
    @RequestMapping(value = "/test/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String login(GeneralMemberDto mem,
                        HttpSession session){
        JsonObject json = null;
        json = Json.createObjectBuilder()
                .add("result",true)
                .add("message","登陆成功")
                .build();

        Credential credential = new DefaultCredential(1,mem.getUsername(),"nickname",100);
        CredentialUtils.createCredential(session,credential);

        return json.toString();
    }


    /**
     * 管理员
     * @param session
     * @param model
     * @return
     */
   @RequestMapping(value = "/test/admin")
   public String admin(HttpSession session,Model model) {
        System.out.println(session);
        System.out.println(model);
        if(session!=null){
            Credential credential = CredentialUtils.getCredential(session);
            System.out.println("用户名 = = "+credential.getUsername());
            model.addAttribute("username",credential.getUsername());
        }
        else{
            System.out.println("session为空?");
        }
       return "test/admin";
   }

    @RequestMapping(value = "/hello/{id}")
   public String hello(@PathVariable String id,
                             HttpServletRequest req,
                             Model model){
       System.out.println("");
        model.addAttribute(id);

        return  "test/hello";
   }
}
