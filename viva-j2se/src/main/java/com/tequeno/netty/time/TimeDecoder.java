package com.tequeno.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

///**
// * decoder是数据入站的第一环
// * 本质上是一个ChannelInboundHandlerAdapter的子类 使用CodecOutputList将会数据flush到下一环
// * 需要重写channelRead方法
// * netty提供各类decoder 需要自己继承并重写decode方法
// * MessageToMessageDecoder
// * ByteToMessageDecoder   接收二进制数据 自定义转换数据类型 需要自己判断数据长度 否则得到的结果会和预期的不一致
// * ReplayingDecoder 继承 ByteToMessageDecoder 无需自己判断数据长度
// * LineBasedFrameDecoder 使用行尾控制字符作为分隔符
// * DelimiterBasedFrameDecoder  使用自定义的字符作为分隔符
// * LengthFieldBasedFrameDecoder  指定长度来标识包信息 可以处理tcp粘包半包信息问题
// * HttpObjectDecoder  http消息解码器
// */
//public class TimeDecoder extends ChannelInboundHandlerAdapter {
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
//    }
//}

///**
// * 照搬StringDecoder
// */
//public class TimeDecoder extends MessageToMessageDecoder<ByteBuf> {
//
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
//        System.out.println("TimeDecoder");
//        out.add(msg.toString(CharsetUtil.UTF_8));
//    }
//}

/**
 * 只读取4个字节的Integer数据
 */
public class TimeDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("TimeDecoder");
        int readableLen = in.readableBytes();
        out.add(in.readInt());
//        if (readableLen >= 4) {
//        }
    }
}

