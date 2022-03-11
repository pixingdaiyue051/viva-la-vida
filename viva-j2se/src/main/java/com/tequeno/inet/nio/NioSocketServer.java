package com.tequeno.inet.nio;

public class NioSocketServer {

    public static void main(String[] args) {
//        NioSocketModel nioSocketModel = new NioSocketModel();
//        nioSocketModel.serverSimulator();

        NioImServer server = new NioImServer();
        server.listen();
    }
}
