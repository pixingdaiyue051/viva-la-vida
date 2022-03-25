package com.tequeno.netty.ws;

import com.tequeno.netty.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<NettyRequest> {

    //接收到客户都发送的消息
    @Override
    public void channelRead0(ChannelHandlerContext ctx, NettyRequest request) throws Exception {
        System.out.println("server-----" + request);
        if (NettyCodeEnum.HEART.getCode().equals(request.getCode())) {
            System.out.println("收到心跳包");
            WebSocketServer.sendMsg(NettyResponseHandler.success(NettyCodeEnum.HEART), ctx);
        }
        if (NettyCodeEnum.SUB.getCode().equals(request.getCode())) {
            System.out.println("收到订阅包");
            String key = request.getKey();
            AttributeKey<Object> attributeKey = AttributeKey.valueOf(key);
            ctx.channel().attr(attributeKey).set(request.getValue());
            WebSocketServer.sendMsg(NettyResponseHandler.success(NettyCodeEnum.SUB), ctx);
        }
        if (NettyCodeEnum.BIZ.getCode().equals(request.getCode())) {
            System.out.println("收到业务包");
            WebSocketServer.sendMsg(NettyResponseHandler.success(NettyCodeEnum.BIZ), ctx);
        }
    }


    //客户端建立连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server-----channelActive");
        ctx.channel().attr(NettyConstant.ATTR_READ_IDLE).set(0);
        WebSocketServer.addClientChannel(ctx.channel());
    }

    //关闭连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server-----channelInactive");
//        ServerSocket.removeClientChannel(ctx.channel());
    }

    //出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("server-----userEventTriggered-----" + evt);
        if (evt instanceof IdleStateEvent) {
            Attribute<Object> attribute = ctx.channel().attr(NettyConstant.ATTR_READ_IDLE);
            int i = Integer.parseInt(attribute.get().toString());
            if (++i < NettyConstant.MAX_IDLE_TIMES) {
                attribute.set(i);
                return;
            }
//            // 先发一条消息告知即将关闭
//            WebSocketServer.sendMsg(NettyResponseHandler.fail(NettyCodeEnum.CLOSE), ctx);
//            // 延时关闭连接
//            ctx.channel().eventLoop().schedule((Callable<ChannelFuture>) ctx::close, NettyConstant.LATER_TO_CLOSE, TimeUnit.SECONDS);

            ctx.close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}