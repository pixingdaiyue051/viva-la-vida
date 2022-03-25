package com.tequeno.netty.im;

import com.tequeno.netty.protobuf.Wrapper;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class ImServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
//                new StringDecoder(),
//                new StringEncoder(),
//                new MyServerHandler(),
                new ProtobufEncoder(),
                new ProtobufDecoder(Wrapper.getDefaultInstance()),
                new ImServerProtobufHandler()
        );
    }
}