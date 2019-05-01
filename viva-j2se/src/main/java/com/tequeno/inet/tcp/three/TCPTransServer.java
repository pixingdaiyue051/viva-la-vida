package com.tequeno.inet.tcp.three;

public class TCPTransServer {
    public static void main(String[] args) {
        TCPTransHandler handler = new TCPTransHandler();
        handler.doServer();
    }
}
