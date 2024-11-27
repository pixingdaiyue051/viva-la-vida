package com.tequeno.netty.ws;

import com.tequeno.netty.NettyConstant;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        // websocket是基于http的协议提升 所以需要使用http编码解码器
        pipeline.addLast(new HttpServerCodec());
        // http数据是分段传输的 所以需要使用整个数据聚合器 2<<17=(2^8)*(2^1024)=128K 表示消息聚合时每片分段最大的字节数
        pipeline.addLast(new HttpObjectAggregator(2 << 17));
        // websocket数据以数据帧(frame)形式传输 2<<24=2^25=(2^5)*(2^10)*(2^10)=32M 表示一次传输过程最大可接收的字节数
        pipeline.addLast(new WebSocketServerProtocolHandler("/", null, false, 2 << 24));
        pipeline.addLast(new WebSocketServerEncoder());
        pipeline.addLast(new WebSocketServerDecoder());
        // 空闲状态处理器 会将空闲状态传输到pipeline中的下一个handler交由其userEventTrigger方法处理
        pipeline.addLast(new IdleStateHandler(NettyConstant.READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS));
        //添加自定义的助手类
        pipeline.addLast(new WebSocketServerHandler());
    }
}