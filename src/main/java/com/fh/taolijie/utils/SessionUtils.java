package com.fh.taolijie.utils;

import cn.fh.security.credential.Credential;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
