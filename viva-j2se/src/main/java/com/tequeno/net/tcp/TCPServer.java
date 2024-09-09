package com.tequeno.net.tcp;

public class TCPServer {

    public static void main(String[] args) {
//        TCPEchoHandler handler = new TCPEchoHandler();
//        handler.doServer();

//        TCPEchoPlusHandler handler = new TCPEchoPlusHandler();
//        handler.doServer();

        TCPFileSocketHandler handler = new TCPFileSocketHandler();
        handler.doServer();
    }
}
