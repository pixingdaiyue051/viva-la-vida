package com.tequeno.inet.netty.server;

import com.tequeno.inet.netty.NettyRequest;
import com.tequeno.inet.netty.NettyResponseHandler;
import com.tequeno.inet.netty.UnixTime;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Collections;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        System.out.println("MyServerHandler------" + request.toString());
//        if ("0000".equals(request.getCode())) {
//            ctx.writeAndFlush(NettyResponseHandler.success());
//            return;
//        }
//        if ("0001".equals(request.getCode())) {
//            ctx.writeAndFlush(NettyResponseHandler.success(new UnixTime()));
//            return;
//        }
//        if ("0002".equals(request.getCode())) {
//            ctx.writeAndFlush(NettyResponseHandler.success("new UnixTime()"));
//            return;
//        }
//        if ("0003".equals(request.getCode())) {
//            ctx.writeAndFlush(NettyResponseHandler.success(10L));
//            return;
//        }
//        if ("0004".equals(request.getCode())) {
//            ctx.writeAndFlush(NettyResponseHandler.success(10.01D));
//            return;
//        }
//        if ("0005".equals(request.getCode())) {
//            ctx.writeAndFlush(NettyResponseHandler.success(Collections.singletonList("loop")));
//            return;
//        }
        ctx.writeAndFlush(NettyResponseHandler.fail());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyServerHandler-----channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyServerHandler-----channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}