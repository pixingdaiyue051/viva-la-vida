package com.tequeno.servlet;

import com.tequeno.handler.ResHandler;
import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet(name = "FrontServlet", urlPatterns = {"/front", "/front/*"})
@Slf4j
public class FrontServlet extends GenericServlet {

    private static final long serialVersionUID = 8469266686553913672L;

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            ResHandler.getInstance().success(res);
        } catch (Exception e) {
            log.error("异常", e);
            ResHandler.getInstance().fail(res);
        }
    }
}
