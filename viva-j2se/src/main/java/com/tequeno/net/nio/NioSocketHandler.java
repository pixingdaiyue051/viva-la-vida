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
import java.util.Scanner;
import java.util.Set;

/**
 * nio通信模型 同步非阻塞
 * server
 * |
 * |
 * selector
 * |
 * |
 * channel
 * |
 * |
 * buffer
 * |
 * |
 * client
 */
public class NioSocketHandler {

    /**
     * 模拟一个nio服务端 非阻塞等待客户连接
     * 服务端和客户端都注册到selector上
     * 只有一个线程 多路复用 连接多个客户端
     */
    public void doServer() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {
            // 开启server并监听端口
            serverSocketChannel.bind(new InetSocketAddress(InetConst.NIO_SERVER_PORT));
            // 设置server阻塞
            serverSocketChannel.configureBlocking(false);
            // 将server注册到selector上
            // 注册到selector时需要指明事件 服务端需要接收client的数据 只要selector上有accept事件触发就可以获取到对应key 进而反退到对应的channel
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            // 循环等待客户的连接
            System.out.println("服务器等待...");
            while (true) {
                // select 方法是一个阻塞方法 需要设置一个等待超时时间 返回已选的 key 集合
                if (selector.select(1000L) == 0) {
//                    System.out.println("服务器没有selectedKey继续等待...");
                    // 在超时时间内没有获取到任何 key
                    // 可以处理其他时间或继续等待
                    continue;
                }
                // 获取到所有有事件的触发的 key
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 当前这个key是由accept事件触发 表示此时有客户端连接到了服务器
                    if (key.isAcceptable()) {
                        // 虽然accept方法是阻塞的 但是因为是有事件触发的前提 所以此时会立即执行
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        System.out.println("服务端获取到一个客户端连接 channel:" + socketChannel.hashCode());
                        // 设置为非阻塞
                        socketChannel.configureBlocking(false);
                        // 客户端注册到selector上 需要指明是读取事件是read 指定一个buffer 这个buffer是一个共享数据
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
//                        // 已建立连接现象客户端发送一个消息
//                        socketChannel.write(ByteBuffer.wrap("已连接到服务器".getBytes(StandardCharsets.UTF_8)));
                    }
                    // 当前这个key是由read事件触发 表示此时有客户端有发送数据8
                    if (key.isReadable()) {
                        // 先反向获取绑定的channel
                        SocketChannel channel = (SocketChannel) key.channel();
                        System.out.println("服务端接收客户端" + channel.hashCode() + "发送的数据");
                        // 获取注册时指定的byteBuffer
//                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        // 单独开辟一个buffer 不接受上一次的数据
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // 再读取channel内的数据 写入到byteBuffer
                        int read = channel.read(buffer);
                        String msg = new String(buffer.array(), StandardCharsets.UTF_8);
//                        if(InetConst.BREAK_OUT_NEW.equals(msg)) {
//                            key.cancel();
//                            channel.close();
//                        }
                        if (read > 0) {
                            // 直接输出数据
                            System.out.println(msg);
                        } else {
                            // TODO 客户端的连接断开了 服务器端还会继续持有该客户端的连接 应该使用一个标志来关闭客户端的连接
                            // TODO 没有数据不一定就是断开了连接 不应该直接关闭
                            // 如果没有数据了 需要主动清空这个key 取消它的注册状态 关闭连接
                            key.cancel();
                            channel.close();
                        }
                    }
                    // 手动移除key 防止多线程下重复操作
//                    System.out.println("手动移除key 防止重复操作");
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟一个nio客户端 非阻塞连接到服务器
     */
    public void doClient() {
        try (SocketChannel channel = SocketChannel.open();
             Scanner in = new Scanner(System.in)) {
            // 连接到服务器
            boolean connected = channel.connect(new InetSocketAddress(InetConst.HOST, InetConst.NIO_SERVER_PORT));
            // 设置为非阻塞
            channel.configureBlocking(false);
            // 没有连接上服务器
            if (!connected) {
                // 循环尝试连接到服务器
                while (!channel.finishConnect()) {
                    System.out.println("尚未连接到服务器");
                }
            }
            // 以终端输入流的形式向服务器发送数据
            // 该方式是阻塞的但是和nio没有任何关系
            while (true) {
                String nextLine = in.nextLine();
                System.out.println("输入:" + nextLine);
                ByteBuffer byteBuffer = ByteBuffer.wrap(nextLine.getBytes(StandardCharsets.UTF_8));
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
