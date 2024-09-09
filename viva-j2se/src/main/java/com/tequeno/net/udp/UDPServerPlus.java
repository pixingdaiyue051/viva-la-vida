package com.tequeno.net.udp;

public class UDPServerPlus {

    public static void main(String[] args) {
        UDPHandler ud = new UDPHandler();
        int serverPort = Integer.parseInt(args[0]);
        String hostname = args[1];
        int receiverPort = Integer.parseInt(args[2]);

        ud.run(serverPort, hostname, receiverPort);
    }

}