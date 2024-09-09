package com.tequeno.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPHandler {

    public void run(int serverPort, String receiverIp, int receiverPort) {
        // 1.创建UDP服务,指明发送端的端口,不声明,系统会默认分配
        try (DatagramSocket ds = new DatagramSocket(serverPort);
             Scanner scanner = new Scanner(System.in)) {
            // 2.单独一个线程接收数据
            new Thread(() -> this.doReceive(ds)).start();
            // 3.主线程发送数据
            InetSocketAddress adder = new InetSocketAddress(receiverIp, receiverPort);
            this.doSend(ds, scanner, adder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSend(DatagramSocket ds, Scanner scanner, InetSocketAddress adder) {
        try {
            String data;
            while (true) {
                data = scanner.nextLine();
                byte[] buf = data.getBytes();
                DatagramPacket dp = new DatagramPacket(buf, buf.length, adder);
                // 发送报文
                ds.send(dp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doReceive(DatagramSocket ds) {
        try {
            while (true) {
                // 1.建立接收数据报文的对象
                byte[] buf = new byte[1024];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                // 2.接收报文 采用传统的bio阻塞线程方式等待发包
                ds.receive(dp);
                // 3.获取数据报文中的信息
                byte[] b = dp.getData();
                String address = dp.getAddress().getHostAddress();
                int port = dp.getPort();
                String data = new String(b, 0, dp.getLength(), StandardCharsets.UTF_8);
                System.out.println(address + ":" + port + ":" + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
