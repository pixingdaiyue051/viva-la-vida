package com.tequeno.servlet;

import com.alibaba.fastjson.JSON;
import com.tequeno.handler.FileHandler;
import com.tequeno.utils.HtResultUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = -5225236372647086695L;

    private final static Logger log = LoggerFactory.getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("UserServlet doGet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("UserServlet doPost");
        String uri = req.getRequestURI();
        String method = req.getMethod();
        log.info("uri[{}],method[{}]", uri, method);
        ServletContext context = req.getServletContext();
        String contextPath = context.getContextPath();
        try {

            if ("/user".equals(uri)) {
                String path = Paths.get(contextPath, "WEB-INF", "user.jsp").toString();
                req.setAttribute("result", HtResultUtil.success());
                req.setAttribute("idx", "2841各国纷纷苦瓜纯粹的斯坦福火箭航天发仿佛已经扣除当天");

                RequestDispatcher dispatcher = req.getRequestDispatcher(path);
                dispatcher.forward(req, resp);
                return;
            }

            if ("/user/add".equals(uri)) {
                Part namePart = req.getPart("name");
                Part genderPart = req.getPart("gender");
                Part avatarPart = req.getPart("avatar");
                String name = new String(namePart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                String gender = new String(genderPart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                log.info("name[{}],gender[{}],avatar[{}],avatar[{}]", name, gender, avatarPart.getSubmittedFileName(), avatarPart.getSize());
                FileHandler.getInstance().uploadFile(avatarPart);

                String res = JSON.toJSONString(HtResultUtil.success("已添加"));
                PrintWriter writer = resp.getWriter();
                writer.write(res);
                writer.flush();
                return;
            }

            resp.sendRedirect(contextPath + "/404.jsp");
        } catch (Exception e) {
            log.error("异常", e);
            resp.sendRedirect(contextPath + "/500.jsp");
        }
    }
}
