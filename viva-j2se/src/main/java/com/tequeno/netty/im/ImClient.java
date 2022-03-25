package com.tequeno.netty.im;

import com.tequeno.netty.NettyConstant;
import com.tequeno.netty.protobuf.NettyMsgWrapper;
import com.tequeno.netty.protobuf.Wrapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class ImClient {

    private String host;

    private int port;

    public ImClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ImClientInitializer());

            ChannelFuture f = b.connect(host, port).sync();
            Channel channel = f.channel();

            System.out.println("-------------" + channel.localAddress());

            Scanner in = new Scanner(System.in);
            while (true) {
                String line = in.nextLine();
                System.out.println("[发送]" + line);
                if (NettyConstant.BREAK_OUT.equals(line)) {
                    channel.close();
                    break;
                }
//                channel.writeAndFlush(line);
                Wrapper wrap = NettyMsgWrapper.wrap("2000", line);
                channel.writeAndFlush(wrap);
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new ImClient(NettyConstant.HOST, NettyConstant.PORT).run();
    }
}