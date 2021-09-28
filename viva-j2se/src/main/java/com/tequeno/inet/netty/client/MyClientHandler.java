package com.tequeno.inet.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String response) throws Exception {
        System.out.println("MyClientHandler------" + response.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler-----channelActive");
//        ByteBuf buffer = ctx.alloc().buffer(16); // Unpooled.buffer(16);
//        buffer.writeBytes("&&&&&&&&&&&&&&&-1".getBytes(StandardCharsets.UTF_8));
//        ctx.writeAndFlush(buffer);
        ctx.writeAndFlush("&&&&&&&&&&&&&&&-1");
        ExecutorService pool = Executors.newCachedThreadPool();
        IntStream.range(0, 20)
                .forEach(i -> pool.execute(() -> {
//                    ByteBuf buffer1 = ctx.alloc().buffer(16); // Unpooled.buffer(16);
//                    buffer1.writeBytes(("&&&&&&&&&&&&&&&" + i).getBytes(StandardCharsets.UTF_8));
//                    ctx.writeAndFlush(buffer1);
                    ctx.writeAndFlush("&&&&&&&&&&&&&&&" + i);
                }));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler-----channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}