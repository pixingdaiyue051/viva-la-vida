package com.tequeno.inet.tcp.one;

public class TCPClient {
    public static void main(String[] args) {
        TCPHandler handler = new TCPHandler();
        handler.doClient();
    }
}
