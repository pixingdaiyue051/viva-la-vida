package com.tequeno.net.udp.two;

import com.tequeno.net.InetConst;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPSendHandler implements Runnable {

    private DatagramSocket ds;

    public UDPSendHandler(DatagramSocket ds) {
        this.ds = ds;
    }

    @Override
    public void run() {
        // 1.创建UDP服务
        try (Scanner scanner = new Scanner(System.in);) {
            // 2.建立发送数据报文
            String data = null;
            InetAddress i = InetAddress.getByName(InetConst.HOSTNAME);
            while (true) {
                data = scanner.nextLine();
                if (InetConst.BREAK_OUT.equalsIgnoreCase(data)) {
                    break;
                }
                byte[] buf = data.getBytes();
                DatagramPacket dp = new DatagramPacket(buf, buf.length, i, InetConst.RECEIVER_PORT);
                // 3.发送报文
                ds.send(dp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
