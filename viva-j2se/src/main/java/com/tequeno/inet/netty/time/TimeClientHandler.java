package com.tequeno.inet.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.HashedWheelTimer;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.TimeUnit;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 和服务器建立连接
     *
     * @param ctx channel连接上下文
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        ChannelFuture future = ctx.writeAndFlush(Unpooled.copiedBuffer("hello world!", CharsetUtil.UTF_8));
        future.addListener(f-> {
            System.out.println(f.isSuccess());
            System.out.println(f.isDone());
            System.out.println(f.isCancelled());
            System.out.println(f.isCancellable());
        });
    }

    /**
     * 和服务器断开连接
     *
     * @param ctx channel连接上下文
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    /**
     * 接收到服务器发送消息
     *
     * @param ctx channel连接上下文
     * @param msg 接收到的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        ByteBuf in = (ByteBuf) msg;
        String getMsg = in.toString(CharsetUtil.UTF_8);
        System.out.println(getMsg);
        ReferenceCountUtil.release(msg);
    }

//    /**
//     * 一次消息接收完毕
//     *
//     * @param ctx channel连接上下文
//     * @throws Exception
//     */
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelReadComplete");
//        System.out.println("当前线程:"+Thread.currentThread().getName());
//    }

    /**
     * 连接出现异常
     * 一般断开连接
     *
     * @param ctx   channel连接上下文
     * @param cause 异常栈
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("channelInactive");
        cause.printStackTrace();
        ctx.close();
    }
}