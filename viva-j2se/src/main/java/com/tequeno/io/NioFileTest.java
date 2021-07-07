package com.tequeno.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Desription:
 * @Author: hexk
 */
public class NioFileTest {

    public static void main(String[] args) {
//        RandomAccessFile file =  new RandomAccessFile("");
//        file.getChannel()
        NioFileTest nioFileTest = new NioFileTest();
//            nioFileTest.testBuffer();
        nioFileTest.testChannel();
    }

    private void testBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 看一下初始时4个核心变量的值
        readInfo(byteBuffer);
        // 添加一些数据到缓冲区中
        String s = "aka";
        byteBuffer.put(s.getBytes());
        // 看一下初始时4个核心变量的值
        readInfo(byteBuffer);
        // 切换读模式
        byteBuffer.flip();
        readInfo(byteBuffer);
        // 创建一个limit()大小的字节数组(因为就只有limit这么多个数据可读)
        byte[] bytes = new byte[byteBuffer.limit()];
        // 将读取的数据装进我们的字节数组中
        byteBuffer.get(bytes);
        // 输出数据
        System.out.println(new String(bytes, 0, bytes.length));
        readInfo(byteBuffer);
        // 清空缓冲区,回到写模式
        byteBuffer.clear();
        readInfo(byteBuffer);
    }

    private void readInfo(ByteBuffer byteBuffer) {
        System.out.println("limit--->" + byteBuffer.limit());
        System.out.println("position--->" + byteBuffer.position());
        System.out.println("capacity--->" + byteBuffer.capacity());
        System.out.println("mark--->" + byteBuffer.mark());
        System.out.println("--------------------------------------");
    }

    private void testChannel() {
        try (FileChannel inChannel = FileChannel.open(Paths.get("doc/data.txt"), StandardOpenOption.READ);
             FileChannel outChannel = FileChannel.open(Paths.get("D:\\hexk\\测试数据文档\\data1.txt"), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
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

            // channel配合MappedByteBuffer完成文件读写,直接操作缓冲区
//            MappedByteBuffer inMapper = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
//            MappedByteBuffer outMapper = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
//            byte[] b = new byte[inMapper.limit()];
//            inMapper.get(b);
//            outMapper.put(b);
//            String s = new String(b);
//            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
