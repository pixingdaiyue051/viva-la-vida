package com.tequeno.netty.time;

import com.tequeno.netty.NettyConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class TimeClient {

    private String host;

    private int port;

    public TimeClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        // 创建线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 客户端启动对象
            Bootstrap b = new Bootstrap();
            b.group(workerGroup) // 设置工作组
                    .channel(NioSocketChannel.class) // 设置客户端的连接通道类型
                    .option(ChannelOption.SO_KEEPALIVE, true) // 保持和服务器的活跃链接
                    .handler(new TimeClientInitializer()); // 添加自定义事件处理器

            // 连接到服务器并启动
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            Channel channel = f.channel();

            // 监听客户端的通道关闭事件
//            f.channel().closeFuture().sync();

            Scanner in = new Scanner(System.in);
            while (true) {
//                Object line = in.nextLine();
                Object line = in.nextInt();
                System.out.println("[发送]" + line);
                if (NettyConstant.BREAK_OUT.equals(line)) {
                    channel.close();
                    break;
                }
                channel.writeAndFlush(line);
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new TimeClient(NettyConstant.HOST, NettyConstant.PORT).run();
    }
}