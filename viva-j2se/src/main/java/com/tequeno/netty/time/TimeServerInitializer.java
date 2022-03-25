package com.tequeno.netty.time;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 当有新的channel连接进来的首先会调用这个initializer
 * 向pipeline中添加一系列需要使用的到的handler 包括怕出战和入站的handler并会按照出战和入站进行区分 以分割数据的发送和接受操作
 * 出战-----> 发送  writeAndFlush
 * 入站-----> 接收  channelRead
 * 每一次数据发送和接收都会按照initChannel方法中添加的handler顺序处理
 */
public class TimeServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
                new TimeEncoder(),
                new TimeDecoder(),
                new TimeServerHandler()
        );
    }
}