package com.tequeno.inet.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline()
                .addLast(new HttpServerCodec()) // netty提供的http服务编码解码器
//                .addLast(new HttpObjectAggregator(65536)) // netty提供的http消息聚合器
                .addLast(new MyHttpServerHandler())
        ;
    }
}