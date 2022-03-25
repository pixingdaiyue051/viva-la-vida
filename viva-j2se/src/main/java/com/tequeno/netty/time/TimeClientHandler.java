package com.tequeno.netty.time;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private int count;

    /**
     * 和服务器建立连接
     *
     * @param ctx channel连接上下文
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
//        Object msg = "hello world";
        Object msg = Integer.MAX_VALUE;

        for (int i = 0; i < 10; i++) {
            System.out.println("[发送]" + msg);
            ctx.writeAndFlush(msg);
        }
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
        System.out.println("[接收次数]"+ (++count));
        System.out.println("[接收]"+msg);
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
        System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
}