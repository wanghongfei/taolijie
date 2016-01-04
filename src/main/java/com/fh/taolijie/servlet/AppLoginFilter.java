package com.fh.taolijie.servlet;

import cn.fh.security.credential.Credential;
import cn.fh.security.credential.DefaultCredential;
import cn.fh.security.utils.CredentialUtils;
import com.alibaba.fastjson.JSON;
import com.fh.taolijie.component.ResponseText;
import com.fh.taolijie.constant.ErrorCode;
import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.constant.RequestParamName;
import com.fh.taolijie.domain.acc.MemberModel;
import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.StringUtils;
import com.fh.taolijie.utils.json.JsonWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用于登陆的过虑器
 * Created by whf on 8/17/15.
 */
public class AppLoginFilter implements Filter, ApplicationContextAware {
    private static final Logger infoLogger = LoggerFactory.getLogger(AppLoginFilter.class);

    /**
     * 持有容器
     */
    private static ApplicationContext applicationContext;

    private static AccountService accountService;

    private static JedisPool jedisPool;

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

        // 跳过静态资源
        if (StringUtils.isStaticResource(req.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // *************** DEBUG *******************

        HttpServletResponse response = (HttpServletResponse) servletResponse;
/*        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Origin", "*");*/
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, sid");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS ");
        response.setHeader("Access-Control-Allow-Origin", "http://cdn.taolijie.cn");
        // *************** DEBUG *******************

        // 先尝试通过sid登陆
        if (loginBySid(req)) {
            if (infoLogger.isDebugEnabled()) {
                infoLogger.debug("trying to log with sid succeeded");
            }
            filterChain.doFilter(servletRequest, servletResponse);
            return;

        }


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
                resp.setContentType(Constants.Produce.JSON);
                resp.getOutputStream().write(respStr.getBytes());
                resp.getOutputStream().flush();

                return;
            }

            // 用户信息放到request中
            req.setAttribute("user", mem);
            req.setAttribute("role", mem.getRoleList().get(0).getRolename());

            Credential credential = new DefaultCredential(mem.getId(), mem.getUsername());
            credential.addRole(mem.getRoleList().get(0).getRolename());

            req.setAttribute(Credential.CREDENTIAL_CONTEXT_ATTRIBUTE, credential);

            if (infoLogger.isDebugEnabled()) {
                infoLogger.debug("appToken[{}] login succeeded for user:{}", appToken, mem.getUsername());
            }
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 通过sid登陆
     * @param req
     * @return
     */
    private boolean loginBySid(HttpServletRequest req) {
        if (infoLogger.isDebugEnabled()) {
            infoLogger.debug("trying to log with sid...");
        }

        // 取出cookie中的sid
        String sid = null;
        Cookie cookie = findCookie(req.getCookies(), RequestParamName.SESSION_ID.toString());
        if (null != cookie) {
            sid = cookie.getValue();
            if (null == sid) {
                return false;
            }
        } else {
            // cookie中没有
            // 尝试从header中取
            sid = req.getHeader(RequestParamName.SESSION_ID.toString());
        }

        // cookie和session中都没有
        if (null == sid) {
            if (infoLogger.isDebugEnabled()) {
                infoLogger.debug("sid not found");
            }

            return false;
        }


        // 根据sid向redis中查询用户信息
        String key = RedisKey.SESSION.toString() + sid;

        JedisPool pool = retrieveRedis("jedisPool");
        Jedis jedis = JedisUtils.getClient(pool);
        Map<String, String> map = jedis.hgetAll(key);
        JedisUtils.returnJedis(pool, jedis);

        // 没查到表示已经过期或者未登陆
        if (null == map || map.isEmpty()) {
            if (infoLogger.isDebugEnabled()) {
                infoLogger.debug("trying to log with sid failed: no session found for key:{}", key);
            }
            return false;
        }

        // 查到
        // 取出用户信息放到request中
        String username = map.get("username");
        String role = map.get("role");
        String id = map.get("id");

        req.setAttribute("user", username);
        req.setAttribute("role", role);

        Credential credential = new DefaultCredential(Integer.valueOf(id), username);
        credential.addRole(role);

        req.setAttribute(Credential.CREDENTIAL_CONTEXT_ATTRIBUTE, credential);



        return true;
    }

    private Cookie findCookie(Cookie[] cookies, String name) {
        if (null == cookies) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }

        return null;
    }

    private AccountService retrieveService(String beanName) {
        if (null == accountService) {
            accountService = (AccountService) applicationContext.getBean(beanName);
        }

        return accountService;
    }

    private JedisPool retrieveRedis(String beanName) {
        if (null == jedisPool) {
            jedisPool = (JedisPool) applicationContext.getBean(beanName);
        }

        return jedisPool;
    }

    @Override
    public void destroy() {

    }
}
