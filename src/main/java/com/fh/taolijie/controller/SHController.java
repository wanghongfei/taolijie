package com.fh.taolijie.controller;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.JobPostDto;
import com.fh.taolijie.controller.dto.SecondHandPostDto;
import com.fh.taolijie.service.*;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ControllerHelper;
import com.fh.taolijie.utils.ObjWrapper;
import com.fh.taolijie.utils.ResponseUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by wynfrith on 15-4-1.
 */
@Controller
@RequestMapping("/user/sh")
public class SHController{

    @Autowired
    SHPostService shPostService;
    @Autowired
    SHPostCategoryService shPostCategoryService;
    @Autowired
    AccountService accountService;

    /**
     * 发布二手
     * @param
     * @param req
     * @return
     */
    @RequestMapping(value = "/post" ,method = RequestMethod.GET)
    public String postSH(HttpSession session,
                      HttpServletRequest req){
      return "";
    }

    /**
     * 发布二手信息
     * @param shDto
     * @param session
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody
    String job(@Valid SecondHandPostDto shDto,
               BindingResult result,
               HttpSession session){
        GeneralMemberDto mem = null;

        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }
        String username = CredentialUtils.getCredential(session).getUsername();
        mem = accountService.findMember(username, new GeneralMemberDto[0], false);

        /*创建二手信息*/
        shDto.setMemberId(mem.getId());
        shDto.setPostTime(new Date());

        if(shDto.getCategoryId()!=null){
            shPostService.addPost(shDto);
        }else {
            return new JsonWrapper(false,Constants.ErrorType.PARAM_ILLEGAL).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 我的二手列表 GET
     * @param session 用户的角色
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String mySHList(HttpSession session){
        return "";
    }


    /**
     * 获取发布的二手列表 Ajax GET
     * @param page  页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "list/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String mySHList(@PathVariable int page,HttpSession session){
        int capcity = Constants.PAGE_CAPACITY;
        Credential credential = CredentialUtils.getCredential(session);

        GeneralMemberDto mem = accountService.findMember(credential.getUsername(), new GeneralMemberDto[0], false);

        List<SecondHandPostDto> list = shPostService.getPostList(mem.getId(),false,page-1,capcity,new ObjWrapper());
        return JSON.toJSONString(list);
    }




    /**
     * 获取已删除的二手列表 Ajax GET
     * @param page 页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "dellist/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String delList(@PathVariable int page,HttpSession session){
        return "";
    }


    /**
     * 获取带审核的二手列表 Ajax GET
     * @param page 页码数
     * @param session 用户的信息
     * @return
     */
    @RequestMapping(value = "auditlist/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String auditList(@PathVariable int page,HttpSession session){
        return "";
    }


    /**
     * 删除一条二手信息
     * @param id 兼职的id
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "del/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String del(@PathVariable int id,HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        SecondHandPostDto sh =shPostService.findPost(id);

        /*判断兼职信息是否由当前用户发布*/
        if(ControllerHelper.isCurrentUser(credential,sh)){
            return new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        /*删除*/

        if (!shPostService.deletePost(id)) {
            return new JsonWrapper(false, Constants.ErrorType.FAILED).getAjaxMessage();
        }
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }


    /**
     * 修改二手页面
     * @param id 传入一个二手所有信息
     * @param session 用户的信息
     * @param model
     * @return
     */
    @RequestMapping(value = "change",method = RequestMethod.POST)
    public String change(@RequestParam int id,HttpSession session,Model model){
        /**
         * 如果该job不是用户发送的,则返回404
         */
        Credential credential = CredentialUtils.getCredential(session);
        SecondHandPostDto sh=shPostService.findPost(id);
        if(sh == null|| ControllerHelper.isCurrentUser(credential,sh)){
            return "redirect:/404";
        }

        model.addAttribute("sh",sh);
        return "mobile/shdetail";
    }

    /**
     * 修改一条二手 ajax
     * @param sh 二手的dto对象
     * @param session  用户的信息
     * @return
     */
    @RequestMapping(value = "change",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String change(@Valid SecondHandPostDto sh,HttpSession session){
        /**
         * 如果该job不是用户发送的,则错误json
         */
        Credential credential = CredentialUtils.getCredential(session);

        if(sh == null|| ControllerHelper.isCurrentUser(credential,sh)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        if(!shPostService.updatePost(sh.getId(), sh)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 刷新二手 数据 ajax
     * 更新一下posttime
     * @param id 二手 id
     * @param session  用户的信息
     */
    @RequestMapping(value = "refresh/{id}",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String refresh(@PathVariable int id,HttpSession session){
        /**
         * 如果该sh不是用户发送的,则错误json
        */
        Credential credential = CredentialUtils.getCredential(session);
        SecondHandPostDto sh =shPostService.findPost(id);
        if(sh == null) {
            return new JsonWrapper(false, Constants.ErrorType.NOT_FOUND).getAjaxMessage();
        }

        if(ControllerHelper.isCurrentUser(credential,sh)){
            return  new JsonWrapper(false, Constants.ErrorType.PERMISSION_ERROR).getAjaxMessage();
        }

        sh.setPostTime(new Date());
        if(!shPostService.updatePost(sh.getId(),sh)){
            return new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }

    /**
     * 我收藏的二手 get
     * @param session
     * @return
     */
    @RequestMapping(value = "fav",method = RequestMethod.GET)
    public String fav(HttpSession session){
        return "";
    }
    /**
     * 我收藏的二手 ajax
     * @param  page
     * @param session
     * @return
     */
    @RequestMapping(value = "fav/{page}",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public @ResponseBody String fav(@PathVariable int page,HttpSession session){
        return "";
    }


}
