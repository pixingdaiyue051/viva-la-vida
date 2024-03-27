package com.tequeno.net.tcp.four;

public class TCPFileTransServer {
    public static void main(String[] args) {
        TCPFileTransHandler handler = new TCPFileTransHandler();
        handler.doServer();
    }
}
