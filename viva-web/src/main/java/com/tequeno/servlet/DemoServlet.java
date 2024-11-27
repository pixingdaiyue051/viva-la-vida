package com.tequeno.servlet;

import com.tequeno.handler.ResHandler;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "DemoServlet", urlPatterns = {"/demo", "/demo/*"})
@Slf4j
public class DemoServlet implements Servlet {

    private ServletConfig servletConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletConfig = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            ServletContext context = req.getServletContext();
            Enumeration<String> contextInitParameterNames = context.getInitParameterNames();
            while (contextInitParameterNames.hasMoreElements()) {
                String next = contextInitParameterNames.nextElement();
                log.info("context init-param {}[{}]", next, context.getInitParameter(next));
            }

            Enumeration<String> initParameterNames = servletConfig.getInitParameterNames();
            while (initParameterNames.hasMoreElements()) {
                String next = initParameterNames.nextElement();
                log.info("servlet init-param {}[{}]", next, servletConfig.getInitParameter(next));
            }

            ResHandler.getInstance().success(res);
        } catch (Exception e) {
            log.error("异常", e);
            ResHandler.getInstance().fail(res);
        }
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {
    }
}
