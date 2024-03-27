package com.tequeno.net.tcp.four;

public class TCPFileTransClient {
    public static void main(String[] args) {
        TCPFileTransHandler handler = new TCPFileTransHandler();
        handler.doClient();
    }
}
