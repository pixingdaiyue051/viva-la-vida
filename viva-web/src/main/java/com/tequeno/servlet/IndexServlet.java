package com.tequeno.servlet;

import com.tequeno.handler.FileHandler;
import com.tequeno.handler.ResHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@WebServlet(name = "IndexServlet", urlPatterns = "/", asyncSupported = true)
@MultipartConfig(maxRequestSize = 1024000, maxFileSize = 1024000)
@Slf4j
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = -2668943737919697336L;
    private static final String FORM_DEFAULT = "application/x-www-form-urlencoded";
    private static final String FORM_DATA = "multipart/form-data";
    private static final String JSON = "application/json";

    private DateTimeFormatter formatter;

    @Override
    public void init() throws ServletException {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

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

            String uri = req.getRequestURI();
            StringBuffer requestURL = req.getRequestURL();
            String queryString = req.getQueryString();
            String pathInfo = req.getPathInfo();
            String pathTranslated = req.getPathTranslated();
            log.info("uri[{}],requestURL[{}],queryString[{}],pathInfo[{}],pathTranslated[{}]", uri, requestURL, queryString, pathInfo, pathTranslated);

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

            if ("/file".equals(uri)) {
                List<File> fileList = FileHandler.getInstance().query();
                String time = LocalDateTime.now().format(formatter);
                ResHandler.getInstance().success(resp, Map.of("fileList", fileList, "time", time));
                return;
            }

            ResHandler.getInstance().fail(resp, "暂无该功能");
        } catch (Exception e) {
            log.error("异常", e);
            ResHandler.getInstance().fail(resp, e.getMessage());
        }
    }
}