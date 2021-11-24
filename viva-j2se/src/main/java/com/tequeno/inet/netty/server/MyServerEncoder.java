package com.tequeno.inet.netty.server;

import com.alibaba.fastjson.JSON;
import com.tequeno.inet.nio.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class MyServerEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) {
        System.out.println("MyServerEncoder------------");

        String tid = NioUtil.uuid();
        NioBodyDto body = NioBodyHandler.wrap(NioMsgCodeEnum.CODE_1.getCode(), msg, tid);
        byte[] src = JSON.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        String head = NioHeadHandler.text(src.length, tid);
        out.writeBytes(head.getBytes(StandardCharsets.UTF_8));
        out.writeBytes(src);
    }
}