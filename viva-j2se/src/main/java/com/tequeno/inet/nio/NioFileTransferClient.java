package com.tequeno.inet.nio;

public class NioFileTransferClient {

    public static void main(String[] args) {
        NioFileTransferModel model = new NioFileTransferModel();
//        model.client("/data/doc/458087622-1-208.mp4");
        model.zeroCpClient("/data/doc/458087622-1-208.mp4");
    }
}
