package com.tequeno.netty.mine;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyEncoder extends MessageToByteEncoder<MyMsg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyMsg myMsg, ByteBuf byteBuf) throws Exception {
        System.out.println("MyEncoder");
        byteBuf.writeInt(myMsg.getLen());
        byteBuf.writeBytes(myMsg.getContent());
    }
}
