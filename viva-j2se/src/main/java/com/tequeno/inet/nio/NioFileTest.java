package com.tequeno.inet.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Desription:
 * @Author: hexk
 */
public class NioFileTest {

    public static void main(String[] args) throws IOException {
//        RandomAccessFile file =  new RandomAccessFile("");
//        file.getChannel()
        NioFileTest nioFileTest = new NioFileTest();
//        nioFileTest.testBuffer();
        nioFileTest.fileTransferInChannel();
//        nioFileTest.testWrite();
    }

    /**
     * 单个channel,单个buffer
     */
    private void testBuffer() {
        try (FileChannel fileChannel = FileChannel.open(Paths.get("/data/doc/3.txt"),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE)
        ) {
            System.out.println("file size " + fileChannel.size());
            //create buffer with capacity of 128 bytes
            ByteBuffer c2bBuffer = ByteBuffer.allocate(128);
            System.out.println("初始化没有写入任何数据");
            bufferLocalInfo(c2bBuffer);

            int read = fileChannel.read(c2bBuffer);
            while (read != -1) {
                System.out.println("channel->buffer写入");
                bufferLocalInfo(c2bBuffer);
                // 切换读模式
                c2bBuffer.flip();
                System.out.println("buffer切换到读模式");
                bufferLocalInfo(c2bBuffer);
                // 创建一个limit()大小的字节数组(因为就只有limit这么多个数据可读)
                byte[] bytes = new byte[c2bBuffer.limit()];
                // 将读取的数据装进我们的字节数组中
                c2bBuffer.get(bytes);
                System.out.println("从buffer读取数据");
                bufferLocalInfo(c2bBuffer);
                // 输出数据
                System.out.println(new String(bytes));
                // 清空缓冲区,回到写模式
                c2bBuffer.clear();
                System.out.println("清空缓冲区,buffer回到写模式");
                bufferLocalInfo(c2bBuffer);
                read = fileChannel.read(c2bBuffer);
            }

            System.out.println("******************************************");
            ByteBuffer b2cBuffer = ByteBuffer.allocate(128);
            System.out.println("初始化没有写入任何数据");
            bufferLocalInfo(b2cBuffer);
            String src = "2343535tgedgrdg53y643hrtsdbdsgregt4y3ebsdgcb\n";
            b2cBuffer.put(src.getBytes());
            System.out.println("写入数据到buffer");
            bufferLocalInfo(b2cBuffer);
            b2cBuffer.flip();
            System.out.println("buffer切换到读模式");
            bufferLocalInfo(b2cBuffer);
            fileChannel.write(b2cBuffer);
            System.out.println("buffer读取数据");
            bufferLocalInfo(b2cBuffer);
            b2cBuffer.clear();
            System.out.println("清空缓冲区,buffer回到写模式");
            bufferLocalInfo(b2cBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bufferLocalInfo(ByteBuffer byteBuffer) {
        System.out.println("limit--->" + byteBuffer.limit());
        System.out.println("position--->" + byteBuffer.position());
        System.out.println("capacity--->" + byteBuffer.capacity());
        System.out.println("mark--->" + byteBuffer.mark());
        System.out.println("--------------------------------------");
    }

    /**
     * channel之间之间传输
     * 一个channel即代表一个已打开的文件
     */
    private void fileTransferInChannel() {
        try (FileChannel inChannel = FileChannel.open(Paths.get("/data/doc/2.txt"),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
             FileChannel outChannel = FileChannel.open(Paths.get("/data/doc/3.txt"),
                     StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
//            System.out.println("channel配合buffer完成文件读写");
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//            while (inChannel.read(byteBuffer) != -1) {
//                byteBuffer.flip();
//                outChannel.write(byteBuffer);
//                byteBuffer.clear();
//            }

//            System.out.println("直接使用transfer实现通道之间数据传输");
//            inChannel.transferTo(0, inChannel.size(), outChannel);
//            outChannel.transferFrom(inChannel, 0, inChannel.size());

//            System.out.println("channel配合MappedByteBuffer完成文件读写,直接操作缓冲区");
            MappedByteBuffer inMapper = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMapper = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
            byte[] b = new byte[inMapper.limit()];
            inMapper.get(b);
            outMapper.put(b);
            String s = new String(b);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testWrite() {
        try (FileChannel outChannel = FileChannel.open(Paths.get("/data/doc/3.txt"),
                StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            String text = "87973同一个iv辅导班v都能看见三百v举报覅尔康返回铁道部v帝国和韩国i给";
            byte[] bytes = text.getBytes();

            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            outChannel.write(byteBuffer);
            byteBuffer.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
