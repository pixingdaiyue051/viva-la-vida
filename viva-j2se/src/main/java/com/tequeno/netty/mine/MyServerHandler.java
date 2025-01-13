package com.tequeno.netty.mine;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MyServerHandler extends SimpleChannelInboundHandler<MyMsg> {

    private int count;

    //接收到客户都发送的消息
//    @Override
//    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
////        int len = msg.readableBytes();
////        byte[] bytes = new byte[len];
////        msg.readBytes(bytes);
////        String s = new String(bytes);
//        String s = msg.toString(CharsetUtil.UTF_8);
//
//        System.out.println("[接收次数]" + (++count));
//        System.out.println("[接收]" + s);
//
//        ctx.writeAndFlush(Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8));
//    }

//    @Override
//    public void channelRead0(ChannelHandlerContext ctx, Wrapper msg) throws Exception {
//
//        System.out.println("[接收次数]" + (++count));
//        System.out.println("[接收]" + msg.getMsg());
//
//        ctx.writeAndFlush(NettyMsgWrapper.wrap("2000", LocalDateTime.now().toString()));
//
//    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyMsg msg) throws Exception {

        System.out.println("[接收次数]" + (++count));
        System.out.println("[接收字节]" + msg.getLen());
        System.out.println("[接收]" + new String(msg.getContent(), StandardCharsets.UTF_8));

        FileChannel fc = FileChannel.open(Paths.get("/hexk/github/hey-jude/to-be-continued/lyric.txt"), StandardOpenOption.READ);
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

        fc.close();
    }

    //客户端建立连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    //关闭连接
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    //出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}