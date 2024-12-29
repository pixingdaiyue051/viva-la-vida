package com.tequeno.servlet;

import com.alibaba.fastjson.JSON;
import com.tequeno.handler.FileHandler;
import com.tequeno.utils.HtResultUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 5946051451412318349L;

    private final static Logger log = LoggerFactory.getLogger(UploadServlet.class);

    @Override
    public void init() throws ServletException {
        log.info("UploadServlet init");
        FileHandler.getInstance().initDir();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("UploadServlet doGet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("UploadServlet doPost");

        try {
            Part filePart = req.getPart("file");
            log.info("上传文件[{}],大小[{}]", filePart.getSubmittedFileName(), filePart.getSize());
            FileHandler.getInstance().uploadFile(filePart);

            String res = JSON.toJSONString(HtResultUtil.success("已上传"));
            PrintWriter writer = resp.getWriter();
            writer.write(res);
            writer.flush();
        } catch (Exception e) {
            log.error("上传异常", e);
            String res = JSON.toJSONString(HtResultUtil.fail("上传失败"));
            PrintWriter writer = resp.getWriter();
            writer.write(res);
            writer.flush();
        }
    }
}
