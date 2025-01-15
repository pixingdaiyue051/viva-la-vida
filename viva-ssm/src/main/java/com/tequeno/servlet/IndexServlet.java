package com.tequeno.servlet;

import com.alibaba.fastjson.JSON;
import com.tequeno.utils.HtResultUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "IndexServlet", value = "/")
@Slf4j
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 5010342985766835764L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(HtResultUtil.fail("暂无该功能")));
        writer.flush();
    }
}
