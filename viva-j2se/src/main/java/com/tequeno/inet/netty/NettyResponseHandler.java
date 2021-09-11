package com.tequeno.inet.netty;

public class NettyResponseHandler {

    public static NettyResponse success() {
        NettyResponse response = new NettyResponse();
        response.setCode("0000");
        response.setSuccess(true);
        return response;
    }

    public static NettyResponse success(String code, Object data) {
        NettyResponse response = success();
        response.setCode(code);
        response.setData(data);
        return response;
    }

    public static NettyResponse success(Object data) {
        NettyResponse response = success();
        response.setData(data);
        return response;
    }

    public static NettyResponse fail() {
        NettyResponse response = new NettyResponse();
        response.setCode("1000");
        response.setSuccess(false);
        return response;
    }

    public static NettyResponse fail(String msg) {
        NettyResponse response = fail();
        response.setMsg(msg);
        return response;
    }
}
