package com.tequeno.zerocp;

public class Main {

    public static void main(String[] args) {

        String src = args[0];
        if (null == src || "".equals(src)) {
            System.out.println("源路径为空");
            return;
        }
        String dst = args[1];
        if (null == dst || "".equals(dst)) {
            System.out.println("目标路径为空");
            return;
        }
        if (src.equals(dst)) {
            System.out.println("源路径与目标路径相同");
            return;
        }

        if (args.length == 2) {
            FileChannelModel fileChannelModel = new FileChannelModel();
            fileChannelModel.fileTransfer(src, dst);
            return;
        }
        String socket = args[2];
        String[] split = socket.split("=");
        if (!"--socket_port".equals(split[0])) {
            System.out.println("使用--socket_port=xxxx设置端口号");
            return;
        }
        int port;
        try {
            port = Integer.parseInt(split[1]);
        } catch (Exception e) {
            System.out.println("端口号有误");
            return;
        }
        SocketChannelModel socketChannelModel = new SocketChannelModel(port);
        new Thread(() -> socketChannelModel.zeroCpServer(dst)).start();
        socketChannelModel.zeroCpClient(src);
    }
}
