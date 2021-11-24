package com.tequeno.inet.netty.client;

import com.alibaba.fastjson.JSON;
import com.tequeno.inet.nio.NioBodyDto;
import com.tequeno.inet.nio.NioHeadDto;
import com.tequeno.inet.nio.NioHeadHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyClientDecoder extends ByteToMessageDecoder {

    private int offset = 0;

    private String last = null;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyClientDecoder------------" + in);

        int len = in.readableBytes();
        while (len > 0) {
            if (offset > 0) {
                ByteBuf lastBuf = in.readBytes(offset);
                String body = last + lastBuf.toString(StandardCharsets.UTF_8);
                System.out.println("body " + body);
                out.add(JSON.parseObject(body, NioBodyDto.class));
                last = null;
                offset = 0;
            }

            ByteBuf headBuf = in.readBytes(NioHeadHandler.FIXED_LENGTH);
            String head = headBuf.toString(StandardCharsets.UTF_8);
            System.out.println("head " + head);
            NioHeadDto headDto = JSON.parseObject(head, NioHeadDto.class);
            int nextLen = headDto.getLength();

            len -= NioHeadHandler.FIXED_LENGTH;
            if (nextLen > len) {
                offset = nextLen - len;
                last = in.readBytes(len).toString(StandardCharsets.UTF_8);
            } else {
                String body = in.readBytes(nextLen).toString(StandardCharsets.UTF_8);
                System.out.println("body " + body);
                out.add(JSON.parseObject(body, NioBodyDto.class));
            }

            len = in.readableBytes();
        }
    }

    private void bufInfo(ByteBuf in) {
        System.out.println("----------------------");
        int len = in.readableBytes();
        int capacity = in.capacity();
        int readerIndex = in.readerIndex();
        int writerIndex = in.writerIndex();
        System.out.println("len is " + len);
        System.out.println("capacity is " + capacity);
        System.out.println("readerIndex is " + readerIndex);
        System.out.println("writerIndex is " + writerIndex);
        System.out.println("----------------------");
    }
}