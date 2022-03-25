package com.tequeno.netty.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * http服务器
 * 多路io循环使用线程池
 * 为每一次请求单独分配一个channel pipeline handler
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        ctx.writeAndFlush(msg);
    }
}
