package com.tequeno.net.nio;

import com.tequeno.net.InetConst;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NioImServerModel {

    private Selector selector;

    private ServerSocketChannel serverChannel;

    public NioImServerModel() {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(InetConst.NIO_SERVER_PORT));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            System.out.println("服务器等待连接中...");
            while (true) {
                if (selector.select(1000L) == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel clientChannel = serverChannel.accept();
                        System.out.println(clientChannel.getRemoteAddress() + "已连接");
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        this.readClientData(key);
                    }
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readClientData(SelectionKey key) {

        SocketChannel clientChannel = null;
        try {
            clientChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = clientChannel.read(byteBuffer);
            if (read > 0) {
                String msg = new String(byteBuffer.array(), StandardCharsets.UTF_8);
                System.out.println("接收到" + clientChannel.getRemoteAddress() + "数据:" + msg);
                this.send2OtherClient(msg, key);
            } else {
                throw new IOException("没有数据了");
            }
        } catch (Exception e) {
            e.printStackTrace();
            key.cancel();
            try {
                System.out.println(clientChannel.getRemoteAddress() + "断开连接");
                clientChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void send2OtherClient(String msg, SelectionKey self) {
        Set<SelectionKey> keySet = selector.keys();
        keySet.stream().filter(key -> self != key)
                .map(SelectionKey::channel)
                .filter(channel -> channel instanceof SocketChannel)
                .map(channel -> ((SocketChannel) channel))
                .forEach(channel -> {
                    try {
                        System.out.println("消息转发至" + channel.getRemoteAddress());
                        channel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
