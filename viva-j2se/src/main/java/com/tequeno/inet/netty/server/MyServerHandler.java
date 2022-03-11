package com.tequeno.inet.netty.server;

import com.tequeno.inet.nioconst.NioBodyDto;
import com.tequeno.inet.nioconst.NioMsgCodeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.TimeUnit;

public class MyServerHandler extends SimpleChannelInboundHandler<NioBodyDto> {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //接收到客户都发送的消息
    @Override
    public void channelRead0(ChannelHandlerContext ctx, NioBodyDto dto) throws Exception {
        System.out.println("MyServerHandler------------" + dto);
        TimeUnit.SECONDS.sleep(3L);
        sendMessage(ctx, NioMsgCodeEnum.desc(dto.getCode()) + dto.getMsg());
    }


    //客户端建立连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
        System.out.println(ctx.channel().remoteAddress() + "上线了!");
        System.out.println("目前在线数" + channelGroup.size());
    }

    //关闭连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.remove(ctx.channel());
        System.out.println(ctx.channel().remoteAddress() + "断开连接");
        System.out.println("目前在线数" + channelGroup.size());
    }

    //出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    //给单个人发送消息
    private void sendMessage(ChannelHandlerContext ctx, String msg) {
        ctx.writeAndFlush(msg);
    }
}