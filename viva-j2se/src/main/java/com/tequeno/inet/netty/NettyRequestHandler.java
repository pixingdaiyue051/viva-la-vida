package com.tequeno.inet.netty;

import java.util.ArrayList;
import java.util.List;

public class NettyRequestHandler {

    public static NettyRequest tmp() {
        NettyRequest request = new NettyRequest();
        request.setCode("0000");
        request.setKey("10kkj");
        request.setValue("8jhwqq");
        request.setMsg("8hsdvs");
        List<NettyResponse> list = new ArrayList<>();
        list.add(NettyResponseHandler.success("1009881"));
        list.add(NettyResponseHandler.success("1009882"));
        list.add(NettyResponseHandler.success("1009883"));
        list.add(NettyResponseHandler.success("1009884"));
        list.add(NettyResponseHandler.success("10098815"));
        request.setList(list);
        return request;
    }
}
