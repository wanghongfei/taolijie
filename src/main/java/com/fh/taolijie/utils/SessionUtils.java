package com.fh.taolijie.utils;

import cn.fh.security.credential.Credential;
import com.fh.taolijie.constant.RequestParamName;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by whf on 8/25/15.
 */
public class SessionUtils {
    private SessionUtils() {}

    public static Credential getCredential(HttpServletRequest req) {
        return (Credential) req.getAttribute(Credential.CREDENTIAL_CONTEXT_ATTRIBUTE);
    }

    /**
     * 将当前用户取消登陆. (清除Cookie)
     * @param resp
     */
    public static boolean logout(HttpServletResponse resp) {
        // 删除cookie
        Cookie co = new Cookie(RequestParamName.SESSION_ID.toString(), "");
        co.setMaxAge(0);
        resp.addCookie(co);
        co = new Cookie(RequestParamName.APP_TOKEN.toString(), "");
        co.setMaxAge(0);
        resp.addCookie(co);

        co = new Cookie(RequestParamName.USERNAME.toString(), "");
        co.setMaxAge(0);
        resp.addCookie(co);

        return true;
    }

    private static Cookie findSidCookie(Cookie[] cookies) {
        if (null == cookies) {
            return null;
        }

        int LEN = cookies.length;
        for (int ix = 0 ; ix < LEN ; ++ix) {
            Cookie cookie = cookies[ix];
            if (cookie.getName().endsWith(RequestParamName.SESSION_ID.toString())) {
                return cookie;
            }
        }

        return null;
    }

    /**
     * 判断是否是商家
     * @param credential
     * @return
     */
    public static boolean isEmployer(Credential credential) {
        return credential.getRoleList().get(0).equals(Constants.RoleType.EMPLOYER.toString());
    }

    /**
     * 是否是学生
     * @param credential
     * @return
     */
    public static boolean isStudent(Credential credential) {
        return credential.getRoleList().get(0).equals(Constants.RoleType.STUDENT.toString());
    }

    /**
     * 是否是管理员
     * @param credential
     * @return
     */
    public static boolean isAdmin(Credential credential) {
        return credential.getRoleList().get(0).equals(Constants.RoleType.ADMIN.toString());
    }

    /**
     * 从cookie中取出指定值, 如果没找到返回null
     * @param req
     * @param key
     * @return
     */
    public static String getFromCookie(HttpServletRequest req, String key) {
        Cookie[] cookies = req.getCookies();
        if (null == cookies) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
