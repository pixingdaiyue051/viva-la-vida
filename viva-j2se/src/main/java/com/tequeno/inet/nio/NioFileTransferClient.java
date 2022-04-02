package com.tequeno.inet.nio;

import io.netty.util.CharsetUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class NioFileTransferClient {

    public static void main(String[] args) {
        NioFileTransferModel model = new NioFileTransferModel();
        model.client("/data/vod/末代皇帝.flv");
//        model.zeroCpClient("/data/vod/末代皇帝.flv");
    }
}
