package com.tequeno.inet.netty.mine;

import com.tequeno.inet.nioconst.NioBodyDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MyClientHandler extends SimpleChannelInboundHandler<NioBodyDto> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NioBodyDto dto) throws Exception {
//        System.out.println("MyClientHandler------------" + dto);
        System.out.println("[接收]"+dto.getMsg());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler-----channelActive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}