package com.tequeno.servlet;

import com.tequeno.handler.FileHandler;
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

@WebServlet(name = "UploadServlet", urlPatterns = "/upload")
@MultipartConfig(maxRequestSize = 1024000, maxFileSize = 1024000)
@Slf4j
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 5946051451412318349L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Part filePart = req.getPart("file");
            log.info("上传文件[{}],大小[{}]", filePart.getSubmittedFileName(), filePart.getSize());
            FileHandler.getInstance().uploadFile(filePart);

            ResHandler.getInstance().success(resp, "已上传");
        } catch (Exception e) {
            log.error("异常", e);
            ResHandler.getInstance().fail(resp, "上传失败");
        }
    }
}
