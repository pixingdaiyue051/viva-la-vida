package com.tequeno.inet;

import java.nio.charset.StandardCharsets;

public class InetConst {

    public final static int SENDER_PORT = 9923;

    public final static int RECEIVER_PORT = 9924;

    public final static int BIO_SERVER_PORT = 9911;

    public final static int NIO_SERVER_PORT = 9910;

    public final static String HOSTNAME = "127.0.0.1";

    public final static String BREAK_OUT = "110";

    public final static String BREAK_OUT_NEW = new String(BREAK_OUT.getBytes(StandardCharsets.UTF_8));
}