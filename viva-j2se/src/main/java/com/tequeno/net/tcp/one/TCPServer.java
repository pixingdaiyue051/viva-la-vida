package com.tequeno.net.tcp.one;

public class TCPServer {
    public static void main(String[] args) {
        TCPHandler handler = new TCPHandler();
        handler.doServer();
    }
}
