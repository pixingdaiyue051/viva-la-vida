package com.tequeno.servlet;

import com.tequeno.handler.FileHandler;
import com.tequeno.handler.ResHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet(name = "DownloadServlet", urlPatterns = "/download")
@Slf4j
public class DownloadServlet extends HttpServlet {

    private static final long serialVersionUID = -5654345684806684264L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String fileName = req.getParameter("file");
            if (null == fileName || fileName.isEmpty()) {
                return;
            }
            FileHandler.getInstance().downloadFile(resp, fileName);
        } catch (Exception e) {
            log.error("异常", e);
            ResHandler.getInstance().fail(resp, "下载失败");
        }
    }
}
