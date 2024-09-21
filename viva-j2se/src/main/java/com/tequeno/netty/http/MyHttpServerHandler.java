package com.tequeno.netty.http;

import com.tequeno.netty.NettyResponseHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http服务器
 * 多路io循环使用线程池
 * 为每一次请求单独分配一个channel pipeline handler
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<Object> {

    private final static Logger log = LoggerFactory.getLogger(MyHttpServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("MyHttpServerHandler接收到消息:{}", msg);
        MyHttpServer.send(ctx, NettyResponseHandler.success(msg));
    }
}