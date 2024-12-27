package com.tequeno.servlet;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class DemoServlet implements Servlet {

    private final static Logger log = LoggerFactory.getLogger(DemoServlet.class);

    private ServletConfig servletConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("DemoServlet init {}", config);
        servletConfig = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        log.info("DemoServlet service");
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

        PrintWriter writer = res.getWriter();
        writer.write("demo");
        writer.flush();
    }

    @Override
    public String getServletInfo() {
        return "DemoServlet";
    }

    @Override
    public void destroy() {
        log.info("DemoServlet destroy");
    }
}
