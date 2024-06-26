package com.tequeno.netty;

import com.tequeno.net.InetConst;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NettyTest {

    public static void main(String[] args) {
//        NettyTest test = new NettyTest();
//        test.testByteBuf();
        System.out.println(64 * 1024);
        System.out.println(2 << 15);
        System.out.println(16 * 1024 * 1024);
        System.out.println(2 << 23);
        System.out.println("------WebKitFormBoundaryL7w09lyBRlbEwU2I--".getBytes(StandardCharsets.UTF_8).length);
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

    public void testScanner() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String nextLine = scanner.nextLine();
                if (InetConst.BREAK_OUT.equals(nextLine)) {
                    System.out.println("结束");
                    break;
                }
                System.out.println("输入:" + nextLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}