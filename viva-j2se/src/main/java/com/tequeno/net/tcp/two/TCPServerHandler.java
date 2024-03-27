package com.tequeno.net.tcp.two;

import com.tequeno.net.InetConst;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServerHandler implements Runnable {

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(InetConst.SENDER_PORT);
             Socket socket = serverSocket.accept();
             InputStream in = socket.getInputStream()) {
            // 1.获取客服端的发送来的信息
            // accept方法是阻塞式获取socket流,没有则一直等待直到有客户端连接上
            String clientIp = socket.getInetAddress().getHostAddress();
            byte[] b = new byte[1024];
            int len = in.read(b);
            String data = new String(b, 0, len, StandardCharsets.UTF_8);
            System.out.println("客户端" + clientIp + "发来消息:" + data);
            // 2.向客户端回传数据
            Thread.sleep(5000L);
            OutputStream out = socket.getOutputStream();
            data = "silly boy,keep yourself";
            out.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
