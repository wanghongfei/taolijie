package com.fh.taolijie.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by whf on 10/11/15.
 */
public class XSSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        XSSWrapper wrapper = new XSSWrapper((HttpServletRequest) request);
        filterChain.doFilter(wrapper, response);
    }

    @Override
    public void destroy() {

    }
}
