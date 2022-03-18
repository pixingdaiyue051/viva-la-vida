package com.tequeno.inet.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;

/**
 * http服务器
 * 多路io循环使用线程池
 * 为每一次请求单独分配一个channel pipeline handler
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
//        System.out.println("------------------");
//        System.out.println(msg);
//        System.out.println("------------------");
        System.out.println("消息类型:" + msg.getClass());
        System.out.println("客户端地址:" + ctx.channel().remoteAddress());
        System.out.println("channel id:" + ctx.channel().id().asLongText());
        System.out.println("pipeline hashcode:" + ctx.pipeline().hashCode());
        System.out.println("当前线程:" + Thread.currentThread().getName());
        System.out.println("****************************");


        // 解码得到http请求
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String uri = request.uri();
            System.out.println(uri);
            if ("/favicon.ico".equals(uri)) {
                System.out.println("暂无网页图标");
                return;
            }


            // 先构建需要发送数据
            ByteBuf content = Unpooled.copiedBuffer(LocalDateTime.now().toString(), CharsetUtil.UTF_8);
            // 将消息封装成httpResponse发送到客户端
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
                    .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response);
        }

//        // 解码得到请求体 TODO
//        if (msg instanceof HttpContent) {
//            HttpContent content = (HttpContent) msg;
//            ByteBuf byteBuf = content.content();
//            byte[] array = byteBuf.array();
//            System.out.println(new String(array, StandardCharsets.UTF_8));
//        }
    }
}
