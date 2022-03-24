package com.tequeno.zerocp;

import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SocketChannelModel {

    private int port;

    public SocketChannelModel(int port) {
        this.port = port;
    }

    public void zeroCpServer(String fileName) {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(fileName),
                StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(port));
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

    public void zeroCpClient(String fileName) {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.READ);
             SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", port))) {
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
