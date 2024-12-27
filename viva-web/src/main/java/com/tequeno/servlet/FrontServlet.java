package com.tequeno.servlet;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

public class FrontServlet extends GenericServlet {

    private static final long serialVersionUID = 8469266686553913672L;

    private final static Logger log = LoggerFactory.getLogger(DemoServlet.class);

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        log.info("FrontServlet service");

        PrintWriter writer = res.getWriter();
        writer.write("front");
        writer.flush();
    }
}
