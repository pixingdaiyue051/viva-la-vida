package com.tequeno;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

public class DemoServlet implements Servlet {

    private final static Logger log = LoggerFactory.getLogger(DemoServlet.class);

    private ServletConfig conf;

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("demo init {}", config);
        conf = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return conf;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        log.info("demo service");

        PrintWriter writer = res.getWriter();
        writer.write("demo");
        writer.flush();
    }

    @Override
    public String getServletInfo() {
        return "demo";
    }

    @Override
    public void destroy() {
        log.info("demo destroy");
    }
}
