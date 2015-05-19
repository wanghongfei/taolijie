package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.LoginDto;
import com.fh.taolijie.controller.dto.RegisterDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserInvalidException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ControllerHelper;
import com.fh.taolijie.utils.TaolijieCredential;
import com.fh.taolijie.utils.json.JsonWrapper;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by wynfrith on 15-5-16.
 * 权限操作控制器
 */
@Controller
@RequestMapping("/")
public class AuthController{
    @Autowired
    AccountService accountService;

    /**
     * 用户登陆页面
     * Method : GET
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    //region 登陆页面 String login(HttpSession session)
    public String login(HttpSession session){
        //如果已经登陆,直接跳转
        Credential credential = CredentialUtils.getCredential(session);
        if(credential != null)
            return "redirect:/";
        return "pc/login";
    }
    //endregion


    /**
     * 用户登陆验证
     * Method : POST AJAX
     * @param mem 登陆实体对象
     * @param result  错误信息
     * @param session
     * @param res
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    //region 用户登陆验证  @ResponseBody String login
    public @ResponseBody String login(@Valid LoginDto mem,
                                      BindingResult result,
                                      HttpSession session,
                                      HttpServletResponse res){
        int cookieExpireTime = 3*24*60*60;//3天

        //登陆验证
        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }

        //登陆方法
        // TODO : 添加一个可以通过邮箱登陆的方法
        try {
            accountService.login(mem.getUsername(),mem.getPassword());
        } catch (UserNotExistsException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        } catch (PasswordIncorrectException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        } catch (UserInvalidException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        }

        //验证成功获取用户的信息和权限保存到session
        GeneralMemberDto member = accountService.findMember(mem.getUsername(),new GeneralMemberDto[0],true);

        Credential credential = new TaolijieCredential(member.getId(),member.getUsername());
        RoleDto role = null;
        for(Integer rid:member.getRoleIdList()){
            role = accountService.findRole(rid);
            credential.addRole(role.getRolename());
            break;
        }
        CredentialUtils.createCredential(session,credential);

        //如果选择自动登陆,保存cookie
        if(mem.getRememberMe().equals("true")){
            Cookie usernameCookie = new Cookie("username", mem.getUsername());
            usernameCookie.setMaxAge(cookieExpireTime);
            Cookie passwordCookie = new Cookie("password", mem.getPassword());
            passwordCookie.setMaxAge(cookieExpireTime);
            res.addCookie(usernameCookie);
            res.addCookie(passwordCookie);
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion


    /**
     * 注册页面
     * Method : GET
     * @param session
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    //region 注册页面 register(HttpSession session)
    public String register(HttpSession session){
        Credential credential = CredentialUtils.getCredential(session);
        if(credential != null)
            return "redirect:/";
        return "pc/register";
    }
    //endregion


    /**
     * 注册用户
     * Method : POST AJAX
     * @param mem
     * @param result
     * @param session
     * @param res
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    //region 注册用户 public @ResponseBody String register
    public @ResponseBody String register(@Valid RegisterDto mem,
                                         BindingResult result,
                                         HttpSession session,
                                         HttpServletResponse res){
        // TODO: 需要验证邮箱的唯一性
        //验证表单错误
        if(result.hasErrors()){
            return new JsonWrapper(false,result.getAllErrors()).getAjaxMessage();
        }
        //两次密码不一致
        if(!(mem.getPassword().equals(mem.getRepassword()))){
            return new JsonWrapper(false,Constants.ErrorType.REPASSWORD_ERROR).getAjaxMessage();
        }

        //注册不同权限的账户
        //1.根据权限的名称找到对应的权限id,如果没有找到,返回false
        //2.创建一个用户
        //3.为该账户添加权限, assignRole方法
        String roleName = mem.getIsEmployer() ?
                Constants.RoleType.EMPLOYER.toString() :
                Constants.RoleType.STUDENT.toString();
        int roleId = ControllerHelper.getRoleId(roleName,accountService);
        if(roleId == -1)
            return new JsonWrapper(false,Constants.ErrorType.ERROR).getAjaxMessage();

        GeneralMemberDto newMember = new GeneralMemberDto();
        newMember.setUsername(mem.getUsername());
        newMember.setPassword(CredentialUtils.sha(mem.getPassword()));
        newMember.setValid(true);
        newMember.setCreated_time(new Date());
        newMember.setRoleIdList(Arrays.asList(roleId));
        //注册并且检查用户名是否存在
        try {
            accountService.register(newMember);
        } catch (DuplicatedUsernameException e) {
            return new JsonWrapper(false,e.getMessage()).getAjaxMessage();
        }

        //accountService.assignRole(roleId,mem.getUsername());

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion


    /**
     * 注销
     * 清空session
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET,
            produces = "application/json;charset=utf-8")
    //region 注销  @ResponseBody String logout
    public @ResponseBody String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
    //endregion


}
