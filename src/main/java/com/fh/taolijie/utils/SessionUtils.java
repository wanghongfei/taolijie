package com.fh.taolijie.utils;

import cn.fh.security.credential.Credential;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by whf on 8/25/15.
 */
public class SessionUtils {
    private SessionUtils() {}

    public static Credential getCredential(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (null == session) {
            return null;
        }

        return (Credential) session.getAttribute(Credential.CREDENTIAL_CONTEXT_ATTRIBUTE);
    }
}
