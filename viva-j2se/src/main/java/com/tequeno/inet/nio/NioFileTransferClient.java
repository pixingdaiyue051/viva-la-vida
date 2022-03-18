package com.tequeno.inet.nio;

import io.netty.util.CharsetUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class NioFileTransferClient {

    public static void main(String[] args) {
        NioFileTransferModel model = new NioFileTransferModel();
//        model.client("/data/doc/458087622-1-208.mp4");
        model.zeroCpClient("/data/末代皇帝.flv");
    }
}
