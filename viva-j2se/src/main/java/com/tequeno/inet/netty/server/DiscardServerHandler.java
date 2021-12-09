package com.tequeno.inet.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;


/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            // Discard the received data silently.
            ByteBuf in = (ByteBuf) msg;
//            while (in.isReadable()) {
//                System.out.println((char) in.readByte());
//                System.out.flush();
//            }
            System.out.println(in.toString(CharsetUtil.US_ASCII));
            //  echo any received data is sent back.
//            ctx.write(msg);
//            ctx.flush();
            ctx.writeAndFlush(msg);
//            Please note that we did not release the received message unlike we did in the DISCARD example.
//            It is because Netty releases it for you when it is written out to the wire.
        } finally {
//            ((ByteBuf) msg).release();
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}