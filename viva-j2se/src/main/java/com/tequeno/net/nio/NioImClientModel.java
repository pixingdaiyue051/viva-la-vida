package com.tequeno.net.nio;

import com.tequeno.net.InetConst;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NioImClientModel {

    private Selector selector;

    private SocketChannel channel;

    private String localAddress;

    public NioImClientModel() {
        try {
            selector = Selector.open();
            channel = SocketChannel.open(new InetSocketAddress(InetConst.HOSTNAME, InetConst.NIO_SERVER_PORT));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            localAddress = channel.getLocalAddress().toString();
            System.out.println(localAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        new Thread(() -> {
            try {
                while (selector.select() > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();

                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            channel.read(byteBuffer);
                            String msg = new String(byteBuffer.array(), StandardCharsets.UTF_8);
                            System.out.println("接收到:" + msg);
                        } else {
                            System.out.println("暂无消息");
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void send() {
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                String nextLine = in.nextLine();
                String msg = localAddress + "_" + nextLine;
                System.out.println("发送:" + msg);
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                channel.write(byteBuffer);
                byteBuffer.clear();
                if (InetConst.BREAK_OUT.equals(nextLine)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
