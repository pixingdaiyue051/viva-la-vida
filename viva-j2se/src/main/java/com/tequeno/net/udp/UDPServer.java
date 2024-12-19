package com.tequeno.net.udp;

import com.tequeno.net.InetConst;

public class UDPServer {

    public static void main(String[] args) {
        UDPHandler ud = new UDPHandler();
        ud.run(InetConst.SENDER_PORT, InetConst.HOST, InetConst.RECEIVER_PORT);
    }

}