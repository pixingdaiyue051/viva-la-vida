package com.tequeno.inet.tcp.four;

import com.tequeno.inet.InetConst;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP分客户端和服务端 Socket和ServerSockt 建立套接字时需要指明主机和端口
 */
public class TCPFileTransHandler {

    /**
     * 建立socket客户端同时会创建输入输出流 使用输出流向服务端发送数据 使用输入流获取服务端传输的数据
     */
    public void doClient() {
        File file = new File("E:\\Files\\hexk\\doc\\test1\\hero is shit.jpg");
        try (Socket socket = new Socket(InetConst.HOSTNAME, InetConst.SENDPORT);
             FileInputStream fis = new FileInputStream(file);
             // 输出流传输文件名
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             // 输出流传输文件信息(二进制流)
             BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());) {
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
        try (ServerSocket serverSocket = new ServerSocket(InetConst.SENDPORT);
             Socket socket = serverSocket.accept();
             // 输入流获取为文件名
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             // 输入流获取文件信息(二进制流)
             BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
             FileOutputStream fos = new FileOutputStream(this.checkFileInServer(dis.readUTF()));) {

            byte[] b = new byte[1024];

            while (bis.read(b) > 0) {
                fos.write(b);
                fos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File checkFileInServer(String fileName) throws Exception {
        int index = fileName.lastIndexOf(".");
        String preFix = fileName.substring(0, index);
        File file = new File("E:\\Files\\hexk\\doc\\test2");
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        int i = 0;
        for (File f : file.listFiles()) {
            if (f.getAbsolutePath().contains(preFix)) {
                i++;
            }
        }
        if (i > 0) {
            String subFix = fileName.substring(index);
            fileName = preFix + "(" + i + ")" + subFix;
        }
        file = new File(file.getAbsolutePath() + "\\" + fileName);
        return file;
    }
}
