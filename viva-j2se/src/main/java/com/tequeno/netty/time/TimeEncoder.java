package com.tequeno.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

///**
// * encoder是数据出战时 自定义handler的上一环 接收来自自定义handler的数据
// * 本质上还是一个继承了ChannelOutboundHandlerAdapter类的子类 使用ctx上下文的write方法将数据flush到下一环
// * 需要重写write方法
// * netty提供各类encoder  需要自己继承并重写encode方法
// */
//public class TimeEncoder extends ChannelOutboundHandlerAdapter {
//    @Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
//        try {
//            ByteBuf encoded = ctx.alloc().buffer(4);
//            encoded.writeInt(Integer.parseInt(msg.toString()));
//            ctx.write(encoded, promise);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//    }
//}

///**
// * 照搬StringEncoder  泛型类型中填写需要转换之前的数据类型即To前面的类型
// */
//public class TimeEncoder extends MessageToMessageEncoder<CharSequence> {
//
//    @Override
//    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
//        System.out.println("TimeEncoder");
//        if (msg.length() != 0) {
//            out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), CharsetUtil.UTF_8));
//        }
//    }
//}

/**
 * 将数据转成Integer发送
 */
public class TimeEncoder extends MessageToByteEncoder<Integer> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf byteBuf) throws Exception {
        System.out.println("TimeEncoder");
        byteBuf.writeInt(msg);
    }
}