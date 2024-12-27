package com.tequeno.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
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
        log.info("IndexServlet doGet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("IndexServlet doPost");
        String uri = req.getRequestURI();
        String method = req.getMethod();
        log.info("uri[{}],method[{}]", uri, method);
        if ("/favicon.ico".equals(uri)) {
            return;
        }
        if ("OPTIONS".equals(method)) {
            return;
        }

        ServletContext context = req.getServletContext();
        String contextPath = context.getContextPath();
        try {
            String remoteUser = req.getRemoteUser();
            String remoteAddr = req.getRemoteAddr();
            String remoteHost = req.getRemoteHost();
            int remotePort = req.getRemotePort();
            log.info("remoteUser[{}],remoteAddr[{}],remoteHost[{}],remotePort[{}]", remoteUser, remoteAddr, remoteHost, remotePort);

            String localAddr = req.getLocalAddr();
            String localName = req.getLocalName();
            int localPort = req.getLocalPort();
            String serverName = req.getServerName();
            int serverPort = req.getServerPort();
            log.info("localAddr[{}],localName[{}],localPort[{}],serverName[{}],serverPort[{}]", localAddr, localName, localPort, serverName, serverPort);

            String servletPath = req.getServletPath();
            String realPath = context.getRealPath("");
            log.info("contextPath[{}],servletPath[{}],realPath[{}]", contextPath, servletPath, realPath);

            StringBuffer requestURL = req.getRequestURL();
            String queryString = req.getQueryString();
            String pathInfo = req.getPathInfo();
            String pathTranslated = req.getPathTranslated();
            log.info("requestURL[{}],queryString[{}],pathInfo[{}],pathTranslated[{}]", requestURL, queryString, pathInfo, pathTranslated);

            if ("/index".equals(uri)) {
                RequestDispatcher dispatcher = req.getRequestDispatcher(contextPath + "/index.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            if ("/err".equals(uri) || "/error".equals(uri)) {
                throw new RuntimeException("err");
            }

            resp.sendRedirect(contextPath + "/404.jsp");
        } catch (Exception e) {
            log.error("异常", e);
            resp.sendRedirect(contextPath + "/500.jsp");
        }
    }
}