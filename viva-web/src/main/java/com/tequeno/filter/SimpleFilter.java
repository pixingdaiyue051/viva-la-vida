package com.tequeno.filter;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SimpleFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(SimpleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("SimpleFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("SimpleFilter doFilter");
        servletResponse.setContentType("application/json;charset=utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("SimpleFilter destroy");
    }
}
