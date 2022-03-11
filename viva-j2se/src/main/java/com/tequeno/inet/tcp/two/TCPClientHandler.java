package com.tequeno.inet.tcp.two;

import com.tequeno.inet.InetConst;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClientHandler implements Runnable {

    @Override
    public void run() {
        try (Socket socket = new Socket(InetConst.HOSTNAME, InetConst.SENDER_PORT);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {
            // 1.向服务端发送数据
            out.write("let me take care of you".getBytes());
            // 2.获取服务端回传的数据
            String serverIp = socket.getInetAddress().getHostAddress();
            byte[] b = new byte[1024];
            int len = in.read(b);
            String data = new String(b, 0, len, StandardCharsets.UTF_8);
            System.out.println("服务端" + serverIp + "发回消息:" + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
