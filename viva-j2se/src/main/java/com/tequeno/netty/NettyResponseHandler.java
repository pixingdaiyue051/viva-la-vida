package com.tequeno.netty;

public class NettyResponseHandler {

    public static NettyResponse success(NettyCodeEnum nettyCodeEnum) {
        NettyResponse response = new NettyResponse();
        response.setSuccess(true);
        response.setCode(nettyCodeEnum.getCode());
        response.setMsg(nettyCodeEnum.getDesc());
        return response;
    }

    public static NettyResponse success(NettyCodeEnum nettyCodeEnum, String msg) {
        NettyResponse response = success(nettyCodeEnum);
        response.setMsg(msg);
        return response;
    }

    public static NettyResponse success(NettyCodeEnum nettyCodeEnum, Object data) {
        NettyResponse response = success(nettyCodeEnum);
        response.setData(data);
        return response;
    }

    public static NettyResponse success(NettyCodeEnum nettyCodeEnum, String msg, Object data) {
        NettyResponse response = success(nettyCodeEnum);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static NettyResponse success() {
        return success(NettyCodeEnum.SUCCESS);
    }

    public static NettyResponse success(String msg) {
        return success(NettyCodeEnum.SUCCESS, msg);
    }

    public static NettyResponse success(Object data) {
        return success(NettyCodeEnum.SUCCESS, data);
    }

    public static NettyResponse success(String msg, Object data) {
        return success(NettyCodeEnum.SUCCESS, msg, data);
    }

    public static NettyResponse fail(NettyCodeEnum nettyCodeEnum) {
        NettyResponse response = new NettyResponse();
        response.setSuccess(false);
        response.setCode(nettyCodeEnum.getCode());
        response.setMsg(nettyCodeEnum.getDesc());
        return response;
    }

    public static NettyResponse fail() {
        return fail(NettyCodeEnum.FAIL);
    }

    public static NettyResponse fail(String msg) {
        NettyResponse response = fail();
        response.setMsg(msg);
        return response;
    }

    public static NettyResponse fail(NettyCodeEnum nettyCodeEnum, String msg) {
        NettyResponse response = fail(nettyCodeEnum);
        response.setMsg(msg);
        return response;
    }
}
