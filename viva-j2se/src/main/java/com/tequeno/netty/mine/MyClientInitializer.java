package com.tequeno.netty.mine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
                new MyEncoder(),
                new MyDecoder(),
                new MyClientHandler()
        );
    }
}