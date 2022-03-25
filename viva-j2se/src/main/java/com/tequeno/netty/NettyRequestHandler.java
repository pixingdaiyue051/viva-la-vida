package com.tequeno.netty;

public class NettyRequestHandler {

    public static NettyRequest wrap(NettyCodeEnum codeEnum) {
        NettyRequest request = new NettyRequest();
        request.setCode(codeEnum.getCode());
        request.setMsg(codeEnum.getDesc());
        return request;
    }

    public static NettyRequest wrap(NettyCodeEnum codeEnum, String msg) {
        NettyRequest request = wrap(codeEnum);
        request.setMsg(msg);
        return request;
    }

    public static NettyRequest wrap(NettyCodeEnum codeEnum, Object value) {
        NettyRequest request = wrap(codeEnum);
        request.setValue(value);
        return request;
    }

    public static NettyRequest wrap(NettyCodeEnum codeEnum, String msg, Object value) {
        NettyRequest request = wrap(codeEnum);
        request.setMsg(msg);
        request.setValue(value);
        return request;
    }

    public static NettyRequest heartBeat() {
        return wrap(NettyCodeEnum.HEART);
    }

}
