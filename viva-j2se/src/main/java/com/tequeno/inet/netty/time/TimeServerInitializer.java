package com.tequeno.inet.netty.time;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class TimeServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
//                new TimeServerEncoder(),
                new TimeServerHandler()
        );
    }
}