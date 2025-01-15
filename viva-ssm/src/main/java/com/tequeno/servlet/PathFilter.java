package com.tequeno.servlet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebFilter(urlPatterns = "/*", asyncSupported = true)
@Slf4j
public class PathFilter extends HttpFilter {

    private static final long serialVersionUID = -3913587389223136667L;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.info("PathFilter doFilter");
        String uri = req.getRequestURI();
        String method = req.getMethod();
        log.info("uri[{}],method[{}]", uri, method);
        if ("/favicon.ico".equals(uri)) {
            return;
        }
        if ("OPTIONS".equals(method)) {
            return;
        }
        res.setContentType("application/json;charset=utf-8");
        chain.doFilter(req, res);
    }
}
