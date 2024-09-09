package com.tequeno.net.tcp;

public class TCPClient {

    public static void main(String[] args) {
//        TCPEchoHandler handler = new TCPEchoHandler();
//        handler.doClient();

//        TCPEchoPlusHandler handler = new TCPEchoPlusHandler();
//        handler.doClient();

        TCPFileSocketHandler handler = new TCPFileSocketHandler();
        handler.doClient("/data/doc/1.doc");
    }
}
