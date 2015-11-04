package com.fh.taolijie.servlet;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by whf on 10/11/15.
 */
public class XSSWrapper extends HttpServletRequestWrapper {
    public XSSWrapper(HttpServletRequest req) {
        super(req);
    }

    /**
     * 过虑参数名相同的参数
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] originalParam = super.getParameterValues(name);
        if (null == originalParam) {
            return null;
        }

        int LEN = originalParam.length;
        for (int ix = 0 ; ix < LEN ; ++ix) {
            originalParam[ix] = StringEscapeUtils.escapeXml10(originalParam[ix]);
        }

        return originalParam;
    }

    /**
     * 过虑单个参数
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String originalParam = super.getParameter(name);
        if (null == originalParam) {
            return null;
        }

        return StringEscapeUtils.escapeXml10(originalParam);
    }
}
