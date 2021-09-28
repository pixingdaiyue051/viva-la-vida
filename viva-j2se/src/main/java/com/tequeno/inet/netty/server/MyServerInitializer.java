package com.tequeno.inet.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.StandardCharsets;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
//                new MyServerEncoder(),
//                new StringDecoder(StandardCharsets.UTF_8),
                new MyServerDecoder(),
                new MyServerHandler()
        );
    }
}