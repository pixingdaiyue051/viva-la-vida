package com.tequeno.netty.time;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

/**
 * 一个简单的tcp连接
 * 也可以继承SimpleChannelInboundHandler 实现消息类型字段判断和释放消息
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int count;

    /**
     * 客户端连接成功
     *
     * @param ctx channel连接上下文
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    /**
     * 客户端断开连接
     *
     * @param ctx channel连接上下文
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    /**
     * 接收到客户端发送消息
     *
     * @param ctx channel连接上下文
     * @param msg 接收到的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        System.out.println("[接收次数]"+ (++count));
        System.out.println("[接收]"+ msg);
        // 1.立刻发送消息
        LocalDateTime now = LocalDateTime.now();
        String currTime = now.toString();
        System.out.println("[发送]" + currTime);
//        ctx.writeAndFlush(currTime);
        ctx.writeAndFlush(now.getNano());

//        // 2.将消息提交到taskQueue中异步执行
//        // taskQueue是当前线程NioEventLoop内的一个任务队列 所有提交到整个队列的任务会按照入队顺序执行
//        ctx.channel().eventLoop().execute(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(10L);
//                ctx.writeAndFlush(Unpooled.copiedBuffer(LocalDateTime.now().toString(), CharsetUtil.UTF_8));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        ctx.channel().eventLoop().execute(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(20L);
//                ctx.writeAndFlush(Unpooled.copiedBuffer(LocalDateTime.now().toString(), CharsetUtil.UTF_8));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        // 3.将消息提交到scheduleTaskQueue中延迟执行
//        ctx.channel().eventLoop().schedule(() -> ctx.writeAndFlush(Unpooled.copiedBuffer(LocalDateTime.now().toString(), CharsetUtil.UTF_8)), 30, TimeUnit.SECONDS);
    }

//    /**
//     * 一次消息发送完毕
//     * @param ctx channel连接上下文
//     * @throws Exception
//     */
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelReadComplete");
//        System.out.println("当前线程:"+Thread.currentThread().getName());
//        ctx.writeAndFlush(Unpooled.copiedBuffer(LocalDateTime.now().toString(), CharsetUtil.UTF_8));
//    }

    /**
     * 连接出现异常
     * 一般都会在这一步关闭和客户端的连接
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