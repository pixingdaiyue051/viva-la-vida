package com.tequeno.inet.netty.server;


import com.alibaba.fastjson.JSON;
import com.tequeno.inet.netty.NettyResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class MyServerEncoder extends MessageToByteEncoder<NettyResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyResponse msg, ByteBuf out) {
        out.writeBytes(JSON.toJSONString(msg).getBytes(StandardCharsets.UTF_8));
        System.out.println("MyServerEncoder");
    }
}