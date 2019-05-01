package com.tequeno.inet.tcp.five;

public class TCPThreadFileTransClient {
    public static void main(String[] args) {
        TCPThreadFileTransHandler handler = new TCPThreadFileTransHandler();
        handler.doClient();
    }
}
