package com.tequeno.inet.nio;

public class NioSocketClient {

    public static void main(String[] args) {
//        NioSocketModel nioSocketModel = new NioSocketModel();
//        nioSocketModel.clientSimulator();

        NioImClient client = new NioImClient();
        client.read();
        client.send();
    }
}
