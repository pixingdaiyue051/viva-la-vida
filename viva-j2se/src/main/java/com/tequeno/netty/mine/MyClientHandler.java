package com.tequeno.netty.mine;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MyClientHandler extends SimpleChannelInboundHandler<MyMsg> {

    private int count;

//    @Override
//    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//        System.out.println("[接收次数]" + (++count));
//        System.out.println("[接收]" + msg.toString(CharsetUtil.UTF_8));
//    }

//    @Override
//    public void channelRead0(ChannelHandlerContext ctx, Wrapper msg) throws Exception {
//        System.out.println("[接收次数]" + (++count));
//        System.out.println("[接收]" + msg.getMsg());
//    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyMsg msg) throws Exception {
        System.out.println("[接收次数]" + (++count));
        System.out.println("[接收字节]" + msg.getLen());
        System.out.println("[接收]" + new String(msg.getContent(), StandardCharsets.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        FileChannel fc = FileChannel.open(Paths.get("/hexk/github/hey-jude/to-be-continued/lyric.txt"), StandardOpenOption.READ);
        for (int i = 0; i < 10; i++) {
//            ByteBuf buf = Unpooled.copiedBuffer("hello world" + i, CharsetUtil.UTF_8);
//            ctx.writeAndFlush(buf);

//            Wrapper wrap = NettyMsgWrapper.wrap("1000", "hello world" + i);
//            ctx.writeAndFlush(wrap);

            int v = (int) (Math.random() * fc.size());
            System.out.println("######" + v);
            ByteBuffer dst = ByteBuffer.allocate(v);
            fc.read(dst);
            dst.flip();

            byte[] b = new byte[dst.limit()];
            dst.get(b);

            MyMsg myMsg = new MyMsg();
            myMsg.setContent(b);
            myMsg.setLen(b.length);
            ctx.writeAndFlush(myMsg);
        }
        fc.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}