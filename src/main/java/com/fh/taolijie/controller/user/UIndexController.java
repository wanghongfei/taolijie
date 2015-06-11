package com.fh.taolijie.controller.user;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.dto.ChangePasswordDto;
import com.fh.taolijie.dto.ProfileDto;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by wynfrith on 15-6-11.
 */
@RequestMapping(value = "/user")
@Controller
public class UIndexController {

    @Autowired
    AccountService accountService;
    @Autowired

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(){
        return "";
    }


    /**
     * 个人资料GET
     */
    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public String user(HttpSession session,Model model){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/login";
        }
        MemberModel memberDto = accountService.findMember(credential.getUsername(), false);
        model.addAttribute("user", memberDto);
//        long notifaicationNum = notificationService.getNotificationAmount(credential.getId(),false);
//        model.addAttribute("notificationNum",notifaicationNum);
        model.addAttribute("role",credential.getRoleList().iterator().next());

        return "pc/user/profile";

    }

    /**
     * 个人资料修改
     * @param session
     * @param profileDto
     * @return
     */
    //region 个人资料修改 profile
    @RequestMapping(value = "/profile", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public @ResponseBody String profile(HttpSession session,ProfileDto profileDto){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential==null){
            return "redirect:/login";
        }
        MemberModel user = accountService.findMember(credential.getId());
        user.setLastPostTime(new Date());
        user.setName(profileDto.getName());
        user.setGender(profileDto.getGender());

        accountService.updateMember(user);
        return  new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion



    /**
     * 安全信息(密码)
     * @param req
     * @return
     */
    @RequestMapping(value = "/setting/security", method = RequestMethod.GET)
    public String security(HttpServletRequest req){
        return "pc/user/security";
    }

    /**
     * 修改密码
     * @param session
     * @return
     */
    //region 修改密码 security
    @RequestMapping(value = "/setting/security",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody String security(@Valid ChangePasswordDto dto,
                                         BindingResult result,
                                         HttpSession session){

        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }

        String username = CredentialUtils.getCredential(session).getUsername();

        MemberModel mem = accountService.findMember(username, false);


        if(!mem.getPassword().equals(CredentialUtils.sha(dto.getOldPassword()))){
            System.out.println("用户的密码:"+mem.getPassword());
            System.out.println("输入的原密码:"+CredentialUtils.sha(dto.getOldPassword()));
            return new JsonWrapper(false, Constants.ErrorType.PASSWORD_ERROR).getAjaxMessage();
        }else if(!dto.getNewPassword().equals(dto.getRePassword())){
            return  new JsonWrapper(false, Constants.ErrorType.REPASSWORD_ERROR).getAjaxMessage();
        }

        //加密
        mem.setPassword(CredentialUtils.sha(dto.getNewPassword()));

        System.out.println("更新后的密码"+mem.getPassword());
        System.out.println(CredentialUtils.sha(mem.getPassword()));
        accountService.updateMember(mem);


        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion



}
