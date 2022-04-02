package com.tequeno.inet.nio;

public class NioFileTransferServer {

    public static void main(String[] args) {
        NioFileTransferModel model = new NioFileTransferModel();
        model.server("/data/upload/2.mp4");
//        model.zeroCpServer("/data/upload/5.mp4");
    }
}
