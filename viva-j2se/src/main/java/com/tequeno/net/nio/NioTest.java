package com.tequeno.net.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioTest {

    public static void main(String[] args) throws IOException {
//        RandomAccessFile file =  new RandomAccessFile("");
//        file.getChannel();

        NioTest nioSocketModel = new NioTest();
        nioSocketModel.testBuffer();
//        nioSocketModel.testFileOutChannel();
        nioSocketModel.testFileInChannel();
//        nioSocketModel.testFileTransfer();
    }

    /**
     * capacity     申请的缓存容量 不可变
     * limit        最后一个数据所在位置(limit<=capacity)
     * position     当前所在位置(position<=limit)
     * mark         标记位(mark<=position)
     * /
     * put 存入数据(存入数据时会自动移动指针)
     * get 读取数据(读取数据时会自动向后移动指针即修改position)
     * flip 切换读写模式
     */
    private void testBuffer() {
//        IntBuffer intBuffer = IntBuffer.allocate(5);
//        for (int i = 0; i < intBuffer.limit(); i++) {
//            intBuffer.put((int) (i * Math.random() * 20));
//        }
//        intBuffer.flip();
//        while (intBuffer.hasRemaining()) {
//            System.out.println(intBuffer.get());
//        }


        // 需要填充的字节数超出申请的最大容量时异常 java.nio.BufferOverflowException
        String str = "testing...";
        ByteBuffer byteBuffer = ByteBuffer.allocate(str.length());
        byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));
        // 读写翻转 直接开始读取时异常 java.nio.BufferUnderflowException
        byteBuffer.flip();
        // 根据buffer大小申请内存 手动读取数据
        byte[] array = new byte[byteBuffer.limit()];
        byteBuffer.get(array);
        System.out.println(new String(array, StandardCharsets.UTF_8));
        byteBuffer.clear();

        // 存入数据 直接包装无需担忧超出字节
        String str1 = "testing...";
        final ByteBuffer byteBuffer1 = ByteBuffer.wrap(str1.getBytes(StandardCharsets.UTF_8));
        // 直接获得缓冲区字节数
        final byte[] array1 = byteBuffer1.array();
        System.out.println(new String(array1, StandardCharsets.UTF_8));
        byteBuffer1.clear();

    }

    /**
     * 将内存数据写入到文件内
     * 使用 fileChannel 的 write 方法将 byteBuffer 内的数据写入到文件
     */
    private void testFileOutChannel() {
        try (FileOutputStream fos = new FileOutputStream("/data/doc/2.txt");
             FileChannel fileChannel = fos.getChannel()) {
            String text = "87973同一个iv辅导班v都能看见三百v举报覅尔康返回铁道部v帝国和韩国i给";
            byte[] bytes = text.getBytes();
            // 使用wrap直接包装
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
//            // 手动申请内存
//            byteBuffer = ByteBuffer.allocate(256);
//            byteBuffer.put(bytes);
            // 写入文件
            fileChannel.write(byteBuffer);
            // 清空缓冲区 并不会实际清空数据
            byteBuffer.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件数据写入内存中
     * 使用 fileChannel 的 read 方法将 文件内的数据写入到 byteBuffer
     */
    private void testFileInChannel() {
        try (FileInputStream fis = new FileInputStream("/data/doc/sign_-1.txt");
             FileChannel fileChannel = fis.getChannel()) {
            // 读取文件
            System.out.println("文件总字节数:" + fileChannel.size());
            ByteBuffer byteBuffer = ByteBuffer.allocate(500);
            fileChannel.read(byteBuffer, 0);
            System.out.println("已读取字节数:" + byteBuffer.position());
            // 获取缓冲区的数据
            byte[] bytes = byteBuffer.array();
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
            // 清空缓冲区 并不会实际清空数据
            byteBuffer.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件之间的拷贝
     * 一个channel即代表一个已打开的文件 或者是一个已经打开的网络通道
     */
    private void testFileTransfer() {
        try (FileChannel inChannel = FileChannel.open(Paths.get("/data/doc/2.txt"),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
             FileChannel outChannel = FileChannel.open(Paths.get("/data/doc/3.txt"),
                     StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            // channel配合buffer完成文件读写
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//            while (inChannel.read(byteBuffer) != -1) {
//                byteBuffer.flip();
//                outChannel.write(byteBuffer);
//                byteBuffer.clear();
//            }

            // 直接使用transfer实现通道之间数据传输
//            inChannel.transferTo(0, inChannel.size(), outChannel);
//            outChannel.transferFrom(inChannel, 0, inChannel.size());

//            long read, total = 0;
//            while ((read = inChannel.transferTo(total, inChannel.size() - total, outChannel)) != 0) {
//                total += read;
//            }

            // channel配合MappedByteBuffer完成文件读写,直接操作缓冲区
            // mappedByteBuffer已经完成了对file的映射 可以通过操作byteBuffer来修改文件
            MappedByteBuffer inMapper = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMapper = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
            // 实现文件的copy paste
            byte[] bytes = new byte[inMapper.limit()];
            // 先将文件1的数据读取到内存
            inMapper.get(bytes);
            // 再将内存数据写入到文件2
            outMapper.put(bytes);


            String s = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}