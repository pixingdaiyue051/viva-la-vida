package com.tequeno.inet.netty.ws;

public class MyWebSocketStarter {

    public static void main(String[] args) throws Exception {
        new MyWebSocketServer(7517).run();
    }
}
