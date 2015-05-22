package com.fh.taolijie.test.controller.home;

import cn.fh.security.credential.Credential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.controller.AdminController;
import com.fh.taolijie.controller.dto.GeneralMemberDto;
import com.fh.taolijie.controller.dto.RoleDto;
import com.fh.taolijie.controller.home.AuthController;
import com.fh.taolijie.exception.checked.UserNotExistsException;
import com.fh.taolijie.service.impl.*;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.TaolijieCredential;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;


/**
 * Created by wynfrith on 15-5-17.
 */

@WebAppConfiguration
@ContextConfiguration(classes = {
        AuthController.class,
        DefaultAccountService.class,
        Mail.class,
        JavaMailSenderImpl.class})

public class AuthControllerTest extends BaseSpringDataTestClass {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    AuthController authController;
    @Autowired
    MockHttpSession session;
    MockMvc mockMvc;

    @Before
    public void before() {
        if (mockMvc == null)
            mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @After
    public void after() {

    }


    /**
     * 正常访问登陆界面
     * @throws Exception
     */
    @Test
    //region testLoginGet()
    public void testLoginGet() throws Exception {
        mockMvc.perform(get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("pc/login"))
                .andReturn();
    }
    //endregion


    /**
     * 测试在已登陆的情况下访问登陆页面
     * @throws Exception
     */
    @Test
    //region testLoginGETAndSession()
    public void testLoginGETAndSession() throws Exception {
        // TODO: 在程序中创建数据字段
        //首先在登陆的情况下
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","wynfrith")
                .param("password","wfc5582563")
                .param("rememberMe","false")
                .session(session))
                .andReturn();
        //带着session去访问login页面
        mockMvc.perform(get("/login")
                .session(session))
                .andExpect(status().isFound())
                .andReturn();
    }
    //endregion


    /**
     * 登陆成功
     * @throws Exception
     */
    @Test
    //region testLoginPost()
    public void testLoginPost() throws  Exception{
        String str = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();

        //1.没有cookie
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","wynfrith")
                .param("password","wfc5582563")
                .param("rememberMe","false")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(str))
                .andReturn();
        //2.添加cookie
        int cookieExpireTime = 3*24*60*60;
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "wynfrith")
                .param("password", "wfc5582563")
                .param("rememberMe", "true")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(str))
                .andExpect(cookie().value("username", "wynfrith"))
                .andExpect(cookie().value("password", "wfc5582563"))
                .andExpect(cookie().maxAge("username", cookieExpireTime))
                .andExpect(cookie().maxAge("password", cookieExpireTime))
                .andReturn();
    }
    //endregion


    /**
     * 登陆错误
     */
    @Test
    //region testLoginPostFailed()
    public void testLoginPostFailed() throws Exception{
        // TODO: 以后可以添加BindingResult中的error
        String passwordError = new JsonWrapper(false,
                Constants.ErrorType.PASSWORD_ERROR).getAjaxMessage();;
        String userNotFound =  new JsonWrapper(false,
                Constants.ErrorType.USERNAME_NOT_EXISTS).getAjaxMessage();
        String  userInvalid =  new JsonWrapper(false,
                Constants.ErrorType.USER_INVALID_ERROR).getAjaxMessage();

        //1.用户不存在
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","wynfrith290347")
                .param("password","wfc5582563")
                .param("rememberMe","false")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(userNotFound))
                .andReturn();
        //2.密码错误
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","wynfrith")
                .param("password","888888888888888")
                .param("rememberMe","false")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(passwordError))
                .andReturn();
        //3.非法用户
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","validuser")
                .param("password","wfc558256")
                .param("rememberMe","false")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(userInvalid))
                .andReturn();

    }
    //endregion


    /**
     * 访问注册页面
     * @throws Exception
     */
    @Test
    //region testRegisterGet()
    public void testRegisterGet() throws Exception {
        mockMvc.perform(get("/register")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("pc/register"))
                .andReturn();
    }
    //endregion


    /**
     * 成功注册
     * @throws Exception
     */
    @Test
    //region testRegisterSuccess()
    public void testRegisterSuccess() throws Exception {
        String str = new JsonWrapper(true, Constants.ErrorType.SUCCESS).getAjaxMessage();
        //注册学生用户
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","testStudent")
                .param("password","wfc5582563")
                .param("repassword","wfc5582563")
                .param("isEmployer","false")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(str))
                .andReturn();
        //注册商家用户
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","testEmployer")
                .param("password","wfc5582563")
                .param("repassword","wfc5582563")
                .param("isEmployer","true")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(str))
                .andReturn();
    }
    //endregion


    /**
     * 测试登陆失败
     * @throws Exception
     */
    @Test
    //region testRegisterError()
    public void testRegisterError() throws Exception {
        String userExists= new JsonWrapper(false, Constants.ErrorType.USERNAME_EXISTS).getAjaxMessage();
        String repasswordError= new JsonWrapper(false, Constants.ErrorType.REPASSWORD_ERROR).getAjaxMessage();
        String roleError= new JsonWrapper(false, Constants.ErrorType.ERROR).getAjaxMessage();

        //1 两次密码不一致
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","testEmployer")
                .param("password","wfc5582563")
                .param("repassword","888888888")
                .param("isEmployer","true")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(repasswordError))
                .andReturn();

        //2 用户名以存在
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","wynfrith")
                .param("password","wfc5582563")
                .param("repassword","wfc5582563")
                .param("isEmployer","true")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(userExists))
                .andReturn();
        //3 用户类型不存在
        // 貌似没法测,应该不担心这个问题
    }
    //endregion


    /**
     * 注销
     * @throws Exception
     */
    @Test
    //region testLogout()
    public void testLogout() throws Exception {

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "wynfrith")
                .param("password", "wfc5582563")
                .param("rememberMe", "false")
                .session(session))
                .andReturn();

        mockMvc.perform(get("/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(status().isOk())
                .andReturn();
    }
    //endregion
}
