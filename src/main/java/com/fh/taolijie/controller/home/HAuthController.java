package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.domain.RoleModel;
import com.fh.taolijie.dto.LoginDto;
import com.fh.taolijie.dto.RegisterDto;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserInvalidException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.TaolijieCredential;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.relation.Role;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
public class HAuthController {

    @Autowired
    AccountService accountService;


    /**
     * 登陆页面
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest req) {
        return "pc/login";
    }


    /**
     * ajax登陆请求
     *
     * @param result
     * @param session
     * @param res
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    //region 登陆请求 login
    public
    @ResponseBody
    String login(@Valid LoginDto loginDto,
                 BindingResult result,
                 HttpSession session,
                 HttpServletResponse res) {

        int cookieExpireTime = 1 * 24 * 60 * 60;//1天

        /*验证用户信息*/
        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }

        /*验证用户是否存在*/
        try {
            accountService.login(loginDto.getUsername(), loginDto.getPassword());
        } catch (UserNotExistsException e) {
            return new JsonWrapper(false, e.getMessage()).getAjaxMessage();
        } catch (PasswordIncorrectException e) {
            return new JsonWrapper(false, e.getMessage()).getAjaxMessage();
        } catch (UserInvalidException e) {
            return new JsonWrapper(false, e.getMessage()).getAjaxMessage();
        }

        /*获取用户信息和用户权限*/

        MemberModel mem = accountService.findMember(loginDto.getUsername(), true);
        RoleModel role = mem.getRoleList().iterator().next();
        Credential credential = new TaolijieCredential(mem.getId(), mem.getUsername());
        credential.addRole(role.getRolename());

        //验证身份的session
        CredentialUtils.createCredential(session, credential);
        session.setAttribute("user", mem);
        session.setAttribute("role",role);


        /*如果选择自动登陆,加入cookie*/
        if (loginDto.getRememberMe().equals("true")) {
            Cookie usernameCookie = new Cookie("username", mem.getUsername());
            usernameCookie.setMaxAge(cookieExpireTime);
            Cookie passwordCookie = new Cookie("password", mem.getPassword());
            passwordCookie.setMaxAge(cookieExpireTime);
            res.addCookie(usernameCookie);
            res.addCookie(passwordCookie);
        }

        /*如果自动登陆为true ,返回cookie*/
        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion


    /**
     * 后台登陆页面
     */
    @RequestMapping(value = "login/admin", method = RequestMethod.GET)
    public String AdminLogin() {
        return "pc/admin/login";
    }



    /**
     * 管理员后台登陆
     */
    @RequestMapping(value = "login/admin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    //region 管理员后台登陆 AdminLogin

    public
    @ResponseBody
    String AdminLogin(@Valid LoginDto login,
                      BindingResult result,
                      HttpSession session,
                      HttpServletResponse res) {

        System.out.println("后台管理员登陆:::");
        System.out.println(login.getUsername());
        System.out.println(login.getRememberMe());

        int cookieExpireTime = 1 * 24 * 60 * 60;//1天

        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }

        /*验证登陆*/
        try {
            accountService.login(login.getUsername(), login.getPassword());
        } catch (UserNotExistsException e) {
            return new JsonWrapper(false, e.getMessage()).getAjaxMessage();
        } catch (PasswordIncorrectException e) {
            return new JsonWrapper(false, e.getMessage()).getAjaxMessage();
        } catch (UserInvalidException e) {
            return new JsonWrapper(false, e.getMessage()).getAjaxMessage();
        }

        /*获取用户信息和用户权限*/
        MemberModel mem = accountService.findMember(login.getUsername(), true);
        RoleModel role = mem.getRoleList().iterator().next();
        Credential credential = new TaolijieCredential(mem.getId(), mem.getUsername());
        credential.addRole(role.getRolename());

        CredentialUtils.createCredential(session, credential);
         /*如果选择自动登陆,加入cookie*/
        if (login.getRememberMe().equals("true")) {
            Cookie usernameCookie = new Cookie("username", login.getUsername());
            usernameCookie.setMaxAge(cookieExpireTime);
            Cookie passwordCookie = new Cookie("password", login.getPassword());
            passwordCookie.setMaxAge(cookieExpireTime);
            res.addCookie(usernameCookie);
            res.addCookie(passwordCookie);
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion

    /**
     * 用户注销 post
     */

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }


    /**
     * 注册页面
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest req) {
        return "pc/register";
    }



    /**
     * 注册用户
     * Method : POST AJAX
     *
     * @param result
     * @param session
     * @param res
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    //region 注册用户 public @ResponseBody String register
    public
    @ResponseBody
    String register(@Valid RegisterDto registerDto,
                    BindingResult result,
                    HttpSession session,
                    HttpServletResponse res) {
        int DEFAULT_INM_ID = 11;

        // TODO: 需要验证邮箱的唯一性
        //验证表单错误
        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }
        //两次密码不一致
        if (!(registerDto.getPassword().equals(registerDto.getRePassword()))) {
            return new JsonWrapper(false, Constants.ErrorType.REPASSWORD_ERROR).getAjaxMessage();
        }

        //注册不同权限的账户
        //1.根据权限的名称找到对应的权限id,如果没有找到,返回false
        //2.创建一个用户
        //3.为该账户添加权限, assignRole方法
        String roleName = registerDto.isEmployer() ?
                Constants.RoleType.EMPLOYER.toString() :
                Constants.RoleType.STUDENT.toString();
        RoleModel role = accountService.findRoleByName(roleName);
        //没有role自动创建role
        if(role == null){
            RoleModel newRole = new RoleModel();
            newRole.setRolename(roleName);
            newRole.setMemo("未配置名称");
            accountService.addRole(newRole);

            role = accountService.findRoleByName(roleName);
        }



        MemberModel mem = new MemberModel();
        mem.setUsername(registerDto.getUsername());
        mem.setPassword(CredentialUtils.sha(registerDto.getPassword()));
        mem.setValid(true);
        mem.setCreatedTime(new Date());
        mem.setRoleList(Arrays.asList(role));
        mem.setProfilePhotoId(DEFAULT_INM_ID); // 默认用户头像,更改直接换图片id

        //注册并且检查用户名是否存在
        try {
            accountService.register(mem);
        } catch (DuplicatedUsernameException e) {
            return new JsonWrapper(false, e.getMessage()).getAjaxMessage();
        }

        return new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
    }
    //endregion


}
