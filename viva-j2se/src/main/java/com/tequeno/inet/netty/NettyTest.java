package com.tequeno.inet.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class NettyTest {

    public static void main(String[] args) {
        NettyTest test = new NettyTest();
        test.testByteBuf();
    }

    private void testByteBuf() {
        // 创建并初始化设置byteBuf缓冲区容量 容量会随着写入数据自动扩容 且始终是2的指数
        ByteBuf buf = Unpooled.buffer(11);

        // 写入数据 会引起writerIndex变化
        for (int i = 0; i < buf.writableBytes(); i++) {
            buf.writeByte(i);
        }

        // 读取数据 使用get方法不会引起readerIndex的变化
        for (int i = 0; i < buf.readableBytes(); i++) {
            System.out.println(buf.getByte(i));
        }
        // 读取数据 使用read方法会引起readerIndex的变化
        for (int i = 0; i < buf.readableBytes(); i++) {
            System.out.println(buf.readByte());
        }

        System.out.println(buf.readerIndex());
        System.out.println(buf.writerIndex());
        System.out.println(buf.readableBytes());
        System.out.println(buf.writableBytes());
        System.out.println(buf.capacity());
        System.out.println(buf.arrayOffset());
        System.out.println(buf.hasArray());
        System.out.println(new String(buf.array(), StandardCharsets.UTF_8));
    }

}
