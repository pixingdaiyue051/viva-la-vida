package com.tequeno.inet.netty.client;

import com.tequeno.inet.nioconst.NioBodyDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MyClientHandler extends SimpleChannelInboundHandler<NioBodyDto> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NioBodyDto dto) throws Exception {
        System.out.println("MyClientHandler------------" + dto);
//        TimeUnit.SECONDS.sleep(3L);
//        ctx.writeAndFlush(NioMsgCodeEnum.desc(dto.getCode()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler-----channelActive");
        ctx.writeAndFlush("&&&&&&&&&&&&&&&-start");
        ExecutorService pool = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .forEach(i -> pool.execute(() -> ctx.writeAndFlush("&&&&&&&&&&&&&&&" + i)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}