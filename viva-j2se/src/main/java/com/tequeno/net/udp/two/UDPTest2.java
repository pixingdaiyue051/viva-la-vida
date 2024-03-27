package com.tequeno.net.udp.two;

import com.tequeno.net.InetConst;

import java.net.DatagramSocket;

public class UDPTest2 {

    public static void main(String[] args) {
        try {
            DatagramSocket dss = new DatagramSocket(InetConst.SENDER_PORT);
            DatagramSocket dsr = new DatagramSocket(InetConst.RECEIVER_PORT);
            UDPSendHandler sendHandler = new UDPSendHandler(dss);
            UDPRecieveHandler recieveHandler = new UDPRecieveHandler(dsr);
            Thread ts = new Thread(sendHandler);
            Thread tr = new Thread(recieveHandler);
            ts.start();
            tr.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
