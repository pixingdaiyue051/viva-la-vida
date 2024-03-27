package com.tequeno.net.nio;

public class NioFileTransferClient {

    public static void main(String[] args) {
        NioFileTransferModel model = new NioFileTransferModel();
        model.client("/data/vod/末代皇帝.flv");
//        model.zeroCpClient("/data/vod/末代皇帝.flv");
    }
}
