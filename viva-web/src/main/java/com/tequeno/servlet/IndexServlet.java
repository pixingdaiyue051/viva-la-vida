package com.tequeno.servlet;

import com.tequeno.handler.ResHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet(name = "IndexServlet", urlPatterns = "/")
@Slf4j
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = -2668943737919697336L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            ServletContext context = req.getServletContext();
            String contextPath = context.getContextPath();
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
            String scheme = req.getScheme();
            log.info("scheme[{}],localAddr[{}],localName[{}],localPort[{}],serverName[{}],serverPort[{}]", scheme, localAddr, localName, localPort, serverName, serverPort);

            String servletPath = req.getServletPath();
            String realPath = context.getRealPath("");
            log.info("contextPath[{}],servletPath[{}],realPath[{}]", contextPath, servletPath, realPath);

            StringBuffer requestURL = req.getRequestURL();
            String queryString = req.getQueryString();
            String pathInfo = req.getPathInfo();
            String pathTranslated = req.getPathTranslated();
            log.info("requestURL[{}],queryString[{}],pathInfo[{}],pathTranslated[{}]", requestURL, queryString, pathInfo, pathTranslated);

            ResHandler.getInstance().success(resp);
        } catch (Exception e) {
            log.error("异常", e);
            ResHandler.getInstance().fail(resp, e.getMessage());
        }
    }
}