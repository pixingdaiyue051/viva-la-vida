package com.tequeno.handler;

import com.alibaba.fastjson.JSON;
import com.tequeno.utils.HtResultUtil;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResHandler {

    public void success(ServletResponse resp) throws IOException {
        success(resp, "succeed");
    }

    public void success(ServletResponse resp, Object data) throws IOException {
        String res = JSON.toJSONString(HtResultUtil.success(data));
        PrintWriter writer = resp.getWriter();
        writer.write(res);
        writer.flush();
    }

    public void fail(ServletResponse resp) throws IOException {
        fail(resp, "failed");
    }

    public void fail(ServletResponse resp, String msg) throws IOException {
        String res = JSON.toJSONString(HtResultUtil.fail(msg));
        PrintWriter writer = resp.getWriter();
        writer.write(res);
        writer.flush();
    }

    public static ResHandler getInstance() {
        return ResHolder.INSTANCE;
    }

    private final static class ResHolder {
        private final static ResHandler INSTANCE = new ResHandler();
    }
}
