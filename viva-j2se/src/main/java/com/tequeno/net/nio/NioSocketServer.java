package com.tequeno.net.nio;

public class NioSocketServer {

    public static void main(String[] args) {
//        NioSocketModel nioSocketModel = new NioSocketModel();
//        nioSocketModel.serverSimulator();

        NioImServerModel server = new NioImServerModel();
        server.listen();
    }
}
