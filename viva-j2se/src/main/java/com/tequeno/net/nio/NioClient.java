package com.tequeno.net.nio;

public class NioClient {

    public static void main(String[] args) {
//        NioSocketHandler handler = new NioSocketHandler();
//        handler.doClient();

//        NioFileSocketHandler handler = new NioFileSocketHandler();
//        handler.doClient("/data/vod/末代皇帝.flv");
//        handler.zeroCpClient("/data/vod/末代皇帝.flv");

        NioImClientHandler client = new NioImClientHandler();
        client.start();
    }
}
