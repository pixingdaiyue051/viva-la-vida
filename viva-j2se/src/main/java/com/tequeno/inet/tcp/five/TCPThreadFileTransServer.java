package com.tequeno.inet.tcp.five;

public class TCPThreadFileTransServer {
    public static void main(String[] args) {
        TCPThreadFileTransHandler handler = new TCPThreadFileTransHandler();
        handler.doServer();
    }
}
