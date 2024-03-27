package com.tequeno.net.tcp.three;

public class TCPTransClient {
    public static void main(String[] args) {
        TCPTransHandler handler = new TCPTransHandler();
        handler.doClient();
    }
}
