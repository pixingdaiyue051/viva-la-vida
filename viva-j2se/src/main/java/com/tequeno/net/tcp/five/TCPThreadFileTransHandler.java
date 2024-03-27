package com.tequeno.net.tcp.five;

import com.tequeno.net.InetConst;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP分客户端和服务端 Socket和ServerSockt 建立套接字时需要指明主机和端口
 */
public class TCPThreadFileTransHandler {

    /**
     * 建立socket客户端同时会创建输入输出流 使用输出流向服务端发送数据 使用输入流获取服务端传输的数据
     */
    public void doClient() {
        File file = new File("E:\\Files\\hexk\\doc\\test1\\hero is shit.jpg");
        try (Socket socket = new Socket(InetConst.HOSTNAME, InetConst.SENDER_PORT);
             FileInputStream fis = new FileInputStream(file);
             // 输出流传输文件名
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             // 输出流传输文件信息(二进制流)
             BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {
            dos.writeUTF(file.getName());
            byte[] b = new byte[1024];
            while (fis.read(b) > 0) {
                bos.write(b);
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立服务端,绑定一个端口,等待客户端建立连接 一旦建立连接就可获取客户端socket及输入输出流 使用对应客户端的输入流获取该客户端发送的数据
     * 使用输出流向回传数据 服务端socket因为要和多个客户端多次通信,所以可以不用关闭
     */
    public void doServer() {
        try (ServerSocket serverSocket = new ServerSocket(InetConst.SENDER_PORT);) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new TCPFileThread(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
