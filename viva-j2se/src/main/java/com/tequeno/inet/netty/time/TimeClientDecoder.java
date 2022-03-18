//package com.tequeno.inet.netty.time;
//
//import com.tequeno.inet.netty.UnixTime;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//
//import java.util.List;
//
//public class TimeClientDecoder extends ByteToMessageDecoder { // (1)
//
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
//        if (in.readableBytes() < 4) {
//            System.out.println("decode=1");
//            return; // (3)
//        }
//
////        out.add(in.readBytes(4)); // (4)
//        out.add(new UnixTime(in.readUnsignedInt()));
//        System.out.println("decode=2");
//    }
//}