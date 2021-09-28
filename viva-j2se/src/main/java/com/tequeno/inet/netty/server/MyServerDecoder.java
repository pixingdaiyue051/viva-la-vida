package com.tequeno.inet.netty.server;

import com.alibaba.fastjson.JSON;
import com.tequeno.inet.netty.NettyRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyServerDecoder extends MessageToMessageDecoder<ByteBuf> { // (1)

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)

        String msg = in.toString(StandardCharsets.UTF_8);
        System.out.println("MyServerDecoder------" + in);
        int len = in.readableBytes();
        byte[] bytes = new byte[len];
        in.getBytes(0, bytes);
        String temp = new String(bytes);
        System.out.println("MyServerDecoder temp------" + temp);
        in.skipBytes(len);
        out.add(msg);
    }
}