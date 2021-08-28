package com.tequeno.inet.netty.client;

import com.tequeno.inet.netty.NettyRequest;
import com.tequeno.inet.netty.NettyRequestHandler;
import com.tequeno.inet.netty.NettyResponse;
import com.tequeno.inet.netty.UnixTime;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<NettyResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, NettyResponse response) throws Exception {
        System.out.println("MyClientHandler------" + response.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyRequest request = NettyRequestHandler.tmp();
//        request.setCode("0000");
//        ctx.writeAndFlush(request);
        request.setCode("0001");
        ctx.writeAndFlush(request);
//        request.setCode("0002");
//        ctx.writeAndFlush(request);
//        request.setCode("0003");
//        ctx.writeAndFlush(request);
//        request.setCode("0004");
//        ctx.writeAndFlush(request);
//        request.setCode("0005");
//        ctx.writeAndFlush(request);
//        request.setCode("0019");
//        ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}