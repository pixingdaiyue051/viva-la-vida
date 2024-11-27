package com.tequeno;

import com.tequeno.utils.HtResultUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = -2668943737919697336L;

    private final static Logger log = LoggerFactory.getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("doGet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("doPost");
        String uri = req.getRequestURI();
        String method = req.getMethod();
        log.info("uri[{}],method[{}]", uri, method);
        if ("/favicon.ico".equals(uri)) {
            return;
        }
        if ("OPTIONS".equals(method)) {
            return;
        }
        String contextPath = req.getContextPath();
        try {
            if ("/".equals(uri) || "/index".equals(uri)) {
                RequestDispatcher dispatcher = req.getRequestDispatcher(contextPath + "/index.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            if ("/user".equals(uri)) {
                String path = String.format("%s/WEB-INF/%s.jsp", contextPath, "user");
                req.setAttribute("result", HtResultUtil.success());
                req.setAttribute("idx", "987hnaasd");
                RequestDispatcher dispatcher = req.getRequestDispatcher(path);
                dispatcher.forward(req, resp);
                return;
            }

            if (uri.contains("e")) {
                System.out.println(1 / 0);
            }

            resp.sendRedirect(contextPath + "/404.jsp");
        } catch (Exception e) {
            log.error("异常", e);
            resp.sendRedirect(contextPath + "/500.jsp");
        }
    }
}