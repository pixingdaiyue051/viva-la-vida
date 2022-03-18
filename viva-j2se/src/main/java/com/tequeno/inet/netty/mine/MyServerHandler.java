package com.tequeno.inet.netty.mine;

import com.tequeno.inet.nioconst.NioBodyDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyServerHandler extends SimpleChannelInboundHandler<NioBodyDto> {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //接收到客户都发送的消息
    @Override
    public void channelRead0(ChannelHandlerContext ctx, NioBodyDto dto) throws Exception {
//        System.out.println("MyServerHandler------------" + dto);
        // 消息转发到其他客户端
        sendMessage(dto.getMsg(), channel -> channel != ctx.channel());

        info(ctx);
    }

    //客户端建立连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sendMessage(ctx.channel().remoteAddress() + "上线了");
        channelGroup.add(ctx.channel());
        System.out.println(ctx.channel().remoteAddress() + "上线了");
        System.out.println("目前在线数" + channelGroup.size());
        info(ctx);
    }

    //关闭连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        channelGroup.remove(ctx.channel());
        sendMessage(ctx.channel().remoteAddress() + "断开连接");
        System.out.println(ctx.channel().remoteAddress() + "断开连接");
        System.out.println("目前在线数" + channelGroup.size());
        info(ctx);
    }

    //出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //给单个人发送消息
    private void sendMessage(String msg, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(msg);
    }

    private void sendMessage(String msg) {
        channelGroup.writeAndFlush(msg);
    }

    //给单个人发送消息
    private void sendMessage(String msg, ChannelMatcher matcher) {
        channelGroup.writeAndFlush(msg, matcher);
    }

    private void info(ChannelHandlerContext ctx) {
        System.out.println("当前线程" + Thread.currentThread().getName());
        System.out.println("当前channel" + ctx.channel().remoteAddress());
        System.out.println("当前pipeline" + ctx.pipeline().hashCode());
    }
}