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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "EmpServlet", urlPatterns = {"/emp", "/emp/*"})
@MultipartConfig(maxRequestSize = 1024000, maxFileSize = 1024000)
@Slf4j
public class EmpServlet extends HttpServlet {

    private static final long serialVersionUID = -5225236372647086695L;

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
            String uri = req.getRequestURI();
            if ("/emp/list".equals(uri)) {
                List<File> fileList = FileHandler.getInstance().query();
                String time = LocalDateTime.now().format(formatter);
                ResHandler.getInstance().success(resp, Map.of("fileList", fileList, "time", time));
                return;
            }

            if ("/emp/add".equals(uri)) {
                Part namePart = req.getPart("name");
                Part genderPart = req.getPart("gender");
                Part avatarPart = req.getPart("avatar");
                String name = new String(namePart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                String gender = new String(genderPart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                log.info("{},{},{}", namePart.getContentType(), genderPart.getContentType(), avatarPart.getContentType());
                log.info("name[{}],gender[{}],avatar[{}],avatar[{}]", name, gender, avatarPart.getSubmittedFileName(), avatarPart.getSize());
                FileHandler.getInstance().uploadFile(avatarPart);

                ResHandler.getInstance().success(resp, "已添加");
                return;
            }

            ResHandler.getInstance().success(resp);
        } catch (Exception e) {
            log.error("异常", e);
            ResHandler.getInstance().fail(resp, e.getMessage());
        }
    }
}
