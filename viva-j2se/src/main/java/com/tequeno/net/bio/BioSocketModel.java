package com.tequeno.net.bio;

import com.tequeno.net.InetConst;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * bio通信模型 同步阻塞
 * server
 * |
 * |
 * stream
 * |
 * |
 * client
 */
public class BioSocketModel {

    /**
     * 模拟一个服务端等待连接-连接客户端-读取客户端数据的流程
     * 一个客户端就是一个线程连接
     * 可以使用telnet模拟连接发送数据
     */
    public void serverSimulator() {
        // 1.开启一个线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        // 2.开启一个服务端的socket
        try (ServerSocket serverSocket = new ServerSocket(InetConst.BIO_SERVER_PORT)) {
            System.out.println("开启 server socket-----------");
            // 3.轮询开启线程处理客户端的请求
            Thread thread1 = Thread.currentThread();
            System.out.printf("主线程开启轮询------%s-----%s\n", thread1.getId() + "-----", thread1.getName());
            while (true) {
                // 得到客户端
                // accept是一个阻塞方法
                System.out.println("accept...");
                Socket socket = serverSocket.accept();
                cachedThreadPool.execute(() -> {
                    Thread thread = Thread.currentThread();
                    System.out.printf("接收到 client socket------%s-----%s\n", thread.getId() + "-----", thread.getName());
                    try (InputStream inputStream = socket.getInputStream();
                         OutputStream outputStream = socket.getOutputStream()) {
                        // 先向客户端发送一条数据
                        outputStream.write("已连接可以发送数据了".getBytes(StandardCharsets.UTF_8));
                        // read是一个阻塞方法 去读客户端的数据
                        System.out.println("read...");
                        byte[] b = new byte[1024];
                        int read;
                        while ((read = inputStream.read(b)) != -1) {
                            System.out.printf("读取数据------%s-----%s\n", thread.getId() + "-----", thread.getName());
                            System.out.println(new String(b, 0, read, StandardCharsets.UTF_8));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clientSimulator() {
        try (Socket socket = new Socket(InetConst.HOSTNAME, InetConst.BIO_SERVER_PORT);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             Scanner in = new Scanner(System.in)) {
            // 获取服务端的数据
            new Thread(() -> {
                try {
                    byte[] bytes = new byte[1024];
                    int read;
                    while ((read = inputStream.read(bytes)) != -1) {
                        System.out.println(new String(bytes, 0, read, StandardCharsets.UTF_8));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            // 主线程发送数据
            while (true) {
                String nextLine = in.nextLine();
                System.out.println("输入:" + nextLine);
                if (InetConst.BREAK_OUT.equals(nextLine)) {
                    break;
                }
                outputStream.write(nextLine.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
