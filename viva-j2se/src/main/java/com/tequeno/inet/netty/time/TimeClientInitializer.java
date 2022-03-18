package com.tequeno.inet.netty.time;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class TimeClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
//                new TimeClientDecoder(),
                new TimeClientHandler()
        );
    }
}