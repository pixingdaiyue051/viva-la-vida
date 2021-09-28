package com.tequeno.inet.netty.ws;

import com.alibaba.fastjson.JSON;
import com.tequeno.inet.netty.NettyRequest;
import com.tequeno.inet.netty.NettyResponse;
import com.tequeno.inet.netty.NettyResponseHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //TextWebSocketFrame是netty用于处理websocket发来的文本对象

    //所有正在连接的channel都会存在这里面，所以也可以间接代表在线的客户端
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //在线人数
    public static int online;

    //接收到客户都发送的消息
    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //send_message是我的自定义类型，前后端分离往往需要统一数据格式，可以先把对象转成json字符串再发送给客户端
        System.out.println(msg.text());
        NettyRequest request = JSON.parseObject(msg.text(), NettyRequest.class);
        if ("00".equals(request.getCode())) {
            System.out.println("收到心跳包");
            sendMessage(ctx, NettyResponseHandler.success(request.getCode(), request.getMsg() + "知道了"));
        }
        if ("01".equals(request.getCode())) {
            System.out.println("收到订阅包");
            sendMessage(ctx, NettyResponseHandler.success(request.getCode(), request.getMsg() + "好的"));
        }
        if ("02".equals(request.getCode())) {
            System.out.println("收到业务包");
            sendMessage(ctx, NettyResponseHandler.success(request.getCode(), request.getMsg() + "再等等"));
        }
//        sendAllMessages(ctx, NettyResponseHandler.success());
    }


    //客户端建立连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
        online = channelGroup.size();
        System.out.println(ctx.channel().remoteAddress() + "上线了!");
        System.out.println("目前在线数" + online);
    }

    //关闭连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.remove(ctx.channel());
        online = channelGroup.size();
        System.out.println(ctx.channel().remoteAddress() + "断开连接");
        System.out.println("目前在线数" + online);
    }

    //出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    //给单个人发送消息
    private void sendMessage(ChannelHandlerContext ctx, NettyResponse msg) {
        ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
    }

    //给每个人发送消息,除发消息人外
    private void sendAllMessages(NettyResponse msg) {
        channelGroup.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
    }
}