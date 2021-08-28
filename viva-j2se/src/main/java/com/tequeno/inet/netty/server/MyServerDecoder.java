package com.tequeno.inet.netty.server;

import com.alibaba.fastjson.JSON;
import com.tequeno.inet.netty.NettyRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyServerDecoder extends ByteToMessageDecoder { // (1)

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)

        int len = in.readableBytes();
        if (len < 1) return;
        byte[] bytes = new byte[len];
        in.getBytes(0, bytes);
        String temp = new String(bytes);
        System.out.println("MyServerDecoder------" + temp);
        out.add(JSON.parseObject(temp, NettyRequest.class));
        in.skipBytes(len);
    }
}