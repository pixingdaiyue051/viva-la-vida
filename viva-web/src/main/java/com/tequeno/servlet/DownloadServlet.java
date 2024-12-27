package com.tequeno.servlet;

import com.tequeno.handler.FileHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DownloadServlet extends HttpServlet {

    private static final long serialVersionUID = -5654345684806684264L;

    private final static Logger log = LoggerFactory.getLogger(DownloadServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("DownloadServlet doGet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("DownloadServlet doPost");

        try {
            String fileName = req.getParameter("file");
            if (null == fileName || fileName.isEmpty()) {
                return;
            }
            FileHandler.getInstance().downloadFile(fileName, resp);

        } catch (Exception e) {
            log.error("下载异常", e);
        }
    }
}
