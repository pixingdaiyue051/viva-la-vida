package com.tequeno.net.nio;

public class NioSocketClient {

    public static void main(String[] args) {
//        NioSocketModel nioSocketModel = new NioSocketModel();
//        nioSocketModel.clientSimulator();

        NioImClientModel client = new NioImClientModel();
        client.read();
        client.send();
    }
}
