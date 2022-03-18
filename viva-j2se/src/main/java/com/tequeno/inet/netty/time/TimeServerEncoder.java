//package com.tequeno.inet.netty.time;
//
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelOutboundHandlerAdapter;
//import io.netty.channel.ChannelPromise;
//import io.netty.handler.codec.MessageToByteEncoder;
//
////public class TimeServerEncoder extends ChannelOutboundHandlerAdapter {
////
////    @Override
////    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
////        UnixTime m = (UnixTime) msg;
////        ByteBuf encoded = ctx.alloc().buffer(4);
////        encoded.writeInt((int) m.value());
////        ctx.write(encoded, promise); // (1)
////    }
////}
//
//public class TimeServerEncoder extends MessageToByteEncoder<UnixTime> {
//    @Override
//    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) {
//        out.writeInt((int) msg.getValue());
//        System.out.println("encode");
//    }
//}