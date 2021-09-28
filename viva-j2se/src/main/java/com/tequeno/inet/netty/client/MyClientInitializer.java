package com.tequeno.inet.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
//                new MyClientEncoder(),
//                new MyClientDecoder(),
                new StringEncoder(CharsetUtil.UTF_8),
                new MyClientHandler()
        );
    }
}