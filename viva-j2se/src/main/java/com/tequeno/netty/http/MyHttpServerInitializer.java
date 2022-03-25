package com.tequeno.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline()
                .addLast(new HttpServerCodec(4096, 8192, 65536)) // netty提供的http服务编码解码器 maxChunkSize指每个数据分片最大字节数
//                .addLast(new ChunkedWriteHandler())
//                .addLast(new HttpObjectAggregator(65536)) // netty提供的http消息聚合器
                .addLast(new MyHttpServerEncoder())
                .addLast(new MyHttpServerDecoder())
                .addLast(new MyHttpServerHandler())
        ;
    }
}