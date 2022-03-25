package com.tequeno.netty.time;

import com.tequeno.netty.NettyConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // 主线程组只处理客户端的连接请求并转发注册到子处理器
        // 创建线程池时可以传入相应的参数 指定初始化线程个数和策略
        // 默认情况下是cpu核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 工作线程组 处理实际的读写请求
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 服务器启动对象
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup) // 设置两个工作组
                    .channel(NioServerSocketChannel.class) // 设置服务器连接使用的通道
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置服务器连接队列的个数 指定连接队列的大小
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置客户端的连接为活跃连接 服务器不会自动切换和客户端的连接了 不过还是可以通过编码实现断开连接
                    .childHandler(new TimeServerInitializer()); // 设置工作组的处理器事件 即设置对应的pipeline

            // 绑定一个端口启动 并监听接收客户端的连接
            ChannelFuture f = b.bind(port).sync();

            // 监听服务器的通道关闭事件
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new TimeServer(NettyConstant.PORT).run();
    }
}