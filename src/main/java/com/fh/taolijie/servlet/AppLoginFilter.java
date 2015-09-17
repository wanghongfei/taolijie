package com.fh.taolijie.servlet;

import cn.fh.security.credential.Credential;
import cn.fh.security.credential.DefaultCredential;
import cn.fh.security.utils.CredentialUtils;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RequestParamName;
import com.fh.taolijie.domain.MemberModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.TaolijieCredential;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

/**
 * 用于app登陆的过虑器
 * Created by whf on 8/17/15.
 */
public class AppLoginFilter implements Filter, ApplicationContextAware {
    /**
     * 持有容器
     */
    private static ApplicationContext applicationContext;

    private static AccountService accountService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppLoginFilter.applicationContext = applicationContext;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // 判断是否是app请求
        String appToken = req.getParameter(RequestParamName.APP_TOKEN.toString());
        if (null != appToken) {
            if (null == accountService) {
                accountService = retrieveService("defaultAccountService");
            }

            // 根据appToken查询用户
            MemberModel mem = accountService.selectByAppToken(appToken);
            if (null == mem) {
                // 没查到说明用户已经退出了登陆
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                String respStr = new JsonWrapper(false, ErrorCode.NOT_LOGGED_IN)
                        .getAjaxMessage();
                // 返回错误JSON
                resp.getOutputStream().write(respStr.getBytes());
                resp.getOutputStream().flush();

                return;
            }

            // 如果查到了, 创建Credential到Session中
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval((int) TimeUnit.SECONDS.toSeconds(10)); // 5s
            session.setAttribute("user", mem);
            session.setAttribute("role", mem.getRoleList().get(0).getRolename());

            Credential credential = new DefaultCredential(mem.getId(), mem.getUsername());
            credential.addRole(mem.getRoleList().get(0).getRolename());

            CredentialUtils.createCredential(session, credential);
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    private AccountService retrieveService(String beanName) {
        if (null == accountService) {
            accountService = (AccountService) applicationContext.getBean(beanName);
        }

        return accountService;
    }

    @Override
    public void destroy() {

    }
}
