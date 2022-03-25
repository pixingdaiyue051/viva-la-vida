package com.tequeno.netty.im;

import com.tequeno.netty.protobuf.Wrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ImClientProtobufHandler extends SimpleChannelInboundHandler<Wrapper> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Wrapper msg) throws Exception {
//        System.out.println("MyClientHandler------------" + dto);
        System.out.println("[接收]" + msg.getMsg());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler-----channelActive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}