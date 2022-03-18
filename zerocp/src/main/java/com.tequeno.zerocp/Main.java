package com.tequeno.zerocp;

public class Main {

    public static void main(String[] args) {

        String src = args[0];
        if(null == src || "".equals(src)) {
            return;
        }
        String dst = args[1];
        if(null == dst || "".equals(dst)) {
            return;
        }
        if(src.equals(dst)) {
            return;
        }

        Model model = new Model();

        new Thread(() -> model.zeroCpServer(dst)).start();

        model.zeroCpClient(src);

    }
}
