package com.tequeno.netty.http;

import com.tequeno.netty.NettyConstant;
import com.tequeno.netty.NettyResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyHttpServer {

    private int port;

    public MyHttpServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 128)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new MyHttpServerInitializer());

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("netty启动失败");
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void send(ChannelHandlerContext ctx, NettyResponse res) {
        ctx.writeAndFlush(res);
    }

    public static void main(String[] args) {
        int port = NettyConstant.PORT;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        new MyHttpServer(port).run();
    }
}
