package com.tequeno.net.nio;

import com.tequeno.net.InetConst;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 使用nio api socket 完成一次文件的客户端上传至服务器
 */
public class NioFileTransferModel {

    public void server(String fileName) {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(fileName),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(InetConst.NIO_SERVER_PORT));
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read, total = 0;
            long l1 = System.currentTimeMillis();
            while ((read = socketChannel.read(byteBuffer)) != -1) {
                total += read;
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            long l2 = System.currentTimeMillis();
            System.out.println("接收" + total + "字节,耗时" + (l2 - l1) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void client(String fileName) {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.READ);
             SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(InetConst.HOSTNAME, InetConst.NIO_SERVER_PORT))) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            long read, total = 0;
            long l1 = System.currentTimeMillis();
            while ((read = fileChannel.read(byteBuffer, total)) != -1) {
                total += read;
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            long l2 = System.currentTimeMillis();
            System.out.println("传输" + total + "字节,耗时" + (l2 - l1) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用nio channel transferFrom 方式数据从socket传输到文件
     *
     * @param fileName
     */
    public void zeroCpServer(String fileName) {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(fileName),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(InetConst.NIO_SERVER_PORT));
            SocketChannel socketChannel = serverSocketChannel.accept();
            long read, total = 0;
            long l1 = System.currentTimeMillis();

            while ((read = fileChannel.transferFrom(socketChannel, total, Long.MAX_VALUE)) != 0) {
                total += read;
            }

            long l2 = System.currentTimeMillis();
            System.out.println("接收" + total + "字节,耗时" + (l2 - l1) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用nio channel transferTo 方式传输数据
     * 底层使用了零拷贝实现
     * 但是在windows平台下一次只能传输8m数据
     *
     * @param fileName
     */
    public void zeroCpClient(String fileName) {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.READ);
             SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(InetConst.HOSTNAME, InetConst.NIO_SERVER_PORT))) {
            long read, total = 0;
            long l1 = System.currentTimeMillis();

            while ((read = fileChannel.transferTo(total, fileChannel.size(), socketChannel)) != 0) {
                total += read;
            }

            long l2 = System.currentTimeMillis();
            System.out.println("传输" + total + "字节,耗时" + (l2 - l1) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
