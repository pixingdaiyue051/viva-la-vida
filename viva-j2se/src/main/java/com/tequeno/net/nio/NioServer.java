package com.tequeno.net.nio;

public class NioServer {

    public static void main(String[] args) {
//        NioSocketHandler handler = new NioSocketHandler();
//        handler.doServer();

        NioFileSocketHandler handler = new NioFileSocketHandler();
        handler.doServer("/data/upload/2.mp4");
//        handler.zeroCpServer("/data/upload/5.mp4");

        NioImServerHandler server = new NioImServerHandler();
        server.start();
    }
}
