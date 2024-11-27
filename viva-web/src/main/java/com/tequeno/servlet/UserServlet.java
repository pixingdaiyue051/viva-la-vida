package com.tequeno.servlet;

import com.tequeno.handler.ResHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Enumeration;

@WebServlet(name = "UserServlet", urlPatterns = {"/user", "/user/*"}, asyncSupported = true)
@MultipartConfig(maxRequestSize = 1024000, maxFileSize = 1024000)
@Slf4j
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = -8574890033109373720L;
    private static final String FORM_DEFAULT = "application/x-www-form-urlencoded";
    private static final String FORM_DATA = "multipart/form-data";
    private static final String JSON = "application/json";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String contentType = req.getContentType();
            log.info("contentType[{}]", contentType);

            if (null == contentType || contentType.isEmpty() || FORM_DEFAULT.equals(contentType)) {
                Enumeration<String> parameterNames = req.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String k = parameterNames.nextElement();
                    String v = req.getParameter(k);
                    log.info("parameters k[{}] v[{}]", k, v);
                }
            } else if (contentType.startsWith(FORM_DATA)) {
                Collection<Part> parts = req.getParts();
                for (Part p : parts) {
                    String type = p.getContentType();
                    String k = p.getName();
                    byte[] bytes = p.getInputStream().readAllBytes();
                    String v = new String(bytes, StandardCharsets.UTF_8);
                    log.info("multipart [{}] k[{}] v[{}]", type, k, v);
                }
            } else if (JSON.equals(contentType)) {
                byte[] bytes = req.getInputStream().readAllBytes();
                String v = new String(bytes, StandardCharsets.UTF_8);
                log.info("{}", v);
            }

            ResHandler.getInstance().success(resp);
        } catch (Exception e) {
            log.error("异常", e);
            ResHandler.getInstance().fail(resp, e.getMessage());
        }
    }
}
