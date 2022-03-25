package com.tequeno.netty.mine;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

//public class MyDecoder extends ReplayingDecoder<Void> {
//
//    @Override
//    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
//        System.out.println("MyDecoder");
//        int len = byteBuf.readInt();
//        byte[] b = new byte[len];
//        byteBuf.readBytes(b);
//        MyMsg myMsg = new MyMsg();
//        myMsg.setLen(b.length);
//        myMsg.setContent(b);
//        list.add(myMsg);
//    }
//}

public class MyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        buf.markReaderIndex();
        int length = buf.readInt();

        if (buf.readableBytes() < length) {
            buf.resetReaderIndex();
            return;
        }

        byte[] b = new byte[length];
        buf.readBytes(b);
        MyMsg myMsg = new MyMsg();
        myMsg.setLen(b.length);
        myMsg.setContent(b);
        out.add(myMsg);
    }
}
