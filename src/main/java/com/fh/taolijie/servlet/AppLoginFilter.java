package com.fh.taolijie.servlet;

import cn.fh.security.credential.Credential;
import cn.fh.security.credential.DefaultCredential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RequestParamName;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 用于app登陆的过虑器
 * Created by whf on 8/17/15.
 */
public class AppLoginFilter implements Filter, ApplicationContextAware {
    private static final Logger infoLogger = LogUtils.getInfoLogger();

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
        // 先从header中取token
        String appToken = req.getHeader(RequestParamName.HEADER_APP_TOKEN.toString());
        // 取不到再从请求参数中取
        if (null == appToken) {
            appToken = req.getParameter(RequestParamName.APP_TOKEN.toString());
        }

        // 是否是wechat端登陆
        String wechat = req.getParameter(RequestParamName.WECHAT_TOKEN.toString());
        if (null != appToken || null != wechat) {
            if (infoLogger.isDebugEnabled()) {
                infoLogger.debug("token found:{}", appToken == null ? wechat : appToken);
            }

            if (null == accountService) {
                accountService = retrieveService("defaultAccountService");
            }

            // 根据token查询用户
            MemberModel mem = null;
            if (null != appToken) {
                mem = accountService.selectByAppToken(appToken);
            } else if (null != wechat) {
                mem = accountService.selectByWechatToken(wechat);
            }

            if (null == mem) {
                if (infoLogger.isDebugEnabled()) {
                    infoLogger.debug("invalid appToken:{}", appToken);
                }

                // 没查到说明用户已经退出了登陆
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                String respStr = JSON.toJSONString(new ResponseText(ErrorCode.NOT_LOGGED_IN));
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

            if (infoLogger.isDebugEnabled()) {
                infoLogger.debug("appToken[{}] login succeeded for user:{}", appToken, mem.getUsername());
            }
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
