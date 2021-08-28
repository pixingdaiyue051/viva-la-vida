package com.tequeno.inet.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
                new MyServerEncoder(),
                new MyServerDecoder(),
                new MyServerHandler()
        );
    }
}