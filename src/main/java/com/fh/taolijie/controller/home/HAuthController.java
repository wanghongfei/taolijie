package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RegType;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.acc.RoleModel;
import com.fh.taolijie.dto.LoginDto;
import com.fh.taolijie.dto.LoginRespDto;
import com.fh.taolijie.dto.RegisterDto;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.UserInvalidException;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.acc.impl.CodeService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.StringUtils;
import com.fh.taolijie.utils.TaolijieCredential;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
public class HAuthController {
    private static Logger infoLog = LogUtils.getInfoLogger();

    @Autowired
    AccountService accountService;

    @Autowired
    private CodeService codeService;

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
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    @ResponseBody
    public ResponseText login(@Valid LoginDto loginDto,
                              BindingResult result,
                              @RequestParam(value = "m", required = false) String m,
                              HttpSession session,
                              HttpServletResponse res) {


        // 参数验证
        if (result.hasErrors()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        /*验证用户是否存在*/
        try {
            accountService.login(loginDto.getUsername(), loginDto.getPassword());
        } catch (UserNotExistsException e) {
            return new ResponseText(ErrorCode.USER_NOT_EXIST);

        } catch (PasswordIncorrectException e) {
            return new ResponseText(ErrorCode.BAD_PASSWORD);

        } catch (UserInvalidException e) {
            return new ResponseText(ErrorCode.USER_INVALID);

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

        // 用户选择了remember me
        if (loginDto.getRememberMe().equals("true")) {
            String token = StringUtils.randomString(15);
            mem.setLastTokenDate(new Date());
            mem.setAutoLoginIdentifier(token);
            accountService.updateMember(mem);

            // 将自动登陆用的token放到cookie中
            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setMaxAge((int)TimeUnit.DAYS.toSeconds(7)); // 7天
            res.addCookie(tokenCookie);

            // 将用户名放到cookie中
            // 过期时间为7天
            Cookie nameCookie = new Cookie("un", mem.getUsername());
            nameCookie.setMaxAge((int)TimeUnit.DAYS.toSeconds(7)); // 7天
            res.addCookie(nameCookie);

        } else {
            // 将用户名放到cookie中
            // 浏览器关闭就过期
            Cookie nameCookie = new Cookie("un", mem.getUsername());
            res.addCookie(nameCookie);
        }


        // 根据参数m判断是否是移动端
        if (null != m && m.equals(Constants.CLIENT_MOBILE)) {
            // 是移动端
            // 生成app token
            String appToken = RandomStringUtils.randomAlphabetic(20);
            // 写入数据库
            accountService.updateAppToken(mem.getId(), appToken);

            // 返回token给客户端
            return new ResponseText(new LoginRespDto(mem.getId(), appToken));
        }

        return ResponseText.getSuccessResponseText();
    }


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
    public @ResponseBody String AdminLogin(@Valid LoginDto login,
                      BindingResult result,
                      HttpSession session,
                      HttpServletResponse res) {

        int cookieExpireTime = (int) TimeUnit.DAYS.toSeconds(1); // 一天

        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }

        /*验证登陆*/
        try {
            accountService.login(login.getUsername(), login.getPassword());
        } catch (UserNotExistsException e) {
            return new JsonWrapper(false, ErrorCode.USER_NOT_EXIST).getAjaxMessage();
        } catch (PasswordIncorrectException e) {
            return new JsonWrapper(false, ErrorCode.BAD_PASSWORD).getAjaxMessage();
        } catch (UserInvalidException e) {
            return new JsonWrapper(false, ErrorCode.USER_INVALID).getAjaxMessage();
        }

        /*获取用户信息和用户权限*/
        MemberModel mem = accountService.findMember(login.getUsername(), true);
        RoleModel role = mem.getRoleList().iterator().next();

        Credential credential = new TaolijieCredential(mem.getId(), mem.getUsername());
        credential.addRole(role.getRolename());

        CredentialUtils.createCredential(session, credential);

         /*如果选择自动登陆,加入cookie*/
        if (login.getRememberMe().equals("true")) {
            Cookie usernameCookie = new Cookie("un", login.getUsername());
            usernameCookie.setMaxAge(cookieExpireTime);
            res.addCookie(usernameCookie);
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS).getAjaxMessage();
    }

    /**
     * 用户注销 post
     */

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(@RequestParam(required = false, value = "m") String m,
                         @RequestParam(required = false, value = "appToken") String appToken,
                         HttpServletResponse resp,
                         HttpSession session){
        session.invalidate();

        // 删除cookie
        Cookie co = new Cookie("token", "");
        co.setMaxAge(0);
        resp.addCookie(co);

        co = new Cookie("un", "");
        co.setMaxAge(0);
        resp.addCookie(co);

        // 判断是否是app
        if (null != m && m.equals(Constants.CLIENT_MOBILE)) {
            Credential credential = CredentialUtils.getCredential(session);

            // 删除appToken
            accountService.updateAppToken(credential.getId(), null);

            return new JsonWrapper(true, ErrorCode.SUCCESS)
                    .getAjaxMessage();
        }

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
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    public @ResponseBody String register(@Valid RegisterDto registerDto,
                                         BindingResult result,
                                         @RequestParam Integer regType,
                                         @RequestParam(required = false) String code,
                                         @RequestParam(required = false) String identifier,
                                         HttpServletResponse res) {

        // TODO: 需要验证邮箱的唯一性
        //验证表单错误
        if (result.hasErrors()) {
            return new JsonWrapper(false, result.getAllErrors()).getAjaxMessage();
        }

        RegType type = RegType.fromCode(regType);
        if (null == type) {
            return new JsonWrapper(false, ErrorCode.INVALID_PARAMETER).getAjaxMessage();
        }

        //两次密码不一致
        if (!(registerDto.getPassword().equals(registerDto.getRePassword()))) {
            return new JsonWrapper(false, ErrorCode.RE_PASSWORD_ERROR).getAjaxMessage();
        }

        // 创建model对象
        MemberModel mem = new MemberModel();
        mem.setUsername(registerDto.getUsername());
        mem.setName(registerDto.getNickname());
        mem.setPassword(CredentialUtils.sha(registerDto.getPassword()));
        mem.setValid(true);
        mem.setVerified(Constants.VerifyStatus.NONE.toString());
        mem.setCreatedTime(new Date());
        mem.setRegType(RegType.USERNAME.code());

        // 手机号注册
        if (type == RegType.MOBILE) {
            // 昵称必填
            if (null == registerDto.getNickname()) {
                return new JsonWrapper(false, ErrorCode.BAD_USERNAME).getAjaxMessage();
            }

            // 参数code和identifier不能为空字符串
            if (!StringUtils.checkNotEmpty(code) || !StringUtils.checkNotEmpty(identifier)) {
                return new JsonWrapper(false, ErrorCode.INVALID_PARAMETER).getAjaxMessage();
            }

            // 验证验证码
            if (!codeService.validateSMSCode(identifier, code)) {
                return new JsonWrapper(false, ErrorCode.VALIDATION_CODE_ERROR).getAjaxMessage();
            }

            mem.setPhone(registerDto.getUsername());
            mem.setRegType(RegType.MOBILE.code());
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



        mem.setRoleList(Arrays.asList(role));

        //注册并且检查用户名是否存在
        try {
            accountService.register(mem);
        } catch (DuplicatedUsernameException e) {
            return new JsonWrapper(false, ErrorCode.USER_EXIST)
                    .getAjaxMessage();
        }

        return new JsonWrapper(true, ErrorCode.SUCCESS)
                .getAjaxMessage();
    }


    /**
     * 绑定wechat appid
     * @return
     */
    @RequestMapping(value = "/register/bind/wechat", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    @ResponseBody
    public ResponseText bindWechatToken(@RequestParam String wechatToken,
                                        @RequestParam Integer memId) {
        MemberModel mem = new MemberModel();
        mem.setId(memId);
        mem.setWechatToken(wechatToken);

        int row = accountService.updateMember(mem);
        if (row <= 0) {
            return new ResponseText(ErrorCode.FAILED);
        }

        return ResponseText.getSuccessResponseText();
    }


    /**
     * (手机注册时使用)向指定手机号发送短信
     * @return
     */
    @RequestMapping(value = "/register/sms", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    @ResponseBody
    public ResponseText sendSMSAtRegistration(@RequestParam String mobile,
                                              HttpServletRequest req) {

        String randCode = RandomStringUtils.randomAlphabetic(15);
        if (infoLog.isDebugEnabled()) {
            infoLog.debug("identifier generated:{}", randCode);
        }

        String res = codeService.genSMSValidationCode(randCode, mobile);
        if (null == res) {
            return new ResponseText(ErrorCode.FAILED);
        }

        return new ResponseText(randCode);
    }

    /**
     * 通过手机找回密码
     * @return
     */
    @RequestMapping(value = "/findPwd", method = RequestMethod.POST, produces = Constants.Produce.JSON)
    @ResponseBody
    public ResponseText findPwd(@RequestParam String mobile,
                                @RequestParam String newPwd,
                                @RequestParam String code,
                                @RequestParam String identifier,
                                HttpServletRequest req) {

        // 验证验证码
        if (!codeService.validateSMSCode(identifier, code)) {
            return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
        }

        MemberModel mem = accountService.findMember(mobile, true);
        if (null == mem) {
            return new ResponseText(ErrorCode.INVALID_PHONE_NUMBER);
        }


        // 修改密码
        MemberModel example = new MemberModel();
        example.setId(mem.getId());
        example.setPassword(CredentialUtils.sha(newPwd));
        accountService.updateMember(example);

        return ResponseText.getSuccessResponseText();

    }
}
