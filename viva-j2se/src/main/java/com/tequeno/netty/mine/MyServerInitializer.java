package com.tequeno.netty.mine;

import com.tequeno.netty.protobuf.Wrapper;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
//                new ProtobufEncoder(),
//                new ProtobufDecoder(Wrapper.getDefaultInstance()),
                new MyEncoder(),
                new MyDecoder(),
                new MyServerHandler()
        );
    }
}