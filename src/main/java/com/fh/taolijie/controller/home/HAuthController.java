package com.fh.taolijie.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RegType;
import com.fh.taolijie.constant.RequestParamName;
import com.fh.taolijie.constant.certi.CertiStatus;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.domain.acc.RoleModel;
import com.fh.taolijie.dto.LoginDto;
import com.fh.taolijie.dto.LoginRespDto;
import com.fh.taolijie.dto.RegisterDto;
import com.fh.taolijie.exception.checked.DuplicatedUsernameException;
import com.fh.taolijie.exception.checked.PasswordIncorrectException;
import com.fh.taolijie.exception.checked.acc.UserInvalidException;
import com.fh.taolijie.exception.checked.acc.UserNotExistsException;
import com.fh.taolijie.exception.checked.code.SMSIntervalException;
import com.fh.taolijie.exception.checked.code.SMSVendorException;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.service.acc.impl.CodeService;
import com.fh.taolijie.service.acc.impl.SessionServ;
import com.fh.taolijie.utils.*;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wynfrith on 15-6-11.
 */
@Controller
public class HAuthController {
    private static Logger logger = LoggerFactory.getLogger(HAuthController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionServ sessionServ;

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
                              HttpServletRequest req,
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
        //String sid = accountService.createRedisSession(mem);
        String sid = sessionServ.createSession(mem);


/*        Credential credential = new TaolijieCredential(mem.getId(), mem.getUsername());
        credential.addRole(role.getRolename());

        // 身份信息放到request中
        req.setAttribute(Credential.CREDENTIAL_CONTEXT_ATTRIBUTE, credential);
        req.setAttribute("user", mem);
        req.setAttribute("role", role);*/

        Cookie nameCookie = new Cookie("un", mem.getUsername());
        nameCookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(30)); // 30天
        res.addCookie(nameCookie);
        Cookie sidCookie = new Cookie("sid", sid);
        sidCookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(30)); // 30天
        res.addCookie(sidCookie);

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

        LoginRespDto respDto = new LoginRespDto();
        respDto.setId(mem.getId());
        respDto.setSid(sid);
        respDto.setGender(mem.getGender());
        respDto.setNickname(mem.getName());
        respDto.setPhotoPath(mem.getProfilePhotoPath());
        respDto.setRole(mem.getRoleList().get(0).getRolename());
        respDto.setUsername(mem.getUsername());
        return new ResponseText(respDto);
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
    public @ResponseBody ResponseText AdminLogin(@Valid LoginDto login,
                      BindingResult result,
                      HttpServletRequest req,
                      HttpServletResponse res) {

        int cookieExpireTime = (int) TimeUnit.DAYS.toSeconds(1); // 一天

        if (result.hasErrors()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        /*验证登陆*/
        try {
            accountService.login(login.getUsername(), login.getPassword());

        } catch (UserNotExistsException e) {
            return new ResponseText(ErrorCode.USER_NOT_EXIST);

        } catch (PasswordIncorrectException e) {
            return new ResponseText(ErrorCode.BAD_PASSWORD);

        } catch (UserInvalidException e) {
            return new ResponseText(ErrorCode.USER_INVALID);
        }

        /*获取用户信息和用户权限*/
        MemberModel mem = accountService.findMember(login.getUsername(), true);
        // 如果不是管理员用户
        if ( false == mem.getRoleList().stream().anyMatch( role -> role.getRolename().equals("ADMIN") )) {
            // 返回权限错误
            return new ResponseText(ErrorCode.PERMISSION_ERROR);
        }


        //String sid = accountService.createRedisSession(mem);
        String sid = sessionServ.createSession(mem);


        Cookie nameCookie = new Cookie("un", mem.getUsername());
        nameCookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(30)); // 30天
        nameCookie.setPath("/");
        res.addCookie(nameCookie);
        Cookie sidCookie = new Cookie("sid", sid);
        sidCookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(30)); // 30天
        sidCookie.setPath("/");
        res.addCookie(sidCookie);

         /*如果选择自动登陆,加入cookie*/
        if (login.getRememberMe().equals("true")) {
            Cookie usernameCookie = new Cookie("un", login.getUsername());
            usernameCookie.setMaxAge(cookieExpireTime);
            res.addCookie(usernameCookie);
        }

        LoginRespDto respDto = new LoginRespDto();
        respDto.setId(mem.getId());
        respDto.setSid(sid);
        respDto.setGender(mem.getGender());
        respDto.setNickname(mem.getName());
        respDto.setPhotoPath(mem.getProfilePhotoPath());
        respDto.setRole(mem.getRoleList().get(0).getRolename());
        respDto.setUsername(mem.getUsername());
        return new ResponseText(respDto);
    }

    /**
     * 用户注销 post
     */

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(@RequestParam(required = false, value = "m") String m,
                         @RequestParam(required = false, value = "appToken") String appToken,
                         HttpServletRequest req,
                         HttpServletResponse resp) throws IOException {

        // 删除cookie
        SessionUtils.logout(resp);


        // 删除Redis中的session
        Credential credential = SessionUtils.getCredential(req);
        String sid = SessionUtils.getFromCookie(req, RequestParamName.SESSION_ID.toString());
        //accountService.deleteRedisSession(sid);
        sessionServ.deleteSession(sid);

        // 判断是否是app
        if (null != m && m.equals(Constants.CLIENT_MOBILE)) {

            // 删除appToken
            accountService.updateAppToken(credential.getId(), null);

            ResponseText rt = ResponseText.getSuccessResponseText();
            String json = JSON.toJSONString(rt);
            resp.getOutputStream().write(json.getBytes());
            resp.getOutputStream().flush();
            return "";
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
    public @ResponseBody ResponseText register(@Valid RegisterDto registerDto,
                                         BindingResult result,
                                         @RequestParam Integer regType,
                                         @RequestParam(required = false) String code,
                                         HttpServletResponse res) {

        // TODO: 需要验证邮箱的唯一性
        //验证表单错误
        if (result.hasErrors()) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        RegType type = RegType.fromCode(regType);
        if (null == type) {
            return new ResponseText(ErrorCode.INVALID_PARAMETER);
        }

        //两次密码不一致
        if (!(registerDto.getPassword().equals(registerDto.getRePassword()))) {
            return new ResponseText(ErrorCode.RE_PASSWORD_ERROR);
        }

        // 创建model对象
        MemberModel mem = new MemberModel();
        mem.setUsername(registerDto.getUsername());
        mem.setName(registerDto.getNickname());
        mem.setPassword(CredentialUtils.sha(registerDto.getPassword()));
        mem.setValid(true);
        mem.setIdCerti(CertiStatus.NOT_YET.code());
        mem.setCreatedTime(new Date());
        mem.setProfilePhotoPath(registerDto.getPhotoPath());
        mem.setRegType(RegType.USERNAME.code());

        // 手机号注册
        if (type == RegType.MOBILE) {
            // 昵称必填
            if (null == registerDto.getNickname()) {
                return new ResponseText(ErrorCode.BAD_USERNAME);
            }

            // 参数code和identifier不能为空字符串
            if (!StringUtils.checkNotEmpty(code)) {
                return new ResponseText(ErrorCode.INVALID_PARAMETER);
            }

            // 验证验证码
            if (!codeService.validateSMSCode(mem.getUsername(), code)) {
                return new ResponseText(ErrorCode.VALIDATION_CODE_ERROR);
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
            return new ResponseText(ErrorCode.USER_EXIST);
        }

        return ResponseText.getSuccessResponseText();
    }




    /**
     * 向指定手机号发送短信
     * @return
     */
    @RequestMapping(value = "/register/sms", method = RequestMethod.GET, produces = Constants.Produce.JSON)
    @ResponseBody
    public ResponseText sendSMSAtRegistration(@RequestParam String mobile,
                                              @RequestParam(defaultValue = "0") int reg, // 0: 注册使用, 1:找回密码
                                              HttpServletRequest req) {


        if (0 == reg) {
            // 检查手机号用户名是否重复
            if (true == accountService.checkUsernameExist(mobile)) {
                return new ResponseText(ErrorCode.USER_EXIST);
            }

            logger.info("send SMS for registration");
        } else {
            logger.info("send SMS for retrieving password");
        }

        try {
            codeService.genSMSValidationCode(mobile, mobile);

        } catch (SMSIntervalException e) {
            return new ResponseText(ErrorCode.TOO_FREQUENT);

        } catch (SMSVendorException e) {
            return new ResponseText(ErrorCode.SMS_INVOKE_FAILED);
        }

        return ResponseText.getSuccessResponseText();
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
                                HttpServletRequest req) {

        logger.info("user {} tried to retrieve password", mobile);

        // 验证验证码
        if (!codeService.validateSMSCode(mobile, code)) {
            logger.info("user {}: code validation failed. input = {}", mobile, code);
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

        logger.info("user {} succeeded in retrieving password", mobile);

        return ResponseText.getSuccessResponseText();

    }
}
