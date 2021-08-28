package com.tequeno.inet.netty.client;


import com.alibaba.fastjson.JSON;
import com.tequeno.inet.netty.NettyRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class MyClientEncoder extends MessageToByteEncoder<NettyRequest> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyRequest msg, ByteBuf out) {
        out.writeBytes(JSON.toJSONString(msg).getBytes(StandardCharsets.UTF_8));
        System.out.println("MyClientEncoder");
    }
}